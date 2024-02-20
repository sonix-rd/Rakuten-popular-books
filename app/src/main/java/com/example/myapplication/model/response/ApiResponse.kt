package com.example.myapplication.model.response

interface ApiResponse {
    val errorCode: Int
    val errorMessage: String
}