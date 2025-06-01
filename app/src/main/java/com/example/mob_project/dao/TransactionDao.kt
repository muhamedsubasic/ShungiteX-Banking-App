package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mob_project.model.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao : BaseDao<Transaction> {

    @Query("SELECT * FROM Transactions WHERE id = :id")
    suspend fun getTransactionById(id: Int): Transaction?

    @Query("SELECT * FROM Transactions ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Insert
    override suspend fun insert(transaction: Transaction): Long

}

