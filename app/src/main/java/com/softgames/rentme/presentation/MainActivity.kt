package com.softgames.rentme.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Public
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.softgames.rentme.presentation.components.buttons.MyButton
import com.softgames.rentme.presentation.components.buttons.MyElevatedButton
import com.softgames.rentme.presentation.components.buttons.MyOutlinedButton
import com.softgames.rentme.presentation.components.buttons.MyTonalButton
import com.softgames.rentme.presentation.navigation.NavigationHost
import com.softgames.rentme.presentation.theme.RentMeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            RentMeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    content = { NavigationHost(this) }
                )
            }
        }
    }
}
