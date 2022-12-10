package com.softgames.rentme.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.softgames.rentme.presentation.navigation.MainViewModel
import com.softgames.rentme.presentation.navigation.NavigationHost
import com.softgames.rentme.presentation.theme.RentMeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MainViewModel by viewModels()
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.isLoading }
        }
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RentMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    content = { NavigationHost(this, viewModel.startDestination) }
                )
            }
        }
    }
}

