package com.example.mob_project.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mob_project.screens.*
import com.example.mob_project.viewmodels.AuthViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    isLoggedIn: Boolean,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) BottomNavItem.Home.route else "login",
        modifier = modifier
    ) {
        composable("login") {
            val viewModel: AuthViewModel = hiltViewModel()
            LoginScreen(authViewModel = viewModel, navController = navController) // ✅ pass navController
        }


        composable(BottomNavItem.Home.route) { HomeScreen(navController) }
        composable(BottomNavItem.Payment.route) { PaymentScreen(navController) }
        composable(BottomNavItem.Settings.route) { SettingsScreen(navController) }
        composable(BottomNavItem.Account.route) { AccountScreen(navController) }
        composable(BottomNavItem.Transactions.route) { TransactionsScreen(navController) }
    }
}