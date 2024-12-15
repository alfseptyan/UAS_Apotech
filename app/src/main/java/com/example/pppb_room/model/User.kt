package com.example.pppb_room.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("role")
    val role: String
)

