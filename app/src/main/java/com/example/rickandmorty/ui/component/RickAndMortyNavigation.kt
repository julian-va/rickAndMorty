package com.example.rickandmorty.ui.component

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

sealed class Screen(val router: String){

    object Home :Screen(router = "home")

    object Detail :Screen(router = "detail?id={id}"){
        fun passId(id:Int):String{
            return "detail?id=$id"
        }
    }

}

class RickAndMortyActions(navController: NavController) {

    val navigateToHome: () -> Unit = {
        navController.navigate(Screen.Home.router) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val navigationToDetail = { id: Int ->
        navController.navigate(Screen.Detail.passId(id = id)) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = false
        }
    }
}

