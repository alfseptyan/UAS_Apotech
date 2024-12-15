package com.example.pppb_room.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Dokter(
    @SerializedName("_id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("spesialis")
    val spesialis: String?,
    @SerializedName("date")
    val date: String?,
    @SerializedName("harga")
    val harga: String?


) : Parcelable {
    // Constructor untuk membaca data dari Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    // Menulis data ke dalam Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(spesialis)
        parcel.writeString(date)
        parcel.writeString(harga)
    }

    // Tidak memerlukan deskripsi khusus
    override fun describeContents(): Int = 0

    // Companion Object untuk Parcelable.Creator
    companion object CREATOR : Parcelable.Creator<Dokter> {
        override fun createFromParcel(parcel: Parcel): Dokter = Dokter(parcel)
        override fun newArray(size: Int): Array<Dokter?> = arrayOfNulls(size)
    }
}
