package io.android.core.data.source.remote

import io.android.core.data.source.remote.network.ApiResponse
import io.android.core.data.source.remote.network.ApiService
import io.android.core.data.source.remote.response.GameListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemoteDataSource(private val apiService: ApiService) {
    suspend fun getGames(): Flow<ApiResponse<GameListResponse>> {
        return flow {
            try {
                val response = apiService.getGames()
                if (response.amiibo?.isEmpty() == true) emit(ApiResponse.Empty)
                else emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}