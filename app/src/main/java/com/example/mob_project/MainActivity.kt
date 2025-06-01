package com.example.mob_project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.mob_project.navigation.BottomNavBar
import com.example.mob_project.navigation.NavigationGraph
import com.example.mob_project.viewmodels.AuthViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dbFile = getDatabasePath("banking_app.db")
        if (dbFile.exists()) {
            Log.d("DB_STATUS", "Database exists at: ${dbFile.absolutePath}")
        } else {
            Log.e("DB_STATUS", "Database NOT FOUND!")
        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val authViewModel: AuthViewModel = viewModel()
            val isLoggedIn by authViewModel.isLoggedIn.collectAsState()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                bottomBar = {

                    if (currentRoute in listOf(
                            "home", "payment", "transactions",
                            "account", "settings"
                        )) {
                        BottomNavBar(navController)
                    }
                }
            ) { innerPadding ->
                NavigationGraph(
                    navController = navController,
                    isLoggedIn = isLoggedIn,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }
        }
    }
}