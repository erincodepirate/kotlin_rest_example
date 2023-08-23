package com.raccooncode.ktorclientandroidtheme.data.remote

import android.util.Log
import com.raccooncode.ktorclientandroidtheme.data.remote.dto.PostRequest
import com.raccooncode.ktorclientandroidtheme.data.remote.dto.PostResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.contentType
import io.ktor.http.ContentType
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class PostsServiceImpl(
    val client: HttpClient
) : PostsService {
    override suspend fun getPosts(): List<PostResponse> {
        return try {
            val result: HttpResponse = client.get(HttpRoutes.POSTS)
            val body = result.body<List<PostResponse>>()
            body
        } catch(e: RedirectResponseException) {
            // 3xx response
            println("Error ${e.response.status.description}")
            emptyList<PostResponse>()
        } catch(e: ClientRequestException) {
            // 4xx response
            println("Error ${e.response.status.description}")
            emptyList<PostResponse>()
        } catch(e: ServerResponseException) {
            println("Error ${e.response.status.description}")
            emptyList<PostResponse>()
        } catch(e: Exception) {
            println("Error")
            emptyList<PostResponse>()
        }
    }

    override suspend fun createPost(postRequest: PostRequest): PostResponse? {
        return try {
            val result: HttpResponse = client.post(HttpRoutes.POSTS) {
                contentType(ContentType.Application.Json)
                setBody(postRequest)
            }
            val body = result.body<PostResponse>()
            Log.d("something", body.toString())
            body
        } catch(e: RedirectResponseException) {
            // 3xx response
            println("Error ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            // 4xx response
            println("Error ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            println("Error ${e.response.status.description}")
            null
        } catch(e: Exception) {
            println("Error")
            null
        }
    }
}