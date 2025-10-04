package com.example.myapplication.database.data

import androidx.room.*

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(user: UserEntity): Long

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getByEmail(email: String): UserEntity?

    @Query("UPDATE users SET isLoggedIn = 0")
    suspend fun clearLoggedIn()

    @Query("UPDATE users SET isLoggedIn = 1 WHERE id = :userId")
    suspend fun setLoggedIn(userId: Long)

    @Query("SELECT * FROM users WHERE isLoggedIn = 1 LIMIT 1")
    suspend fun getCurrentLoggedIn(): UserEntity?
}