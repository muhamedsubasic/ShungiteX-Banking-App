package com.example.mob_project.screens

import android.R.attr.type
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.mob_project.R

@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color(0xFFF5F5F5))
    ) {
        CardsSection()
        RecentTransactionsSection(navController)
        ExpensesSection()
        Spacer(modifier = Modifier.height(16.dp))
    }
}




@Composable
fun CardsSection() {
    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Cards",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        BankCard(
            balance = "12,550.50$",
            cardNumber = "16xxxxxxx",
            cardType = "Debit card",
            logo = painterResource(id = R.drawable.mastercard_logo),
            cardColor = Color(0xCE000000)
        )

        Spacer(modifier = Modifier.height(16.dp))

        BankCard(
            balance = "1,644.52$",
            cardNumber = "54xxxxxxx",
            cardType = "Credit card",
            logo = painterResource(id = R.drawable.visa_logo),
            cardColor = Color(0xFF3F51B5)
        )
    }
}

@Composable
fun BankCard(
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
        colors = CardDefaults.cardColors(
            containerColor = cardColor
        )
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
                    contentDescription = "$type logo",
                    modifier = Modifier.size(35.dp)
                )
            }
        }
    }
}

@Composable
fun RecentTransactionsSection(navController: NavHostController) {
    Spacer(modifier = Modifier.height(40.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFAFAFA)
        ),
        border = BorderStroke(1.5.dp, Color(0xFFFF5252))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Recent transactions",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Column(
                modifier = Modifier.padding(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TransactionBox(date = "11.2024", merchant = "STORE A", amount = "-25.50$")
                TransactionBox(date = "11.2024", merchant = "STORE A", amount = "-5.50$")
                TransactionBox(date = "12.1.2024", merchant = "AMT", amount = "+50.00$")
                TransactionBox(date = "15.1.2024", merchant = "PAYPAL INC", amount = "+100.00$")
                TransactionBox(date = "15.1.2024", merchant = "PAYPAL INC", amount = "+100.00$")
            }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { navController.navigate("transactions") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5252),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    "VIEW MORE",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                )
            }
        }
    }
}

@Composable
fun TransactionBox(date: String, merchant: String, amount: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color(0xFFFF5252)),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = date, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(2f))
            Text(text = merchant, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(3f))
            Text(
                text = amount,
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                color = if (amount.startsWith("-")) Color(0xFFFF5252) else Color(0xFF4CAF50),
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Composable
fun ExpensesSection() {
    val expenses = listOf(
        ExpenseCategory("Groceries", 515.07f, 40, Color(0xFF4CAF50)),
        ExpenseCategory("Entertainment", 343.38f, 60, Color(0xFF2196F3))
    )
    val total = expenses.sumOf { it.amount.toDouble() }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAFAFA)),
        border = BorderStroke(1.5.dp, Color(0xFFFF5252))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Expenses Breakdown",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .padding(8.dp)
                ) {
                    PieChart(expenses = expenses, modifier = Modifier.fillMaxSize())
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    expenses.forEach { category ->
                        LegendItem(category = category)
                    }
                }
            }

            Divider(modifier = Modifier.padding(vertical = 16.dp), color = Color.LightGray, thickness = 1.dp)
            Text(
                text = "Total: ${"%.2f".format(total)}$",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

@Composable
fun PieChart(expenses: List<ExpenseCategory>, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val totalPercentage = expenses.sumOf { it.percentage.toDouble() }.toFloat()
        var startAngle = -90f

        for (expense in expenses) {
            val sweepAngle = (expense.percentage / totalPercentage) * 360f
            drawArc(
                color = expense.color,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                size = Size(size.width, size.height)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun LegendItem(category: ExpenseCategory) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(category.color)
                .border(1.dp, Color.DarkGray, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(
                text = "${category.name}: ${"%.2f".format(category.amount)}$",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "${category.percentage}%",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}

data class ExpenseCategory(
    val name: String,
    val amount: Float,
    val percentage: Int,
    val color: Color
)
