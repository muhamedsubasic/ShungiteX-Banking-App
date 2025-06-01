package com.example.mob_project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.mob_project.R
import com.example.mob_project.model.Transaction
import com.example.mob_project.viewmodel.TransactionViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionsScreen(
    navController: NavController,
    viewModel: TransactionViewModel = hiltViewModel()
) {
    val transactions by viewModel.transactions


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        CardDisplaySection()
        Spacer(modifier = Modifier.height(24.dp))
        TransactionSection(transactions)
    }
}

@Composable
fun CardDisplaySection() {
    Column {
        Text(
            text = "Cards",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BankCardd(
            balance = "12,550.50$",
            cardNumber = "16xxxxxxx",
            cardType = "Debit card",
            logo = painterResource(id = R.drawable.mastercard_logo),
            cardColor = Color(0xCE000000),
        )

        Spacer(modifier = Modifier.height(16.dp))

        BankCardd(
            balance = "1,644.52$",
            cardNumber = "54xxxxxxx",
            cardType = "Credit card",
            logo = painterResource(id = R.drawable.visa_logo),
            cardColor = Color(0xFF3F51B5)
        )
    }
}

@Composable
fun BankCardd(
    balance: String,
    cardNumber: String,
    cardType: String,
    logo: Painter,
    cardColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = cardType,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge
            )

            Column {
                Text(
                    text = balance,
                    color = Color.White,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = cardNumber,
                    color = Color.White.copy(alpha = 0.8f),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Image(
                    painter = logo,
                    contentDescription = "$cardType logo",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}

@Composable
fun TransactionSection(transactions: List<Transaction>) {
    Column {
        Text(
            text = "Recent transactions",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        if (transactions.isEmpty()) {
            Text("No transactions yet.")
        } else {
            transactions.forEach { tx ->
                TransactionItem(tx)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun TransactionItem(tx: Transaction) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formattedDate = tx.date?.let { dateFormat.format(it) } ?: "Unknown Date"

    val isPositive = tx.transactionType.equals("DEPOSIT", ignoreCase = true) ||
            tx.transactionType.equals("RECEIVED", ignoreCase = true)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (isPositive) Color(0xFF00C853) else Color.Red,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(text = formattedDate, fontWeight = FontWeight.SemiBold)
            Text(text = tx.transactionType.uppercase(), fontWeight = FontWeight.Bold)
        }
        Text(
            text = (if (isPositive) "+" else "-") + String.format("%.2f", tx.amount) + "$",
            color = if (isPositive) Color(0xFF00C853) else Color.Red,
            fontWeight = FontWeight.Bold
        )
    }
}
