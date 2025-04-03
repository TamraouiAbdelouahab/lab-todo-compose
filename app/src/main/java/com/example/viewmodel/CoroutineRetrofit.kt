package com.example.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.viewmodel.inerface.RetrofitClient
import com.example.viewmodel.ui.theme.ViewModelTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CoroutineRetrofit : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoScreen()
        }
    }
}

@Composable
fun TodoScreen() {
    var title by remember { mutableStateOf("Chargement...") }

    LaunchedEffect(Unit) {
        try {
            val task = withContext(Dispatchers.IO) {
                RetrofitClient.api.getTask()
            }
            title = task.title
        } catch (e: Exception) {
            title = "Erreur : ${e.message}"
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, style = MaterialTheme.typography.titleLarge)
        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCoroutineRetrofit() {
    ViewModelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            TodoScreen()
        }
    }
}