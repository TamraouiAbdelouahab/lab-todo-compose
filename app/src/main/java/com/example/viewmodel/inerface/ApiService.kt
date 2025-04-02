package com.example.viewmodel.inerface

import retrofit2.Call
import retrofit2.http.GET
import com.example.viewmodel.data.Task
import retrofit2.http.Path

interface ApiService {
    @GET("todos/1")
    //fun getTaskById(@Path("id") id: Int): Call<Task>
    fun getTaskById(): Call<Task>
}