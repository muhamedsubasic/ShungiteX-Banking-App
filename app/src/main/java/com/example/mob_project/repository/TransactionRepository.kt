package com.example.mob_project.repository

import com.example.mob_project.model.Transaction

interface TransactionRepository : BaseRepository<Transaction> {
    suspend fun getTransactionById(Id: Int): Transaction?
    suspend fun getAllTransactions(): List<Transaction>
}
