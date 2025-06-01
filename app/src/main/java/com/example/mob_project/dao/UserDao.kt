package com.example.mob_project.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mob_project.model.User
import java.util.Date

@Dao
interface UserDao : BaseDao<User> {

    @Query("SELECT * FROM Users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?

    @Query("SELECT * FROM Users WHERE username = :username AND password = :password")
    suspend fun getUserByUsernameAndPassword(username: String, password: String): User?

    @Query("SELECT * FROM Users WHERE email = :email AND password = :password")
    suspend fun getUserByEmailAndPassword(email: String, password: String): User?

    @Query("SELECT * FROM Users WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT * FROM Users WHERE username = :username")
    suspend fun getUserByUsername(username: String): User?

    @Query("UPDATE users SET lastLogin = :timestamp WHERE id = :userId")
    suspend fun updateLastLogin(userId: Int, timestamp: Date)

}