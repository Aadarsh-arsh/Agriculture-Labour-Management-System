package com.example.kisanmitra

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.kisanmitra.navigation.NavGraph

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            KisanMitra2App()
        }
    }
}

@Composable
fun KisanMitra2App() {

    val navController = rememberNavController()

    NavGraph(
        navController = navController
    )
}