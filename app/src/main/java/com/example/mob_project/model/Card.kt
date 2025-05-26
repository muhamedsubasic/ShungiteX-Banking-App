package com.example.mob_project.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val accountId: Int,
    val cardNumber: String,
    val cardType: CardType,
    val cardNetwork: CardNetwork,
    val status: CardStatus,
    val expiryDate: Date
) {
    enum class CardType {
        DEBIT, CREDIT, PREPAID
    }

    enum class CardNetwork {
        VISA, MASTERCARD, AMERICAN_EXPRESS, DISCOVER
    }

    enum class CardStatus {
        ACTIVE, INACTIVE, BLOCKED, EXPIRED
    }
}
