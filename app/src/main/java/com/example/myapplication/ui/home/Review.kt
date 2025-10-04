package com.example.myapplication.ui.home

data class Review(
    val title: String,
    val body: String,
    val author: String,
    val imageRes: Int? = null
)
