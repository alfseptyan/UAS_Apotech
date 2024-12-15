package com.example.pppb_room.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DokterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(dokter: DokterEntity)

    @Delete
    suspend fun deleteFavorite(dokter: DokterEntity)

    @Query("SELECT * FROM favorite_dokter")
    fun getAllFavorites(): LiveData<List<DokterEntity>>
}
