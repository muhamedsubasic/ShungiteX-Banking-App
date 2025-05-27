package com.example.mob_project.repository

import com.example.mob_project.model.Card

interface CardRepository : BaseRepository<Card> {
    suspend fun getCardByAccountId(accountId: Int): List<Card>
}
