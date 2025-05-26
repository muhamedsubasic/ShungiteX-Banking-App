package com.example.mob_project.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [User::class, Account::class, Card::class, Payment::class, Transaction::class],
    version = 1
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun paymentDao(): PaymentDao
    abstract fun transactionDao(): TransactionDao
}
