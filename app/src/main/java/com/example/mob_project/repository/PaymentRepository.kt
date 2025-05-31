package com.example.mob_project.repository

import com.example.mob_project.model.Payment


interface PaymentRepository : BaseRepository<Payment> {
    suspend fun getPaymentByAccountId(accountId: Int): Payment?
}
