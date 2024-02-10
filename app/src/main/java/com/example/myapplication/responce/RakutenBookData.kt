package com.example.myapplication.responce

data class RakutenBookData(
    val GenreInformation: List<Any>,
    val Items: List<Item>,
    val carrier: Int,
    val count: Int,
    val first: Int,
    val hits: Int,
    val last: Int,
    val page: Int,
    val pageCount: Int
)