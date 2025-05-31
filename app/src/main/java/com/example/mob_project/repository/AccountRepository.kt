package com.example.mob_project.repository

import com.example.mob_project.model.Account

interface AccountRepository : BaseRepository<Account> {
    suspend fun getAccountById(id: Int): Account?
}
