package com.example.rawggames.presentation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rawggames.navigation.Screen
import com.example.rawggames.presentation.home.HomeScreen
import androidx.navigation.NavType
import com.example.rawggames.presentation.favorite.FavoriteScreen
import com.example.rawggames.presentation.game_detail.GameDetailScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                navigateToGameDetailScreen = {
                    navController.navigate(Screen.GameDetail.createRoute(it))
                },
                navigateToFavoriteScreen = {
                    navController.navigate(Screen.Favorite.route)
                }
            )
        }
        composable(
            Screen.GameDetail.route,
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.LongType
                    defaultValue = -1
                },
            ),
        ) {
            GameDetailScreen(
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
        composable(Screen.Favorite.route) {
            FavoriteScreen(
                navigateToGameDetailScreen = {
                    navController.navigate(Screen.GameDetail.createRoute(it))
                },
                navigateBack = {
                    navController.navigateUp()
                },
            )
        }
    }
}