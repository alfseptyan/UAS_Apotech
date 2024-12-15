package com.example.pppb_room.data

import androidx.lifecycle.LiveData

class DokterRepository(private val dokterDao: DokterDao) {
    val allFavorites: LiveData<List<DokterEntity>> = dokterDao.getAllFavorites()

    suspend fun insert(dokter: DokterEntity) {
        dokterDao.insertFavorite(dokter)
    }

    suspend fun delete(dokter: DokterEntity) {
        dokterDao.deleteFavorite(dokter)
    }
}
