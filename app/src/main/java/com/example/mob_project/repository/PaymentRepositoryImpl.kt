package com.example.mob_project.repository

import com.example.mob_project.dao.PaymentDao
import com.example.mob_project.model.Payment
import javax.inject.Inject

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDao: PaymentDao
) : PaymentRepository {
    override suspend fun getPaymentByAccountId(accountId: Int): Payment? {
        return paymentDao.getPaymentByAccountId(accountId)
    }

    override suspend fun insert(entity: Payment) {
        paymentDao.insert(entity)
    }

    override suspend fun update(entity: Payment) {
        paymentDao.update(entity)
    }

    override suspend fun delete(entity: Payment) {
        paymentDao.delete(entity)
    }
}
