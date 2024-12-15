package com.example.pppb_room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pppb_room.data.DokterEntity

class DokterUserAdapter(
    private val dokterList: List<DokterEntity>,
    private val onFavoriteClicked: (DokterEntity) -> Unit
) : RecyclerView.Adapter<DokterUserAdapter.DokterViewHolder>() {

    class DokterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.textName)
        val specialityTextView: TextView = itemView.findViewById(R.id.textSpesialis)
        val favoriteIcon: ImageView = itemView.findViewById(R.id.btnFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_dokter, parent, false)
        return DokterViewHolder(view)
    }

    override fun onBindViewHolder(holder: DokterViewHolder, position: Int) {
        val dokter = dokterList[position]
        holder.nameTextView.text = dokter.name
        holder.specialityTextView.text = dokter.speciality

        holder.favoriteIcon.setOnClickListener {
            onFavoriteClicked(dokter)
        }
    }

    override fun getItemCount() = dokterList.size
}
