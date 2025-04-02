package com.example.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.viewmodel.ui.theme.ViewModelTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.viewmodel.viewModel.CounterViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.viewmodel.data.Task
import com.example.viewmodel.inerface.RetrofitClient

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ViewModelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CounterScreen()
                }
            }
        }
    }
}

@Composable
fun CounterScreen(counterViewModel: CounterViewModel = viewModel()) {
    val count by counterViewModel.count.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Compteur: $count", style = MaterialTheme.typography.headlineMedium)

        Row(modifier = Modifier.padding(16.dp)) {
            Button(onClick = { counterViewModel.decrement() }, modifier = Modifier.padding(8.dp)) {
                Text(text = "-")
            }

            Button(onClick = { counterViewModel.increment() }, modifier = Modifier.padding(8.dp)) {
                Text(text = "+")
            }
        }
    }
}

// Todo function
@Composable
fun TodoApp() {
    var titre by remember { mutableStateOf("Chargement...") }

    LaunchedEffect(Unit) {
        val call = RetrofitClient.api.getTaskById()
        call.enqueue(object : Callback<Task> {
            override fun onResponse(call: Call<Task>, response: Response<Task>) {
                if (response.isSuccessful) {
                    val todo = response.body()
                    if (todo != null) {
                        titre = todo.title
                    } else {
                        titre = "Réponse vide"
                    }
                } else {
                    titre = "Erreur HTTP ${response.code()}"
                }
            }
            override fun onFailure(call: Call<Task>, t: Throwable) {
                titre = "Erreur réseau : ${t.message}"
            }
        })
    }

    // UI simple
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = titre, style = MaterialTheme.typography.titleLarge)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ViewModelTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ){
            TodoApp()
        }

    }
}