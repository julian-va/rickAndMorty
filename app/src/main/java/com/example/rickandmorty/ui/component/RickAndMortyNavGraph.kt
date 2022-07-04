package com.example.rickandmorty.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.ui.component.home.HomeScreen

@Composable
fun RickAndMortyNavGraph(
    modifier:Modifier = Modifier,
    navigateToHome: () -> Unit,
    navigeteToDetail: (Int) -> Unit,
    navHostController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Home.router
){
    NavHost(navController = navHostController, startDestination = startDestination, modifier = modifier){
        composable(route = Screen.Home.router){
            HomeScreen(
                onItemClicked = {navigeteToDetail(it)}
            )
        }
    }
}