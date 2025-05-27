package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mob_project.model.Payment

@Dao
interface PaymentDao : BaseDao<Payment> {
    @Query("SELECT * FROM Payments WHERE accountId = :accountId")
    suspend fun getPaymentsByAccountId(accountId: Int): List<Payment>
}
