package com.example.myapplication.model.request

import com.example.myapplication.model.request.body.ApiRequestBody
import com.example.myapplication.model.response.RakutenBookResponse
import io.ktor.http.HttpMethod

class BooksAcquisitionRequest : RakutenApiRequest<RakutenBookResponse>{
    override val path: String = "/services/api/BooksBook/Search/20170404?format=json&title=%E5%A4%AA%E9%99%BD&booksGenreId=001004008&applicationId=1053263810010956500"
    override val method: HttpMethod = HttpMethod.Get
    override val body: ApiRequestBody? = null
}