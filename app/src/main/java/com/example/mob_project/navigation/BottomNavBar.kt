package com.example.mob_project.navigation

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.graphics.Color


@Composable
fun BottomNavBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Settings,
        BottomNavItem.Account,
        BottomNavItem.Home,
        BottomNavItem.Payment,
        BottomNavItem.Transactions
    )

    val selectedColor = Color(0xFFD32F2F)
    val unselectedColor = LocalContentColor.current

    NavigationBar {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        items.forEach { item ->
            val isSelected = currentRoute == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (isSelected) selectedColor else unselectedColor
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        color = if (isSelected) selectedColor else unselectedColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                alwaysShowLabel = true
            )
        }
    }
}