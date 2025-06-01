package com.example.mob_project.repository

import com.example.mob_project.model.User

interface UserRepository : BaseRepository<User> {
    suspend fun getUserById(id: Int): User?
    suspend fun getUserByUsername(username: String): User?
}
