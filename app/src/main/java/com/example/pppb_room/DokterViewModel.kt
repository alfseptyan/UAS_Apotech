package com.example.pppb_room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.pppb_room.data.DokterDatabase
import com.example.pppb_room.data.DokterEntity
import com.example.pppb_room.data.DokterRepository
import kotlinx.coroutines.launch

class DokterViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DokterRepository
    val allFavorites: LiveData<List<DokterEntity>>

    init {
        val dokterDao = DokterDatabase.getDatabase(application).dokterDao()
        repository = DokterRepository(dokterDao)
        allFavorites = repository.allFavorites
    }

    fun insert(dokter: DokterEntity) = viewModelScope.launch {
        repository.insert(dokter)
    }

    fun delete(dokter: DokterEntity) = viewModelScope.launch {
        repository.delete(dokter)
    }
}
