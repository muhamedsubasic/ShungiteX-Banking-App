package com.example.mob_project.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.mob_project.R
import com.example.mob_project.model.Transaction
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionsScreen(navController: NavController) {
    val transactions = listOf(
        Transaction(
            transactionType = "STORE A",
            amount = 25.50,
            referenceNumber = "147xxxxxx",
            status = "SENT",
            date = parseDateSafely("01.11.2024"),
            currency = "USD",
            paymentId = null
        ),
        Transaction(
            transactionType = "STORE A",
            amount = 5.50,
            referenceNumber = "147xxxxxx",
            status = "SENT",
            date = parseDateSafely("02.11.2024"),
            currency = "USD",
            paymentId = null
        ),
        Transaction(
            transactionType = "AMT",
            amount = 50.00,
            referenceNumber = "238xxxxxx",
            status = "RECEIVED",
            date = parseDateSafely("12.01.2024"),
            currency = "USD",
            paymentId = null
        ),
        Transaction(
            transactionType = "PAYPAL INC",
            amount = 100.00,
            referenceNumber = "238xxxxxx",
            status = "RECEIVED",
            date = parseDateSafely("15.01.2024"),
            currency = "USD",
            paymentId = null
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 56.dp)
    ) {
        CardDisplaySection()
        Spacer(modifier = Modifier.height(16.dp))
        TransactionSection(transactions)
    }
}

private fun parseDateSafely(dateString: String): Date {
    return try {
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).parse(dateString) ?: Date()
    } catch (e: Exception) {
        Date() // Return current date as fallback
    }
}

@Composable
fun CardItem(
    type: String,
    lastFour: String,
    balance: String,
    backgroundColor: Color,
    logo: Painter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(verticalArrangement = Arrangement.SpaceBetween) {
                Text("${type} card", color = Color.White, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(balance, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                Text(lastFour, color = Color.White, fontSize = 14.sp)
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End,
                modifier = Modifier.fillMaxHeight()
            ) {
                Image(
                    painter = logo,
                    contentDescription = "$type logo",
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
}

@Composable
fun CardDisplaySection() {
    Column(modifier = Modifier.padding(16.dp)) {
        CardItem(
            type = "Debit",
            lastFour = "16xxxxxxx",
            balance = "12,550.50$",
            backgroundColor = Color.Black,
            logo = painterResource(id = R.drawable.mastercard_logo)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CardItem(
            type = "Credit",
            lastFour = "54xxxxxxx",
            balance = "1,644.52$",
            backgroundColor = Color(0xFF304FFE),
            logo = painterResource(id = R.drawable.visa_inc__logo_wine)
        )
    }
}

@Composable
fun TransactionSection(transactions: List<Transaction>) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "Recent transactions",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        transactions.forEach { tx ->
            TransactionItem(tx)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun TransactionItem(tx: Transaction) {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
    val formattedDate = dateFormat.format(tx.date)
    val isPositive = tx.status == "RECEIVED"

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