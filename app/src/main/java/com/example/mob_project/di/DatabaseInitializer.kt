package com.example.mob_project.database

import android.content.Context
import androidx.startup.Initializer
import com.example.mob_project.dao.*
import com.example.mob_project.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class DatabaseInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        val database = AppDatabase.getDatabase(context)
        CoroutineScope(Dispatchers.IO).launch {
            prepopulateDatabase(database)
        }
    }

    private suspend fun prepopulateDatabase(database: AppDatabase) {
        try {
            // Clear existing data
            database.userDao().clearAllUsers()
            database.accountDao().clearAllAccounts()
            database.cardDao().clearAllCards()
            database.paymentDao().clearAllPayments()
            database.transactionDao().clearAllTransactions()

            // Insert test account
            val account = Account(
                firstName = "John",
                lastName = "Doe",
                accountNumber = "123555123",
                iban = "GB33BUKB20201555555555",
                address = "Francuska Revolucija bb",
                phoneNumber = "+38761123123",
                bankName = "ShungiteX"
            )
            val accountId = database.accountDao().insert(account).toInt()

            // Insert test user
            val user = User(
                username = "admin",
                password = "admin123",
                email = "admin@example.com",
                createdAt = Date(),
                lastLogin = 0L,
                accountId = accountId
            )
            database.userDao().insert(user)

            // Insert test card
            val expiryDate = Calendar.getInstance().apply {
                add(Calendar.YEAR, 3)
            }.time

            val card = Card(
                accountId = accountId,
                cardNumber = "4111111111111111",
                cardType = "Debit",
                cardNetwork = "VISA",
                status = "ACTIVE",
                expiryDate = expiryDate
            )
            database.cardDao().insert(card)

            // Insert test payment
            val payment = Payment(
                accountId = accountId,
                receiverAccountNumber = "987654321",
                amount = 100.0,
                description = "Initial deposit",
                status = "COMPLETED"
            )
            val paymentId = database.paymentDao().insert(payment).toInt()

            // Insert test transaction
            val transaction = Transaction(
                transactionType = "DEPOSIT",
                amount = 100.0,
                referenceNumber = "INIT001",
                status = "COMPLETED",
                date = Date(),
                currency = "GBP",
                paymentId = paymentId
            )
            database.transactionDao().insert(transaction)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}