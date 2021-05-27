package com.example.fasol

import com.example.fasol.category.Category
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("api/categories/")
    fun getCategory(): Call<List<Category>>


}