package com.example.pppb_room.network

import com.example.pppb_room.model.Dokter
import com.example.pppb_room.model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIService {
    @GET("User")
    fun getAllUsers(): Call<List<User>>
    @POST("User")
    fun createUser(@Body user: User): Call<User>


    @GET("Dokter")
    fun getAllDokter(): Call<List<Dokter>>
    @POST("Dokter")
    fun createDokter(@Body dokter: Dokter): Call<Dokter>

    @PUT("dokter/{id}")
    fun updateDokter(@Path("id") id: String, @Body dokter: Dokter): Call<Dokter>
    @GET("Dokter/{id}")
    fun getDokterById(@Path("id") id: Int): Call<Dokter>
    @DELETE("Dokter/{id}")
    fun deleteDokter(@Path("id") id: String?): Call<Dokter>



}