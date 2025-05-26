package com.example.mob_project.model

@Entity(
    tableName = "Transactions",
    foreignKeys = [
        ForeignKey(
            entity = Payment::class,
            parentColumns = ["id"],
            childColumns = ["paymentId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("paymentId")]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val transactionType: TransactionType,
    val amount: Double,
    val referenceNumber: String?,
    val status: TransactionStatus,
    val date: Date,
    val currency: String,
    val paymentId: Int? // nullable
)
