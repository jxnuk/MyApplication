package com.example.myapplication.ui.booking

import android.app.Application
import androidx.lifecycle.*
import com.example.myapplication.database.AppDatabase
import com.example.myapplication.database.data.Booking
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookingViewModel(app: Application) : AndroidViewModel(app) {
    private val dao = AppDatabase.get(app).bookingDao()
    val bookings = dao.getAll()

    fun addBooking(b: Booking) = viewModelScope.launch(Dispatchers.IO) {
        dao.insert(b)
    }

    fun cancelBooking(b: Booking) = viewModelScope.launch(Dispatchers.IO) {
        // You can update status or delete; using delete for now
        dao.delete(b)
    }
}
