package com.example.pppb_room.admin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pppb_room.databinding.ActivityEditDokterBinding
import com.example.pppb_room.model.Dokter
import com.example.pppb_room.network.APIClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditDokterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditDokterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditDokterBinding.inflate(layoutInflater)
        binding = ActivityEditDokterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengambil data dokter dari Intent
        val id = intent.getStringExtra("_id")
        val name = intent.getStringExtra("name")
        val spesialis = intent.getStringExtra("spesialis")
        val harga = intent.getStringExtra("harga")
        val date = intent.getStringExtra("date")

        // Mengisi EditText dengan data yang sudah ada
        with(binding) {
            editTextName.setText(name)
            editTextSpesialis.setText(spesialis)
            editTextHarga.setText(harga)
            editTextDate.setText(date)

            // Ketika tombol Simpan ditekan, simpan perubahan ke server
            buttonSave.setOnClickListener {
                val updatedName = editTextName.text.toString()
                val updatedSpesialis = editTextSpesialis.text.toString()
                val updatedHarga = editTextHarga.text.toString()
                val updatedDate = editTextDate.text.toString()

                // Validasi input
                if (validateInput(updatedName, updatedSpesialis, updatedHarga, updatedDate)) {
                    // Update data dokter
                    if (!id.isNullOrEmpty()) { // Mengecek apakah id valid (bukan null atau kosong)
                        updateDokter(id, updatedName, updatedSpesialis, updatedHarga, updatedDate)
                    } else {
                        Toast.makeText(
                            this@EditDokterActivity,
                            "ID dokter tidak ditemukan",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@EditDokterActivity,
                        "Data tidak boleh kosong",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun validateInput(
        name: String,
        spesialis: String,
        harga: String,
        date: String
    ): Boolean {
        // Pastikan semua input tidak kosong
        return name.isNotEmpty() && spesialis.isNotEmpty() && harga.isNotEmpty() && date.isNotEmpty()
    }

    private fun updateDokter(
        id: String?,
        name: String,
        spesialis: String,
        harga: String,
        date: String
    ) {
        val apiService = APIClient.getInstance()
        val dokter = Dokter(
            id = null,
            name = name,
            spesialis = spesialis,
            harga = harga,
            date = date
        )

        // Panggil API untuk update data dokter
        val response = apiService.updateDokter(id.toString(), dokter)
        response.enqueue(object : Callback<Dokter> {
            override fun onResponse(call: Call<Dokter>, response: Response<Dokter>) {
                if (response.isSuccessful) {
                    // Jika update berhasil
                    Toast.makeText(
                        this@EditDokterActivity,
                        "Dokter berhasil diperbarui",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@EditDokterActivity, AdminActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Jika gagal, tampilkan error message dari server
                    val errorResponse = response.errorBody()?.string()
                    Toast.makeText(
                        this@EditDokterActivity,
                        "Gagal memperbarui dokter: $errorResponse",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Dokter>, t: Throwable) {
                // Menangani kegagalan koneksi atau kesalahan lain
                Toast.makeText(
                    this@EditDokterActivity,
                    "Gagal terhubung ke server: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
