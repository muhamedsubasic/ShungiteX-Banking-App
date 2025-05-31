package com.example.mob_project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mob_project.R
import com.example.mob_project.viewmodels.PaymentViewModel
import com.example.mob_project.viewmodels.PaymentState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen() {
    val paymentViewModel: PaymentViewModel = viewModel()
    val paymentState by paymentViewModel.paymentState.collectAsState()

    val accountOptions = listOf("Main Account", "Savings Account", "Business Account")
    var selectedAccount by remember { mutableStateOf(accountOptions[0]) }
    var expanded by remember { mutableStateOf(false) }

    var toAccount by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var convertCurrency by remember { mutableStateOf(false) }
    var emergencyPayment by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(id = R.drawable.moblogo1),
            contentDescription = "Payment Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = selectedAccount,
                onValueChange = {},
                readOnly = true,
                label = { Text("From Account") },
                trailingIcon = {
                    Icon(
                        Icons.Filled.ArrowDropDown,
                        contentDescription = "Dropdown Icon"
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFD32F2F),
                    focusedLabelColor = Color(0xFFD32F2F),
                    cursorColor = Color(0xFFD32F2F)
                )
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                accountOptions.forEach { account ->
                    DropdownMenuItem(
                        text = { Text(account) },
                        onClick = {
                            selectedAccount = account
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = toAccount,
            onValueChange = { toAccount = it },
            label = { Text("To Account") },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFD32F2F),
                focusedLabelColor = Color(0xFFD32F2F),
                cursorColor = Color(0xFFD32F2F)
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFD32F2F),
                focusedLabelColor = Color(0xFFD32F2F),
                cursorColor = Color(0xFFD32F2F)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = convertCurrency,
                onCheckedChange = { convertCurrency = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFD32F2F)
                )
            )
            Text("Convert currency")
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (MM/DD/YYYY)") },
            leadingIcon = {
                Icon(Icons.Default.CalendarToday, contentDescription = "Date")
            },
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFFD32F2F),
                focusedLabelColor = Color(0xFFD32F2F),
                cursorColor = Color(0xFFD32F2F)
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = emergencyPayment,
                onCheckedChange = { emergencyPayment = it },
                colors = CheckboxDefaults.colors(
                    checkedColor = Color(0xFFD32F2F)
                )
            )
            Text("Emergency payment")
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Show error or success messages
        when (paymentState) {
            is PaymentState.Error -> {
                Text(
                    text = (paymentState as PaymentState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            PaymentState.Success -> {
                Text(
                    text = "Payment successful!",
                    color = Color(0xFF388E3C),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            PaymentState.Loading -> {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            else -> {}
        }

        Button(
            onClick = {
                paymentViewModel.submitPayment(
                    fromAccount = selectedAccount,
                    toAccount = toAccount,
                    amount = amount,
                    date = date,
                    convertCurrency = convertCurrency,
                    emergencyPayment = emergencyPayment
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFD32F2F),
                contentColor = Color.White
            ),
            enabled = paymentState != PaymentState.Loading
        ) {
            Text("Submit Payment")
        }
    }
}