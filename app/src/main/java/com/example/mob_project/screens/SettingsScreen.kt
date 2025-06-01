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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.mob_project.viewmodels.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavHostController) {
    var notificationsEnabled by remember { mutableStateOf(true) }
    var darkModeEnabled by remember { mutableStateOf(false) }
    var biometricLoginEnabled by remember { mutableStateOf(true) }
    val authViewModel: AuthViewModel = viewModel()

    var showHelpDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }


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
                onClick = {navController.navigate("account")}
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
                onClick = {showHelpDialog = true }
            )
            SettingsItem(
                icon = Icons.Default.Info,
                title = "About",
                onClick = { showAboutDialog = true}
            )
            SettingsItem(
                icon = Icons.Default.ExitToApp,
                title = "Log Out",
                onClick = {authViewModel.logout(navController) },
                color = Color(0xFFD32F2F))
        }
        if (showHelpDialog) {
            AlertDialog(
                onDismissRequest = { showHelpDialog = false },
                title = { Text("Help Center", fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        Text("Frequently Asked Questions:")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• How to change my password?")
                        Text("• How to link a new bank account?")
                        Text("• How to enable biometric login?")
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("For further assistance, please contact shungitex@gmail.com")
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { showHelpDialog = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                    ) {
                        Text("OK")
                    }
                }
            )
        }

        if (showAboutDialog) {
            AlertDialog(
                onDismissRequest = { showAboutDialog = false },
                title = { Text("About", fontWeight = FontWeight.Bold) },
                text = {
                    Column {
                        Text("Shungite X", fontWeight = FontWeight.Bold)
                        Text("Version 1.0.0")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("A simple and secure way to manage your finances.")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("© 2024 ShungiteX Inc. All rights reserved.")
                    }
                },
                confirmButton = {
                    TextButton(
                        onClick = { showAboutDialog = false },
                        colors = ButtonDefaults.textButtonColors(contentColor = Color(0xFFD32F2F))
                    ) {
                        Text("OK")
                    }
                }
            )
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
