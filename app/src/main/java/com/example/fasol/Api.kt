package com.example.fasol

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("api/categories/")
    fun getCategory(): Call<List<Category>>

    @GET("api/subcategories/{id}")
    fun getSubcategories(@Path("id") subId: Int): Call<List<Subcategory>>
}