package com.example.myapplication.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.database.data.UserDao
import com.example.myapplication.database.data.UserEntity
import com.example.myapplication.database.data.Booking
import com.example.myapplication.database.data.BookingDao

@Database(
    entities = [
        UserEntity::class,
        Booking::class
    ],
    version = 2,                   // ⬅️ bump version
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        // Migration: add bookings table (wasn’t present in v1)
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `bookings` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `bookingNumber` TEXT NOT NULL,
                        `userName` TEXT NOT NULL,
                        `restaurantName` TEXT NOT NULL,
                        `dateTimeText` TEXT NOT NULL,
                        `phone` TEXT NOT NULL,
                        `imageResName` TEXT NOT NULL,
                        `status` TEXT NOT NULL
                    )
                    """.trimIndent()
                )
                // add indexes later if you need (e.g., on bookingNumber)
            }
        }

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"               // keep the same DB name
                )
                    .addMigrations(MIGRATION_1_2)   // ⬅️ keep data; create new table
                    // .fallbackToDestructiveMigration() // (optional DEV-only shortcut)
                    .build().also { INSTANCE = it }
            }
    }
}
