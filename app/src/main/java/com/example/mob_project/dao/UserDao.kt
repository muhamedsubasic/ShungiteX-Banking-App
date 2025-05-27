package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.mob_project.model.User

@Dao
interface UserDao : BaseDao<User> {
    @Query("SELECT * FROM Users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM Users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?
}
