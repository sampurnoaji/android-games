package io.android.core.data.source.remote.network

import io.android.core.data.source.remote.response.GameListResponse
import retrofit2.http.GET

interface ApiService {
    @GET("amiibo")
    suspend fun getGames(): GameListResponse
}