package com.example.pppb_room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pppb_room.PrefManager.PrefManager
import com.example.pppb_room.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var prefManager: PrefManager
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefManager = PrefManager.getInstance(requireContext())

        // Setup RecyclerView
        setupRecyclerView()

        // Setup Logout Button
        binding.logoutButton.setOnClickListener {
            showLogoutDialog()
        }
    }

    private fun setupRecyclerView() {
        val options = listOf("Account Settings", "Notifications", "Privacy Policy", "Help & Support")

    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                // Handle logout logic (e.g., clear session, navigate to login screen)
                Toast.makeText(requireContext(), "Logged out successfully", Toast.LENGTH_SHORT).show()
                prefManager.clear()
                requireActivity().finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
