package com.example.pppb_room.admin

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pppb_room.R
import com.example.pppb_room.databinding.ActivityAdminCreateBinding
import com.example.pppb_room.model.Dokter
import com.example.pppb_room.network.APIClient
import com.example.pppb_room.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AdminCreateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminCreateBinding
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Listener untuk membuka DatePicker
        binding.imageViewDatePicker.setOnClickListener {
            showDatePicker()
        }

        // Listener untuk tombol Submit
        binding.buttonSubmit.setOnClickListener {
            submitData()
        }
    }

    private fun showDatePicker() {
        val datePickerDialog = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                // Set tanggal yang dipilih ke TextView
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                binding.textViewSelectedDate.text = dateFormat.format(calendar.time)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun submitData() {
        // Ambil input dari pengguna melalui View Binding
        val name = binding.editTextName.text.toString().trim()
        val spesialis = binding.editTextSpesialis.text.toString().trim()
        val date = binding.textViewSelectedDate.text.toString().trim()
        val hargaText = binding.editTextHarga.text.toString().trim()

        // Validasi input
        if (name.isEmpty() || spesialis.isEmpty() || date.isEmpty() || hargaText.isEmpty()) {
            Toast.makeText(this, "Harap isi semua data!", Toast.LENGTH_SHORT).show()
            return
        }

        val harga = hargaText.toIntOrNull()
        if (harga == null || harga <= 0) {
            Toast.makeText(this, "Harga harus berupa angka positif!", Toast.LENGTH_SHORT).show()
            return
        }

        // Buat objek Dokter
        val dokter = Dokter(
            id = null, // ID akan di-generate oleh server
            name = name,
            spesialis = spesialis,
            date = date,
            harga = harga.toString()

        )

        // Kirim data ke server menggunakan Retrofit
        val apiService = APIClient.getInstance()
        apiService.createDokter(dokter).enqueue(object : Callback<Dokter> {
            override fun onResponse(call: Call<Dokter>, response: Response<Dokter>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@AdminCreateActivity, "Data berhasil ditambahkan!", Toast.LENGTH_SHORT).show()
                    finish() // Kembali ke halaman sebelumnya
                } else {
                    Toast.makeText(this@AdminCreateActivity, "Gagal menambahkan data!", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<Dokter>, t: Throwable) {
                Toast.makeText(this@AdminCreateActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
