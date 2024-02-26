package com.example.myapplication.model.request

import com.example.myapplication.model.request.body.ApiRequestBody
import com.example.myapplication.model.response.RakutenBookResponse
import io.ktor.http.HttpMethod

class BooksAcquisitionRequest(private var searchKeyword: String? = null) :
    RakutenApiRequest<RakutenBookResponse> {
    private val applicationId: String = "1053263810010956500"
    override val path: String = buildPath()
    private fun buildPath(): String {
        return if (searchKeyword.isNullOrEmpty()) {
            "/services/api/BooksBook/Search/20170404?format=json&booksGenreId=001004008&applicationId=$applicationId"
        } else {
            "/services/api/BooksBook/Search/20170404?format=json&title=${searchKeyword}&booksGenreId=001004008&applicationId=$applicationId"
        }
    }
    override val method: HttpMethod = HttpMethod.Get
    override val body: ApiRequestBody? = null
}