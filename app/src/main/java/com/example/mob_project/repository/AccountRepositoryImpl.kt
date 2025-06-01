package com.example.mob_project.repository

import com.example.mob_project.dao.AccountDao
import com.example.mob_project.model.Account
import javax.inject.Inject

class AccountRepositoryImpl @Inject constructor(
    private val accountDao: AccountDao
) : AccountRepository {
    override suspend fun getAccountById(id: Int): Account? {
        return accountDao.getAccountById(id)
    }

    override suspend fun insert(entity: Account) {
        accountDao.insert(entity)
    }

    override suspend fun update(entity: Account) {
        accountDao.update(entity)
    }

    override suspend fun delete(entity: Account) {
        accountDao.delete(entity)
    }
}
