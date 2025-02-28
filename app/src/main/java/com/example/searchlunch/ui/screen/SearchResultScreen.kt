package com.example.searchlunch.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.searchlunch.R
import com.example.searchlunch.model.FavoriteRestaurant
import com.example.searchlunch.model.Shop
import com.example.searchlunch.ui.AppViewModelProvider
import com.example.searchlunch.ui.SearchLunchTopAppBar
import com.example.searchlunch.ui.naviagtion.NavigationDestination
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import java.util.UUID

object SearchResultDestination: NavigationDestination {
    override val route: String = "search_result"
    override val titleRes: Int = R.string.restaurantList
    const val ITEM_ID_ARG = "itemId"
    val routeWithArgs = "$route/{$ITEM_ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("functionName")
@Composable
fun SearchResultScreen(
    navigateToSearch: () -> Unit,
    navigateUp: () -> Unit,
    navigateToShopDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SearchResultViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val searchResultUiState = viewModel.searchUiState
    val favoriteRestaurantList by viewModel.favoriteBookList.collectAsState()
    val showedRestaurantList by viewModel.showedRestaurant.collectAsState()

    Scaffold(
        topBar = {
            SearchLunchTopAppBar(
                title = stringResource(SearchResultDestination.titleRes),
                canNavigateBack = true,
                scrollBehavior = null,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = navigateToSearch
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
        },
        modifier = modifier
    ) { innerPadding ->
        when(searchResultUiState) {
            is SearchUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
            is SearchUiState.Success -> ResultContents(
                resultShopList = showedRestaurantList,
                favoriteRestaurantList = favoriteRestaurantList,
                navigateToShopDetails = navigateToShopDetails,
                fetchMoreResult = viewModel::fetchMoreResult,
                toggleFavoriteRestaurant = viewModel::toggleFavoriteRestaurant,
                modifier = Modifier.padding(innerPadding)
            )
            is SearchUiState.Error -> ErrorScreen( )
        }
    }
}

@Suppress("functionName")
@Composable
fun ResultContents(
    resultShopList: List<Shop?>,
    favoriteRestaurantList: List<FavoriteRestaurant>,
    navigateToShopDetails: (String) -> Unit,
    fetchMoreResult: suspend (Int) -> Unit,
    toggleFavoriteRestaurant: (Shop, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    var every50Index by rememberSaveable { mutableIntStateOf(50) }
    val listState = rememberLazyListState()

    // ある程度スクロールしたら、自動でAPIの読み込みをするため
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .filter {index -> index >=  every50Index - 30}
            .distinctUntilChanged()
            .collect {
                fetchMoreResult(every50Index)
                every50Index += 50
            }
    }

    if(resultShopList.isNotEmpty()){
        LazyColumn(
            state = listState,
            modifier = modifier
        ) {
            items(
                items = resultShopList,
                key = {it?.id ?: UUID.randomUUID()}
            ) {
                if(it != null){
                    ContentsRow(
                        restaurant = it,
                        favoriteRestaurantList = favoriteRestaurantList,
                        navigateToShopDetails = navigateToShopDetails,
                        toggleFavoriteRestaurant = toggleFavoriteRestaurant
                    )
                }
            }
        }
    }
}

@Suppress("functionName")
@Composable
fun ContentsRow(
    restaurant: Shop,
    favoriteRestaurantList: List<FavoriteRestaurant>,
    navigateToShopDetails: (String) -> Unit,
    toggleFavoriteRestaurant: (Shop, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(72.dp)
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .clickable {
                if(!restaurant.id.isNullOrBlank()) navigateToShopDetails(restaurant.id)
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(restaurant.photo?.pc?.l)
                    .crossfade(100)
                    .placeholderMemoryCacheKey("image-key${restaurant.id}")
                    .memoryCacheKey("image-key${restaurant.id}")
                    .build(),
                contentDescription = stringResource(R.string.thisIsShopPhoto),
                error = painterResource(R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(56.dp)
                    .width(56.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = restaurant.name ?: "",
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = restaurant.access ?: "",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    style = MaterialTheme.typography.labelLarge,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconToggleButton(
                checked = favoriteRestaurantList.any { restaurant.id == it.id },
                onCheckedChange = {
                    toggleFavoriteRestaurant(restaurant, it)
                }
            ){
                if(favoriteRestaurantList.any { restaurant.id == it.id }) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = stringResource(R.string.favorite)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = stringResource(R.string.notFavorite)
                    )
                }
            }
        }
        HorizontalDivider()
    }
}

@Suppress("functionName")
@Stable
@Composable
private fun LoadingScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator()
    }
}

@Suppress("functionName")
@Stable
@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_connection_error),
            contentDescription = stringResource(R.string.connectionError)
        )
    }
}