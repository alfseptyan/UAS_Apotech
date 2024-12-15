package com.example.pppb_room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_dokter")
data class DokterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val speciality: String
)
