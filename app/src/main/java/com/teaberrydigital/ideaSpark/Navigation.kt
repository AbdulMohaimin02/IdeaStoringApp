package com.teaberrydigital.ideaSpark

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.teaberrydigital.ideaSpark.screens.home_screen.MyHomeScreen
import com.teaberrydigital.ideaSpark.ui.screens.new_idea_screen.NewIdeaScreen
import com.teaberrydigital.ideaSpark.ui.screens.update_idea_screen.UpdateIdeaScreen
import com.teaberrydigital.ideaSpark.ui.screens.all_ideas_screen.AllIdeasScreen

@ExperimentalMaterialApi
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home_screen"){
        composable (
            route = "home_screen",
        ){
            MyHomeScreen(navigation = navController)
        }

        composable("new_idea"){
            NewIdeaScreen(navigation = navController)
        }

        composable(
            route = "update_idea/{ideaId}",
            arguments = listOf(navArgument("ideaId"){
                type = NavType.IntType
            })
        ){  navBackStackEntry ->
            UpdateIdeaScreen(navigation = navController, ideaId = navBackStackEntry.arguments!!.getInt("ideaId"))
        }

        composable("all_ideas"){
            AllIdeasScreen(navigation = navController)
        }


    }
}