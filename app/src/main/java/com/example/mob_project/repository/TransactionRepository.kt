package com.example.mob_project.repository

import com.example.mob_project.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactionById(id: Int): Transaction?
    suspend fun insert(entity: Transaction)
    suspend fun update(entity: Transaction)
    suspend fun delete(entity: Transaction)
    fun getAllTransactions(): Flow<List<Transaction>>
}
