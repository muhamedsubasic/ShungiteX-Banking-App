package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mob_project.model.Transaction

@Dao
interface TransactionDao : BaseDao<Transaction> {
    @Query("SELECT * FROM Transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Query("SELECT * FROM Transactions ORDER BY date DESC")
    suspend fun getAllTransactions(): List<Transaction>

    @Query("DELETE FROM Transactions")
    suspend fun clearAllTransactions()
}
