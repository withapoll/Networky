package com.app.networky.ui.navigation

sealed class Screen(val route: String) {
    object WelcomeScreen : Screen("onboarding")
    object Home : Screen("home")

}