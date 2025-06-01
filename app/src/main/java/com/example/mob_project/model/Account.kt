package com.example.mob_project.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Account")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    val accountNumber: String,
    val iban: String,
    val address: String?,
    val phoneNumber: String?,
    val bankName: String?
)