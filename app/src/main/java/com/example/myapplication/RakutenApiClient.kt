package com.example.myapplication

import android.content.res.Resources
import android.util.Log
import com.example.myapplication.model.request.RakutenApiRequest
import com.example.myapplication.model.response.BaseResponse
import com.example.myapplication.model.response.rakutenbookdata.ApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.HttpResponseValidator
import io.ktor.client.features.HttpTimeout
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.request
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.http.takeFrom
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
object RakutenApiClient {
    suspend inline fun <reified T : ApiResponse> call(request: RakutenApiRequest<T>, noinline success: (T) -> Unit, noinline failure: (String) -> Unit
    ) {
        try {
            val response = client.request<T> {
                method = request.method
                request.body?.let {
                    body = it
                }
                contentType(ContentType.Application.Json)
                header(HttpHeaders.CacheControl, "no-cache")
                url {
                    takeFrom(BASE_URL)
                    encodedPath = request.path
                }
            }
            Log.d("RakutenApiClient", "Response received: $response")
            withContext(Dispatchers.Main) {
                success.invoke(response)
            }
        } catch (t: Throwable) {
            val message = t.message ?: Resources.getSystem().getString(R.string.msg_unknown_error)
            Log.d(request.javaClass.name, message)
            withContext(Dispatchers.Main) {
                failure.invoke(message)
            }
        }
    }
    val client = HttpClient(Android) {
        install(HttpTimeout) {
            requestTimeoutMillis = 10000
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("bookApi", message)
                }
            }
            level = LogLevel.HEADERS
        }
        HttpResponseValidator {
            validateResponse {
                if (it.status.isSuccess()) return@validateResponse
                val json = kotlinx.serialization.json.Json.Default
                val bodyText = it.content.readRemaining(headerSizeHint = 4096, limit = 4096 * 30).readText()
                val errorResponse = try {
                    json.decodeFromString(BaseResponse.serializer(), bodyText)
                } catch (t: Throwable) {
                    Log.d(javaClass.name, t.message ?: "")
                    BaseResponse(400, Resources.getSystem().getString(R.string.msg_unknown_error))
                }
                throw ApiException(errorResponse)
            }
        }
    }
    const val BASE_URL = "https://app.rakuten.co.jp"
}