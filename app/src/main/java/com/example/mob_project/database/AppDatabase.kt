package com.example.mob_project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mob_project.model.Transaction
import com.example.mob_project.model.User
import com.example.mob_project.model.Account
import com.example.mob_project.model.Card
import com.example.mob_project.model.Payment



@Database(
    entities = [User::class, Account::class, Card::class, Payment::class, Transaction::class],
    version = 1
)
//@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun paymentDao(): PaymentDao
    abstract fun transactionDao(): TransactionDao
}
