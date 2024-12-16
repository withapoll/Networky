package com.app.networky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
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

                    content = { innerPadding ->

                        WelcomeScreen(
                            modifier = Modifier.padding(innerPadding),
                            onStartClick = {

                            }
                        )
                    }
                )
            }
        }
    }
}
