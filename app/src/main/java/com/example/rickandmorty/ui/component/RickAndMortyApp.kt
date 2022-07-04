package com.example.rickandmorty.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun RickAndMortyApp() {
    RickAndMortyTheme() {
        val navController = rememberNavController()
        val navigationAction = remember(navController) {
            RickAndMortyActions(navController)
        }
        RickAndMortyNavGraph(
            navHostController = navController,
            navigateToHome = navigationAction.navigateToHome,
            navigeteToDetail = navigationAction.navigationToDetail
        )
    }
}