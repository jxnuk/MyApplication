package com.example.myapplication.database.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookingDao {
    @Query("SELECT * FROM bookings ORDER BY id DESC")
    fun getAll(): LiveData<List<Booking>>

    @Insert
    suspend fun insert(b: Booking)

    @Delete
    suspend fun delete(b: Booking)

    @Query("UPDATE bookings SET status = :newStatus WHERE id = :id")
    suspend fun updateStatus(id: Long, newStatus: String)
}