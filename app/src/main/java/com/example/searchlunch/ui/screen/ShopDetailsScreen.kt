package com.example.searchlunch.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.searchlunch.R
import com.example.searchlunch.ui.AppViewModelProvider
import com.example.searchlunch.ui.SearchLunchTopAppBar
import com.example.searchlunch.ui.naviagtion.NavigationDestination
import kotlinx.serialization.json.JsonNull.content

object ShopDetailsDestination: NavigationDestination {
    override val route: String = "shop_details"
    override val titleRes: Int = R.string.restaurantDetails
    const val ITEM_ID_ARG = "itemId"
    val routeWithArgs = "$route/{$ITEM_ID_ARG}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Suppress("functionName")
@Composable
fun ShopDetailsScreen(
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShopDetailsViewModel = viewModel(factory = AppViewModelProvider.factory)
) {
    Box {
        ShopDetailsContents(
            shopDetailsUiState = viewModel.shopDetailsUiState,
            modifier = modifier
        )
        SearchLunchTopAppBar(
            title = stringResource(ShopDetailsDestination.titleRes),
            canNavigateBack = true,
            scrollBehavior = null,
            navigateUp = navigateUp,
            modifier = modifier.align(Alignment.TopCenter)
        )
    }
}

@Suppress("functionName")
@Composable
fun ShopDetailsContents(
    shopDetailsUiState: ShopDetailsUiState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(shopDetailsUiState.shopPhoto)
                .crossfade(100)
                .placeholderMemoryCacheKey("image-key${shopDetailsUiState.id}")
                .memoryCacheKey("image-key${shopDetailsUiState.id}")
                .build(),
            contentDescription = stringResource(R.string.thisIsShopPhoto),
            error = painterResource(R.drawable.ic_broken_image),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .weight(1F)
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .weight(1F)
                .padding(16.dp)
        ) {
            Text(
                text = shopDetailsUiState.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                ContentRow(
                    imageVector = Icons.Default.LocationOn,
                    content = shopDetailsUiState.address
                )
                ContentRow(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_schedule_24),
                    content = shopDetailsUiState.open
                )
                ContentRow(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_assistant_navigation_24),
                    content = shopDetailsUiState.access
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = modifier
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_public_24),
                        contentDescription = null
                    )
                    AnnotatedStringWithLink(
                        url = shopDetailsUiState.shopUrl
                    )
                    Text(
                        text = content,
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}

@Suppress("functionName")
@Composable
private fun ContentRow(
    imageVector: ImageVector,
    content: String,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null
        )

        Text(
            text = content,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Suppress("functionName")
@Composable
fun AnnotatedStringWithLink(
    url:String
) {
    Text(
        buildAnnotatedString {
            withLink(
                LinkAnnotation.Url(
                    url,
                    TextLinkStyles(style = SpanStyle(color = androidx.compose.ui.graphics.Color.Blue))
                )
            ) {
                append(url)
            }
        },
        style = MaterialTheme.typography.labelLarge
    )
}