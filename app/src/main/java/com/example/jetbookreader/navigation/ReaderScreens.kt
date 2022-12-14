package com.example.jetbookreader.navigation

enum class ReaderScreens {
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    ReaderHomeScreen,
    SearchScreen,
    BookDetailsScreen,
    UpdateScreen,
    ReaderStatsScreen;

    companion object {
        fun fromRoute(route: String?): ReaderScreens = when (route?.substringBefore("/")) {
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            CreateAccountScreen.name -> CreateAccountScreen
            ReaderHomeScreen.name -> ReaderHomeScreen
            SearchScreen.name -> SearchScreen
            BookDetailsScreen.name -> BookDetailsScreen
            UpdateScreen.name -> UpdateScreen
            ReaderStatsScreen.name -> ReaderStatsScreen
            null -> ReaderHomeScreen
            else -> throw IllegalAccessException("Route $route is not recognized")
        }
    }
}