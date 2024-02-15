package com.example.myapplication.model.response

import com.example.myapplication.model.response.rakutenbookdata.ApiResponse
import com.example.myapplication.model.response.rakutenbookdata.Item
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RakutenBookResponse(
    /** booksの一覧 */
    val Items: List<Item>,
    val carrier: Int,
    val count: Int,
    val first: Int,
    val hits: Int,
    val last: Int,
    val page: Int,
    val pageCount: Int,
    @SerialName("error_code")
    override val errorCode: Int = 0,
    @SerialName("error_message")
    override val errorMessage: String = ""
): ApiResponse