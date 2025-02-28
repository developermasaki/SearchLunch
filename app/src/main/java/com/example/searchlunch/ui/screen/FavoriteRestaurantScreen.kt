package com.example.searchlunch.ui.screen

import androidx.compose.runtime.Composable
import com.example.searchlunch.R
import com.example.searchlunch.ui.naviagtion.NavigationDestination

object FavoriteRestaurantDestination: NavigationDestination {
    override val route: String = "favorite_restaurant"
    override val titleRes: Int = R.string.favoriteScreen
}

@Composable
fun FavoriteRestaurantScreen(

) {

}