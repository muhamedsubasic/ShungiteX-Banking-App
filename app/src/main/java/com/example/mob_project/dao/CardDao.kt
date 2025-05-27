package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mob_project.model.Card

@Dao
interface CardDao : BaseDao<Card> {
    @Query("SELECT * FROM Card WHERE accountId = :accountId")
    suspend fun getCardsByAccountId(accountId: Int): List<Card>
}
