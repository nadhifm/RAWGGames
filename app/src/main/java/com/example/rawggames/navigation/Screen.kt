package com.example.rawggames.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object GameDetail : Screen("game-detail?gameId={gameId}")  {
        fun createRoute(gameId: Long) = "game-detail?gameId=$gameId"
    }
    object Favorite : Screen("favorite")
}