package com.example.searchlunch.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.searchlunch.R
import com.example.searchlunch.model.FavoriteRestaurant
import com.example.searchlunch.model.QuickSearch
import com.example.searchlunch.ui.AppViewModelProvider
import com.example.searchlunch.ui.SearchLunchTopAppBar
import com.example.searchlunch.ui.components.OriginalCardRow
import com.example.searchlunch.ui.naviagtion.NavigationDestination

object HomeDestination: NavigationDestination {
    override val route: String = "home"
    override val titleRes: Int = R.string.app_name
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("functionName")
@Composable
fun HomeScreen(
    onQuickSearchTextButtonClick: () -> Unit,
    onFavoriteRestaurantTextButtonClick: () -> Unit,
    navigateToSearch: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    val quickSearchList by viewModel.quickSearchListStream.collectAsState()
    val favoriteRestaurantList by viewModel.favoriteRestaurantListStream.collectAsState()

    Scaffold(
        topBar = {
            SearchLunchTopAppBar(
                title = stringResource(HomeDestination.titleRes),
                canNavigateBack = false
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
    ) {innerPadding ->
        HomeContents(
            quickSearchList = quickSearchList,
            favoriteRestaurantList = favoriteRestaurantList,
            onQuickSearchTextButtonClick = onQuickSearchTextButtonClick,
            onFavoriteRestaurantTextButtonClick = onFavoriteRestaurantTextButtonClick,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Suppress("functionName")
@Composable
fun HomeContents(
    quickSearchList: List<QuickSearch>,
    favoriteRestaurantList: List<FavoriteRestaurant>,
    onQuickSearchTextButtonClick: () -> Unit,
    onFavoriteRestaurantTextButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(start = 32.dp, end = 32.dp)
    ) {
        QuickSearchCardRow(
            quickSearchList = quickSearchList,
            onTextButtonClick = onQuickSearchTextButtonClick
        )
        FavoriteRestaurantCardRow(
            shopList = favoriteRestaurantList,
            onTextButtonClick = onFavoriteRestaurantTextButtonClick
        )
    }
}

// どうComposable関数を切り出すか迷う
@Suppress("functionName")
@Composable
fun QuickSearchCardRow(
    quickSearchList: List<QuickSearch>,
    onTextButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OriginalCardRow(
        titleResId = R.string.quickSearch,
        itemList = quickSearchList,
        keyGenerator = {
            it.id
        },
        onTextButtonClick = onTextButtonClick,
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) { quickSearch ->
        ContentCard(
            key = quickSearch.id.toString(),
            contentDescription = quickSearch.keyword,
            onClick = {}
        )
    }
}

@Suppress("functionName")
@Composable
fun FavoriteRestaurantCardRow(
    shopList: List<FavoriteRestaurant>,
    onTextButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OriginalCardRow(
        titleResId = R.string.favorite,
        itemList = shopList,
        keyGenerator = {
            it.id
        },
        onTextButtonClick = onTextButtonClick,
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) { favoriteRestaurant ->
        ContentCard(
            key = favoriteRestaurant.id,
            contentDescription = favoriteRestaurant.name,
            photo = favoriteRestaurant.shopPhoto,
            onClick = {},
            modifier = Modifier
        )
        Spacer(
            modifier = Modifier.width(16.dp).fillMaxHeight()
        )
    }
}

@Suppress("functionName")
@Composable
fun ContentCard(
    modifier: Modifier = Modifier,
    key: String,
    contentDescription: String,
    photo: String? = R.drawable.no_image.toString(),
    onClick: (Int) -> Unit,
) {
    Card(
        onClick = {onClick(key.toInt())},
        modifier = modifier
            .height(120.dp)
            .width(120.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(photo)
                    .crossfade(100)
                    .placeholderMemoryCacheKey("image-key${key}")
                    .memoryCacheKey("image-key${key}")
                    .build(),
                contentDescription = contentDescription,
                error = painterResource(R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
            Surface(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer {
                        alpha = 0.4F
                    }
                    .align(Alignment.BottomCenter)
            ){}
            Text(
                text = contentDescription,
                color = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.Center)
            )
        }
    }
}