package com.teaberrydigital.ideaSpark

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.teaberrydigital.ideaSpark.ui.theme.IdeaSparkTheme


class MainActivity : ComponentActivity() {
    @ExperimentalMaterialApi
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdeaSparkTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    MyNavigation()
                }
            }
        }
    }
}


