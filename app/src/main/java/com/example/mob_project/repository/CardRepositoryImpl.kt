package com.example.mob_project.repository

import com.example.mob_project.dao.CardDao
import com.example.mob_project.model.Card
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao
) : CardRepository {
    override suspend fun getCardByAccountId(accountId: Int): List<Card> {
        return cardDao.getCardByAccountId(accountId)
    }

    override suspend fun insert(entity: Card) {
        cardDao.insert(entity)
    }

    override suspend fun update(entity: Card) {
        cardDao.update(entity)
    }

    override suspend fun delete(entity: Card) {
        cardDao.delete(entity)
    }
}
