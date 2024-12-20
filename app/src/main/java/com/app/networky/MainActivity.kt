package com.app.networky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.networky.ui.navigation.Screen
import com.app.networky.ui.screens.Home
import com.app.networky.ui.screens.WelcomeScreen
import com.app.networky.ui.theme.NetworkyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NetworkyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                ) { innerPadding ->
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.WelcomeScreen.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.WelcomeScreen.route) {
                            WelcomeScreen(
                                onStartClick = {
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.WelcomeScreen.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        composable(Screen.Home.route) {
                            Home(modifier = Modifier.fillMaxSize())
                        }
                    }
                }
            }
        }
    }
}
