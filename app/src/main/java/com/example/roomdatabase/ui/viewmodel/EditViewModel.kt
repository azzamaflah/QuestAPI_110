package com.example.roomdatabase.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdatabase.model.Mahasiswa
import com.example.roomdatabase.repository.MahasiswaRepository
import kotlinx.coroutines.launch

class EditViewModel(private val mhs: MahasiswaRepository) : ViewModel() {

    var uiState by mutableStateOf(InsertUiState())
        private set

    fun updateEditMhsState(insertUiEvent: InsertUiEvent) {
        uiState = InsertUiState(insertUiEvent = insertUiEvent)
    }

    fun loadMahasiswaDetail(nim: String) {
        viewModelScope.launch {
            try {
                val mahasiswa = mhs.getMahasiswaById(nim)
                uiState = mahasiswa.toUiStateMhs()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateMhs() {
        viewModelScope.launch {
            try {
                val mahasiswa = uiState.insertUiEvent.toMhs()
                mhs.updateMahasiswa(mahasiswa.nim, mahasiswa)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

data class InsertUiState2(
    val insertUiEvent: InsertUiEvent2 = InsertUiEvent2()
)

data class InsertUiEvent2(
    val nim: String = "",
    val nama: String = "",
    val alamat: String = "",
    val jenisKelamin: String = "",
    val kelas: String = "",
    val angkatan: String = ""
)

fun InsertUiEvent.toMhs2(): Mahasiswa = Mahasiswa(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)

fun Mahasiswa.toUiStateMhs2(): InsertUiState = InsertUiState(
    insertUiEvent = toInsertUiEvent()
)

fun Mahasiswa.toInsertUiEvent2(): InsertUiEvent = InsertUiEvent(
    nim = nim,
    nama = nama,
    alamat = alamat,
    jenisKelamin = jenisKelamin,
    kelas = kelas,
    angkatan = angkatan
)
