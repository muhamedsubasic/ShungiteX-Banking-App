package com.example.mob_project.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mob_project.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var biometricLoginEnabled by remember { mutableStateOf(true) }
    val authViewModel: AuthViewModel = hiltViewModel()



    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
            verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Settings",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        SettingsSection(title = "Account Settings") {
            SettingsItem(
                icon = Icons.Default.Person,
                title = "Personal Information",
                onClick = {}
            )
            SettingsItem(
                icon = Icons.Default.Lock,
                title = "Change Password",
                onClick = {}
            )
            SettingsItem(
                icon = Icons.Default.AccountBalance,
                title = "Linked Accounts",
                onClick = {}
            )
        }

        // App Preferences Section
        SettingsSection(title = "App Preferences") {
            SettingsSwitchItem(
                icon = Icons.Default.Notifications,
                title = "Notifications",
                isChecked = notificationsEnabled,
                onCheckedChange = { notificationsEnabled = it }
            )
            SettingsSwitchItem(
                icon = Icons.Default.DarkMode,
                title = "Dark Mode",
                isChecked = darkModeEnabled,
                onCheckedChange = { darkModeEnabled = it }
            )
            SettingsSwitchItem(
                icon = Icons.Default.Fingerprint,
                title = "Biometric Login",
                isChecked = biometricLoginEnabled,
                onCheckedChange = { biometricLoginEnabled = it }
            )
        }

        // Support Section
        SettingsSection(title = "Support") {
            SettingsItem(
                icon = Icons.Default.Help,
                title = "Help",
                onClick = { }
            )
            SettingsItem(
                icon = Icons.Default.Info,
                title = "About",
                onClick = { }
            )
            SettingsItem(
                icon = Icons.Default.ExitToApp,
                title = "Log Out",
                onClick = {authViewModel.logout(navController) },
                color = Color(0xFFD32F2F))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFD32F2F),
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            border = BorderStroke(1.dp, Color(0xFFFFCDD2))
        ) {
            Column {
                content()
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    color: Color = Color.Black
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                color = color,
                fontSize = 16.sp
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFD32F2F))
        },
        modifier = Modifier.clickable { onClick() }
    )
    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsSwitchItem(
    icon: ImageVector,
    title: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = title,
                fontSize = 16.sp
            )
        },
        leadingContent = {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFFD32F2F))
        },
        trailingContent = {
            Switch(
                checked = isChecked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = Color(0xFFD32F2F),
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = Color.Gray
                )
            )
        }
    )
    Divider(color = Color(0xFFEEEEEE), thickness = 1.dp)
}
