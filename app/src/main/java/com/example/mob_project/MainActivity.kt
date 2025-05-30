package com.example.mob_project

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.example.mob_project.database.AppDatabase
import com.example.mob_project.navigation.BottomNavBar
import com.example.mob_project.navigation.NavigationGraph
import com.example.mob_project.ui.theme.Mob_ProjectTheme
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            val dbFile = getDatabasePath("banking_app.db")
            Log.d("DB_DEBUG", "Database exists: ${dbFile.exists()}")
        }
        setContent {
            Mob_ProjectTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = { BottomNavBar(navController) }
                ) { innerPadding ->
                    Box(modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
                        NavigationGraph(navController = navController)
                    }
                }
            }
        }
    }
}
