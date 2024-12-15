package com.example.pppb_room

import androidx.room.Entity
import androidx.room.PrimaryKey

data class WishlistItem(

    @PrimaryKey(autoGenerate = true) // ID otomatis meningkat
    val id: Int = 0,
    val name: String,               // Nama dokter/item
    val specialty: String,          // Spesialisasi dokter/item
    val isLoved: Boolean = true     // Status love


)
