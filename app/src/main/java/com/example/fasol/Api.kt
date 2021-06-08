package com.example.fasol

import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("api/categories/")
    fun getCategory(): Call<List<Category>>

    @GET("api/subcategories/")
    fun getSubcategories(): Call<List<Subcategory>>
}