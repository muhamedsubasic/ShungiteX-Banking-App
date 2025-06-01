package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mob_project.model.Account

import java.util.Locale

@Dao
interface AccountDao : BaseDao<Account> {
    @Query("SELECT * FROM Account WHERE id= :id;")
    suspend fun getAccountById(id: Int): Account?

    @Query("DELETE FROM Account")
    suspend fun clearAllAccounts()
}

