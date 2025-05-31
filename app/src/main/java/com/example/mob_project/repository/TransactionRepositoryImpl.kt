package com.example.mob_project.repository

import com.example.mob_project.dao.TransactionDao
import com.example.mob_project.model.Transaction
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun getTransactionById(id: Int): Transaction? {
        return transactionDao.getTransactionById(id)
    }

    override suspend fun insert(entity: Transaction) {
        transactionDao.insert(entity)
    }

    override suspend fun update(entity: Transaction) {
        transactionDao.update(entity)
    }

    override suspend fun delete(entity: Transaction) {
        transactionDao.delete(entity)
    }
    override suspend fun getAllTransactions(): List<Transaction> {
        return transactionDao.getAllTransactions()
    }
}
