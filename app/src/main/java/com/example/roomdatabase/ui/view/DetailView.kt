package com.example.roomdatabase.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomdatabase.R
import com.example.roomdatabase.model.Mahasiswa
import com.example.roomdatabase.ui.costumewidget.CustomTopAppBar
import com.example.roomdatabase.ui.navigation.DestinasiNavigasi
import com.example.roomdatabase.ui.viewmodel.DetailUiState
import com.example.roomdatabase.ui.viewmodel.DetailViewModel
import com.example.roomdatabase.ui.viewmodel.PenyediaViewModel

// Destinasi untuk navigasi ke DetailScreen
object DestinasiDetail : DestinasiNavigasi {
    override val route = "detail/{nim}"
    override val titleRes = "Detail Mahasiswa"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    nim: String,  // nim sebagai parameter dari rute
    navigateBack: () -> Unit,
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)  // ViewModel dengan factory
) {
    val detailUiState = viewModel.detailUIState

    // Mengambil data mahasiswa berdasarkan nim
    LaunchedEffect(nim) {
        viewModel.getDetailMahasiswa(nim)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = "Detail Mahasiswa",
                canNavigateBack = true,
                navigateUp = navigateBack // Navigasi kembali
            )
        },
        content = { innerPadding ->
            // Menampilkan konten berdasarkan state UI
            DetailContent(
                detailUiState = detailUiState,
                modifier = Modifier.padding(innerPadding),
                onDeleteClick = {
                    viewModel.deleteMahasiswa(nim)  // Menghapus mahasiswa berdasarkan nim
                    navigateBack()
                }
            )
        }
    )
}

@Composable
fun DetailContent(
    detailUiState: DetailUiState,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    when (detailUiState) {
        is DetailUiState.Loading -> OnLoading2(modifier = modifier.fillMaxSize())
        is DetailUiState.Error -> OnError2(retryAction = {}, modifier = modifier.fillMaxSize())
        is DetailUiState.Success -> DetailCard(
            mahasiswa = detailUiState.mahasiswa,
            modifier = modifier,
            onDeleteClick = onDeleteClick
        )
    }
}

@Composable
fun DetailCard(
    mahasiswa: Mahasiswa,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = mahasiswa.nama,
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Hapus Mahasiswa"
                    )
                }
            }
            Text(
                text = "NIM: ${mahasiswa.nim}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Kelas: ${mahasiswa.kelas}",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "Alamat: ${mahasiswa.alamat}",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun OnLoading2(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.ic_launcher_background),
            contentDescription = stringResource(R.string.loading)
        )
    }
}

@Composable
fun OnError2(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = null
        )
        Text(
            text = stringResource(R.string.loading_failed),
            modifier = Modifier.padding(16.dp)
        )
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}
