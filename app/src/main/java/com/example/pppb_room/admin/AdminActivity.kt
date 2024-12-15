package com.example.pppb_room.admin

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pppb_room.PrefManager.PrefManager
import com.example.pppb_room.R
import com.example.pppb_room.adapter.DokterAdapter
import com.example.pppb_room.databinding.ActivityAdminBinding
import com.example.pppb_room.model.Dokter
import com.example.pppb_room.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AdminActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminBinding
    private lateinit var prefManager: PrefManager
    private lateinit var dokterAdapter: DokterAdapter
    private val dokterList = mutableListOf<Dokter>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi PrefManager dengan cara Singleton
        prefManager = PrefManager.getInstance(this)

        // Setup RecyclerView
        setupRecyclerView()

        // Load data Dokter
        loadDokterData()
        binding.progressBarCategory.visibility = View.VISIBLE

        val apiService = APIClient.getInstance()
        val call = apiService.getAllDokter()
        call.enqueue(object : Callback<List<Dokter>> {
            override fun onResponse(call: Call<List<Dokter>>, response: Response<List<Dokter>>) {
                binding.progressBarCategory.visibility = View.GONE
                if (response.isSuccessful) {
                    val dokterListResponse = response.body()
                    dokterListResponse?.let {
                        dokterList.clear()
                        dokterList.addAll(it)
                        dokterAdapter.notifyDataSetChanged()
                        binding.progressBarCategory.visibility = View.GONE
                    }
                } else {
                    Toast.makeText(this@AdminActivity, "Gagal memuat data dokter", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<List<Dokter>>, t: Throwable) {
                binding.progressBarCategory.visibility = View.GONE
                Toast.makeText(this@AdminActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })


        // Tambah Dokter
        binding.tambahdokter.setOnClickListener {
            val intent = Intent(this, AdminCreateActivity::class.java)
            startActivity(intent)
        }

        // Logout
        binding.logoutadmin.setOnClickListener {
            showLogoutDialog()
        }
    }

    // Fungsi untuk memuat data dokter
    private fun loadDokterData() {
        // Mengambil instance APIService
        val apiService = APIClient.getInstance()

        // Memanggil API untuk mendapatkan data dokter
        val call = apiService.getAllDokter()

        // Menjalankan request API secara asynchronous
        call.enqueue(object : Callback<List<Dokter>> {
            override fun onResponse(call: Call<List<Dokter>>, response: Response<List<Dokter>>) {
                // Mengecek apakah response berhasil
                if (response.isSuccessful) {
                    val dokterListResponse = response.body()

                    dokterListResponse?.let {
                        dokterList.clear()  // Hapus data lama
                        dokterList.addAll(it)  // Masukkan data baru
                        dokterAdapter.notifyDataSetChanged()  // Update RecyclerView
                    }
                } else {
                    Toast.makeText(this@AdminActivity, "Gagal memuat data dokter", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Dokter>>, t: Throwable) {
                // Menangani error apabila request gagal
                Toast.makeText(this@AdminActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Setup RecyclerView dan adapter untuk Edit dan Delete
    private fun setupRecyclerView() {
        dokterAdapter = DokterAdapter(dokterList, ::onEditClick, ::onDeleteClick)
        binding.recyclerView1.layoutManager = LinearLayoutManager(this)
        binding.recyclerView1.adapter = dokterAdapter
    }

    // Fungsi ketika tombol Edit diklik
    private fun onEditClick(dokter: Dokter) {
        // Aksi Edit
        val intent = Intent(this, EditDokterActivity::class.java)
        intent.putExtra("dokter_id", dokter.id)
        startActivity(intent)
    }

    // Fungsi ketika tombol Delete diklik
    private fun onDeleteClick(dokter: Dokter) {
        // Aksi Delete
        val dialog = AlertDialog.Builder(this)
            .setTitle("Hapus Dokter")
            .setMessage("Apakah Anda yakin ingin menghapus dokter ${dokter.name}?")
            .setPositiveButton("Ya") { _, _ ->
                // Lakukan penghapusan dokter dari list atau database
                dokterList.remove(dokter)
                dokterAdapter.notifyDataSetChanged()
                Toast.makeText(this, "Dokter dihapus", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Tidak", null)
            .create()
        dialog.show()
    }

    // Dialog logout
    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah Anda yakin ingin logout?")
            .setPositiveButton("Ya") { _, _ ->
                // Clear prefManager atau logout
                Toast.makeText(this, "Logout berhasil", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}
