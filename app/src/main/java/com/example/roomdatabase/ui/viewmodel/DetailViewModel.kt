package com.example.roomdatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.model.Mahasiswa
import com.example.roomdatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed class DetailUiState {
    data class Success(val mahasiswa: Mahasiswa) : DetailUiState()
    object Error : DetailUiState()
    object Loading : DetailUiState()
}

class DetailViewModel(private val repository: MahasiswaRepository) : ViewModel() {
    var detailUIState: DetailUiState by mutableStateOf(DetailUiState.Loading)
        private set

    fun getDetailMahasiswa(nim: String) {
        viewModelScope.launch {
            detailUIState = DetailUiState.Loading
            detailUIState = try {
                val mahasiswa = repository.getMahasiswaById(nim)
                DetailUiState.Success(mahasiswa)
            } catch (e: IOException) {
                DetailUiState.Error
            } catch (e: HttpException) {
                DetailUiState.Error
            }
        }
    }

    fun updateMahasiswa(nim: String, mahasiswa: Mahasiswa) {
        viewModelScope.launch {
            try {
                repository.updateMahasiswa(nim, mahasiswa)
                getDetailMahasiswa(nim)
            } catch (e: IOException) {
                detailUIState = DetailUiState.Error
            } catch (e: HttpException) {
                detailUIState = DetailUiState.Error
            }
        }
    }

    fun deleteMahasiswa(nim: String) {
        viewModelScope.launch {
            try {
                repository.deleteMahasiswa(nim)
            } catch (e: IOException) {
                detailUIState = DetailUiState.Error
            } catch (e: HttpException) {
                detailUIState = DetailUiState.Error
            }
        }
    }
}