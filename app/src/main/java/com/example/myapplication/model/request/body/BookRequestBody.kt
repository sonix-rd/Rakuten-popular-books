package com.example.myapplication.model.request.body

data class BookRequestBody(
    val title: String?, // 書籍のタイトルは省略可能なので、Nullableとします
    val booksGenreId: String,
    val applicationId: String
): ApiRequestBody