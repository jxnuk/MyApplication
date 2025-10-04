package com.example.myapplication.database.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookings")
data class Booking(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val bookingNumber: String,
    val userName: String,
    val restaurantName: String,
    val dateTimeText: String,
    val phone: String,
    val imageResName: String,
    val status: String = "ACTIVE"
)
