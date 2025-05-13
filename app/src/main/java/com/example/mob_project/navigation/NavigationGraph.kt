package com.example.mob_project.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mob_project.screens.*

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }

        composable(BottomNavItem.Home.route) { HomeScreen() }
        composable(BottomNavItem.Payment.route) { PaymentScreen() }
        composable(BottomNavItem.Settings.route) { SettingsScreen() }
        composable(BottomNavItem.Account.route) { AccountScreen() }
        composable(BottomNavItem.Transactions.route){ TransactionsScreen() }
    }
}