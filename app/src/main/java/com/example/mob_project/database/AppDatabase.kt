package com.example.mob_project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mob_project.dao.AccountDao
import com.example.mob_project.dao.UserDao
import com.example.mob_project.dao.CardDao
import com.example.mob_project.dao.PaymentDao
import com.example.mob_project.dao.TransactionDao
import com.example.mob_project.model.Transaction
import com.example.mob_project.model.User
import com.example.mob_project.model.Account
import com.example.mob_project.model.Card
import com.example.mob_project.model.Payment

@Database(
    entities = [User::class, Account::class, Card::class, Payment::class, Transaction::class],
    version = 5,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun paymentDao(): PaymentDao
    abstract fun transactionDao(): TransactionDao
}
