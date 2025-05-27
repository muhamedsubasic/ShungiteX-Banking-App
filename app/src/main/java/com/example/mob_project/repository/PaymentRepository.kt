package com.example.mob_project.repository

import com.example.mob_project.navigation.BottomNavItem.Payment

interface PaymentRepository : BaseRepository<Payment> {
    suspend fun getPaymentByAccountId(accountId: Int): List<Payment>
}
