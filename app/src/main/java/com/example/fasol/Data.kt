package com.example.fasol

data class Category(
    val id: Int,
    val name: String,
    val representation: String
)

data class Subcategory(
    val id: Int,
    val category: String,
    val name: String
)