package com.example.viewmodel.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.example.viewmodel.inerface.RetrofitClient

class TodoViewModel : ViewModel() {
    private val _todoTitle = MutableStateFlow("Chargement...")
    val todoTitle: StateFlow<String> = _todoTitle

    init {
        fetchTodo()
    }

    private fun fetchTodo() {
        viewModelScope.launch {
            try {
                val task = RetrofitClient.api.getTask()
                _todoTitle.value = task.title
            } catch (e: Exception) {
                _todoTitle.value = "Erreur : ${e.message}"
            }
        }
    }
}