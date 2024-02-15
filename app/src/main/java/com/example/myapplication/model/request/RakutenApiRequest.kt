package com.example.myapplication.model.request

import com.example.myapplication.model.request.body.ApiRequestBody
import com.example.myapplication.model.response.rakutenbookdata.ApiResponse
import io.ktor.http.HttpMethod

interface RakutenApiRequest<T : ApiResponse> {
    val path: String
    val method: HttpMethod
    val body: ApiRequestBody?
}