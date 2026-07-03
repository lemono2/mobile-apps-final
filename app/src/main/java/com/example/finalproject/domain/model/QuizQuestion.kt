package com.example.finalproject.domain.model

data class QuizQuestion(
    val id: String,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val category: String,
)
