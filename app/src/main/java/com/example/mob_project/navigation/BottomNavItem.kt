package com.example.mob_project.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(val route: String, val label: String, val icon: ImageVector) {
    object Settings : BottomNavItem("settings", "Settings", Icons.Default.Settings)
    object Home : BottomNavItem("home", "Home", Icons.Default.Home)
    object Payment : BottomNavItem("payment", "Payment", Icons.Default.MonetizationOn)
    object Account: BottomNavItem("account", "Account", Icons.Default.ManageAccounts)
    object Transactions : BottomNavItem("transactions", "Transactions", Icons.Default.CreditCard)


}