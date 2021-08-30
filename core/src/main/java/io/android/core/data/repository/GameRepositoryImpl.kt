package io.android.core.data.repository

import io.android.core.data.NetworkBoundResource
import io.android.core.data.Resource
import io.android.core.data.source.local.LocalDataSource
import io.android.core.data.source.remote.RemoteDataSource
import io.android.core.data.source.remote.network.ApiResponse
import io.android.core.data.source.remote.response.GameListResponse
import io.android.core.domain.model.Game
import io.android.core.domain.repository.GameRepository
import io.android.core.util.AppExecutors
import io.android.core.util.mapper.GameMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GameRepositoryImpl(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
) : GameRepository {

    override fun getGames(): Flow<Resource<List<Game>>> {
        return object : NetworkBoundResource<List<Game>, GameListResponse>() {
            override fun loadFromDB(): Flow<List<Game>> {
                return localDataSource.getGames().map {
                    GameMapper.toDomain(it)
                }
            }

            override fun shouldFetch(data: List<Game>?): Boolean {
                return data == null || data.isEmpty()
            }

            override suspend fun createCall(): Flow<ApiResponse<GameListResponse>> {
                return remoteDataSource.getGames()
            }

            override suspend fun saveCallResult(data: GameListResponse) {
                val games = GameMapper.toEntities(data)
                localDataSource.insertGames(games)
            }
        }.asFlow()
    }

    override fun getFavoriteGames(): Flow<List<Game>> {
        return localDataSource.getFavoriteGames().map {
            GameMapper.toDomain(it)
        }
    }

    override fun setFavoriteGame(game: Game, state: Boolean) {
        val gameEntity = GameMapper.toEntity(game)
        appExecutors.diskIO().execute {
            localDataSource.updateFavoriteGame(gameEntity, state)
        }
    }
}