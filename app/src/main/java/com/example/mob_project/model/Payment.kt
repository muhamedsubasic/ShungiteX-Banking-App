package com.example.mob_project.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "Payments",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("accountId")]
)
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountId: Int,
    val receiverAccountNumber: String,
    val amount: Double,
    val description: String?,
    val status: PaymentStatus
) {
    enum class PaymentStatus {
        PENDING, COMPLETED, FAILED, CANCELLED
    }
}
