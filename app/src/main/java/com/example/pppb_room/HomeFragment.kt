package com.example.pppb_room

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pppb_room.adapter.DokterAdapter
import com.example.pppb_room.databinding.FragmentHomeBinding
import com.example.pppb_room.model.Dokter
import com.example.pppb_room.network.APIClient
import com.example.pppb_room.network.APIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var binding: FragmentHomeBinding
    private lateinit var adapter: UserDokterAdapter
    private lateinit var client: APIService
    private lateinit var DokterList:ArrayList<Dokter>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        DokterList = ArrayList()
        adapter = UserDokterAdapter(DokterList, APIClient.getInstance())

        fatchDokterList()
        binding.recyclerViewTopDoctors.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTopDoctors.adapter = adapter

        return binding.root



    }
    private fun fatchDokterList() {
        val client = APIClient.getInstance()
        val response = client.getAllDokter()

        response.enqueue(object : Callback<List<Dokter>> {
            @SuppressLint("NotifyDataSetChanged")

            override fun onResponse(call: Call<List<Dokter>>, response: Response<List<Dokter>>) {
                if (response.isSuccessful && response.body() != null) {
                    // Tambahkan data ke dalam list dan perbarui adapter
                    binding.progressBarTopDoctors.visibility = android.view.View.GONE
                    DokterList.clear()
                    DokterList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()

                } else {
                    Toast.makeText(
                        requireContext(),
                        "Gagal memuat data latihan",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<List<Dokter>>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error: ${t.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}