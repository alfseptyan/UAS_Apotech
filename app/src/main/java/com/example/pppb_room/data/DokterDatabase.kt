package com.example.pppb_room.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DokterEntity::class], version = 1, exportSchema = false)
abstract class DokterDatabase : RoomDatabase() {
    abstract fun dokterDao(): DokterDao

    companion object {
        @Volatile
        private var INSTANCE: DokterDatabase? = null

        fun getDatabase(context: Context): DokterDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DokterDatabase::class.java,
                    "dokter_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
