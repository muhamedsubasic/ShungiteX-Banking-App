package com.example.mob_project.repository

import com.example.mob_project.model.Transaction

interface TransactionRepository : BaseRepository<Transaction> {
    suspend fun getTransactionByAccountId(accountId: Int): List<Transaction>
}
