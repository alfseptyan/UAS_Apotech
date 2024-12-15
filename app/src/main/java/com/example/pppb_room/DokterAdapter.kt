package com.example.pppb_room.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pppb_room.admin.EditDokterActivity
import com.example.pppb_room.databinding.ItemDokterBinding
import com.example.pppb_room.model.Dokter
import com.example.pppb_room.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DokterAdapter(
    private val dokterList: MutableList<Dokter>,
    private val onEditClick: (Dokter) -> Unit,
    private val onDeleteClick: (Dokter) -> Unit
) : RecyclerView.Adapter<DokterAdapter.DokterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DokterViewHolder {
        val binding = ItemDokterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DokterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DokterViewHolder, position: Int) {
        val dokter = dokterList[position]
        holder.bind(dokter)
    }

    override fun getItemCount(): Int = dokterList.size

    inner class DokterViewHolder(private val binding: ItemDokterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(dokter: Dokter) {
            binding.textName.text = dokter.name
            binding.textSpesialis.text = dokter.spesialis
            binding.textHarga.text = dokter.harga.toString()

            binding.edit.setOnClickListener {
                onEditClick(dokter)

                binding.delete.setOnClickListener {
                    onDeleteClick(dokter)
                }

                // Set listener untuk Edit
                binding.edit.setOnClickListener {
                    val intent = Intent(itemView.context, EditDokterActivity::class.java).apply {
                        putExtra("dokter_id", dokter.id)
                        putExtra("name", dokter.name)
                        putExtra("spesialis", dokter.spesialis)
                        putExtra("harga", dokter.harga)
                        putExtra("date", dokter.date)
                    }
                    itemView.context.startActivity(intent)
                }

                // Set listener untuk Delete
                binding.delete.setOnClickListener {
                    val dokterId = dokter.id
                    if (dokterId.isNullOrEmpty()) {
                        Toast.makeText(
                            itemView.context,
                            "ID Dokter tidak valid, tidak dapat menghapus.",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }

                    val APIService = APIClient.getInstance()
                    val response = APIService.deleteDokter(dokterId)

                    response?.enqueue(object : Callback<Dokter> {
                        override fun onResponse(call: Call<Dokter>, response: Response<Dokter>) {
                            if (response.isSuccessful) {
                                // Menampilkan Toast jika penghapusan berhasil
                                Toast.makeText(
                                    itemView.context,
                                    "Dokter ${dokter.name} berhasil dihapus",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Menghapus dokter dari list dan memperbarui RecyclerView
                                val position = adapterPosition
                                if (position != RecyclerView.NO_POSITION) {
                                    dokterList.removeAt(position)
                                    notifyItemRemoved(position)
                                }

                            } else {
                                Toast.makeText(
                                    itemView.context,
                                    "Gagal menghapus dokter. Coba lagi.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<Dokter>, t: Throwable) {
                            // Menangani kegagalan request API
                            Toast.makeText(
                                itemView.context,
                                "Error: ${t.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })
                }
            }
        }
    }
}