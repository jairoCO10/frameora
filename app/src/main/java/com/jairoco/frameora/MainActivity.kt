package com.jairoco.frameora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.jairoco.frameora.ui.screen.FrameoraScreen
import com.jairoco.frameora.ui.theme.FrameoraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            FrameoraTheme {
                FrameoraScreen()
            }
        }
    }
}