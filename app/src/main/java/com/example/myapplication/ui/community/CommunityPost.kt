package com.example.myapplication.ui.community

data class CommunityPost(
    val body: String,
    val imageRes: Int,
    val phone: String = "07156524522",
    val defaultRating: Float = 4f
)
