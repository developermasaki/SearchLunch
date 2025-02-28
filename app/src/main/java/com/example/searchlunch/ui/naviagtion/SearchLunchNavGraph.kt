package com.example.searchlunch.ui.naviagtion

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.searchlunch.ui.screen.FavoriteRestaurantDestination
import com.example.searchlunch.ui.screen.FavoriteRestaurantScreen
import com.example.searchlunch.ui.screen.HomeDestination
import com.example.searchlunch.ui.screen.HomeScreen
import com.example.searchlunch.ui.screen.QuickSearchDestination
import com.example.searchlunch.ui.screen.QuickSearchScreen
import com.example.searchlunch.ui.screen.SearchResultScreen
import com.example.searchlunch.ui.screen.SearchResultDestination
import com.example.searchlunch.ui.screen.SearchScreen
import com.example.searchlunch.ui.screen.SearchScreenDestination
import com.example.searchlunch.ui.screen.ShopDetailsDestination
import com.example.searchlunch.ui.screen.ShopDetailsScreen

@Suppress("functionName")
@Composable
fun SearchLunchNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier.fillMaxSize()
    ) {
        composable(route = HomeDestination.route) {
            HomeScreen(
                onQuickSearchTextButtonClick = {
                    navController.navigate(QuickSearchDestination.route)
                },
                onFavoriteRestaurantTextButtonClick = {
                    navController.navigate(FavoriteRestaurantDestination.route)
                },
                navigateToSearch = {
                    navController.navigate(SearchScreenDestination.route)
                }
            )
        }
        composable(route = QuickSearchDestination.route) {
            QuickSearchScreen()
        }
        composable(route = FavoriteRestaurantDestination.route) {
            FavoriteRestaurantScreen()
        }
        composable(route = SearchScreenDestination.route) {
            SearchScreen(
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToSearchResult = {
                    navController.navigate(SearchResultDestination.routeWithArgs)
                }
            )
        }
        composable(
            route = SearchResultDestination.routeWithArgs,
            arguments = listOf(navArgument(SearchResultDestination.ITEM_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            SearchResultScreen(
                navigateToSearch = {
                    navController.navigate(SearchScreenDestination.route)
                },
                navigateUp = {
                    navController.navigateUp()
                },
                navigateToShopDetails = {
                    navController.navigate("${ShopDetailsDestination.route}/${it}")
                }
            )
        }
        composable(
            route = ShopDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(SearchResultDestination.ITEM_ID_ARG) {
                type = NavType.StringType
            })
        ) {
            ShopDetailsScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}