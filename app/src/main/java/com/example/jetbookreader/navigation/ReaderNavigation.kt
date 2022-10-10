package com.example.jetbookreader.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jetbookreader.screens.createaccount.CreateAccount
import com.example.jetbookreader.screens.details.BookDetailsScreen
import com.example.jetbookreader.screens.home.HomeScreen
import com.example.jetbookreader.screens.login.LoginScreen
import com.example.jetbookreader.screens.search.BookSearchViewModel
import com.example.jetbookreader.screens.search.SearchScreen
import com.example.jetbookreader.screens.splash.ReaderSplashScreen
import com.example.jetbookreader.screens.stats.StatsScreen
import com.example.jetbookreader.screens.update.UpdateScreen

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
            val searchViewModel = hiltViewModel<BookSearchViewModel>()
            SearchScreen(navController, viewModel = searchViewModel)
        }

        composable(ReaderScreens.BookDetailsScreen.name) {
            BookDetailsScreen(navController, bookId = "")
        }
    }
}