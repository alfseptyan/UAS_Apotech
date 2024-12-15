package com.example.pppb_room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pppb_room.databinding.ItemUserBinding
import com.example.pppb_room.model.Dokter
import com.example.pppb_room.network.APIService

class UserDokterAdapter(
    private val DokterList: ArrayList<Dokter>,
    private val client: APIService

) : RecyclerView.Adapter<UserDokterAdapter.ItemUserViewHolder>() {
    inner class ItemUserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(Dokter: Dokter) {

            with(binding)
            {
                textName.text = Dokter.name
                textSpesialis.text = Dokter.spesialis
                textHarga.text = Dokter.harga

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemUserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemUserViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return DokterList.size
    }

    override fun onBindViewHolder(holder: ItemUserViewHolder, position: Int) {
        holder.bind(DokterList[position])
    }

}