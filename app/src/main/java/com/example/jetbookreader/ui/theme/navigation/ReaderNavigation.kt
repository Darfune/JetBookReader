package com.example.jetbookreader.ui.theme.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetbookreader.ui.theme.screens.createaccount.CreateAccount
import com.example.jetbookreader.ui.theme.screens.details.BookDetailsScreen
import com.example.jetbookreader.ui.theme.screens.home.HomeScreen
import com.example.jetbookreader.ui.theme.screens.login.LoginScreen
import com.example.jetbookreader.ui.theme.screens.search.SearchScreen
import com.example.jetbookreader.ui.theme.screens.splash.ReaderSplashScreen
import com.example.jetbookreader.ui.theme.screens.stats.StatsScreen
import com.example.jetbookreader.ui.theme.screens.update.UpdateScreen

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReaderScreens.SplashScreen.name) {

        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController)
        }

        composable(ReaderScreens.ReaderHomeScreen.name){
            HomeScreen(navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            LoginScreen(navController)
        }

        composable(ReaderScreens.UpdateScreen.name) {
            UpdateScreen(navController)
        }

        composable(ReaderScreens.ReaderStatsScreen.name) {
            StatsScreen(navController)
        }

        composable(ReaderScreens.CreateAccountScreen.name) {
            CreateAccount(navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            SearchScreen(navController)
        }

        composable(ReaderScreens.BookDetailsScreen.name) {
            BookDetailsScreen(navController)
        }
    }
}