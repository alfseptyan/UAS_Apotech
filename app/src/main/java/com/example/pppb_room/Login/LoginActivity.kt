package com.example.pppb_room.Login


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.pppb_room.MainActivity
import com.example.pppb_room.PrefManager.PrefManager
import com.example.pppb_room.admin.AdminActivity
import com.example.pppb_room.databinding.ActivityLoginBinding
import com.example.pppb_room.model.User
import com.example.pppb_room.network.APIClient

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    private lateinit var prefManager : PrefManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val client = APIClient.getInstance()

        with(binding){
            btnLogin.setOnClickListener{
                val response = client.getAllUsers()
                response.enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        if (response.isSuccessful && response.body() != null) {
                            response.body()?.forEach { i ->
                                if(i.name == edtUsername.text.toString() && i.password == edtPassword.text.toString()){
                                    prefManager.setLoggedIn(true)
                                    prefManager.saveUsername(edtUsername.text.toString())
                                    prefManager.savePassword(edtPassword.text.toString())
                                    prefManager.saveRole(i.role)
                                    checkLoginStatus()
                                    finish()
                                }
                            }
                        } else {
                            Log.e("API Error", "Response not successful or body is null")
                        }
                    }
                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Koneksi error ${t.toString()}", Toast.LENGTH_LONG).show()
                    }
                })
                }
            txtRegister.setOnClickListener{
                val intentToRegister = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intentToRegister)
            }
        }
    }
    fun checkLoginStatus() {
        if (prefManager.isLoggedIn()) {
            if (prefManager.getRole() == "admin") {
                val intentToHome = Intent(this@LoginActivity, AdminActivity::class.java)
                startActivity(intentToHome)
            } else if (prefManager.getRole() == "user") {
                val intentToHome = Intent(this@LoginActivity, MainActivity::class.java)
                startActivity(intentToHome)
            }
        }
    }

}

