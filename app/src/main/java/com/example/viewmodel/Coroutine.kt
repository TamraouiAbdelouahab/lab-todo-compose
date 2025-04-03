package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodel.ui.theme.ViewModelTheme
import kotlinx.coroutines.delay

class Coroutine : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MauvaisCompteur()
        }
    }
}

@Composable
fun MauvaisCompteur() {
    var compteur by remember { mutableStateOf(0) }

    Thread {
        while (true) {
            Thread.sleep(1000)
            compteur++
        }
    }.start()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Compteur : $compteur", style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun CompteurAvecCoroutine() {
    var compteur by remember { mutableStateOf(0) }

    // ✅ Coroutine sûre et fluide
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000) // Attente non-bloquante
            compteur++  // Compose gère la mise à jour de l'UI
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Compteur : $compteur", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { compteur = 0 }) {
            Text("Réinitialiser")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreviewCoroutine() {
    ViewModelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            CompteurAvecCoroutine()
        }

    }
}