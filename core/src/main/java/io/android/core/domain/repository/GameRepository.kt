package io.android.core.domain.repository

import io.android.core.data.Resource
import io.android.core.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGames(): Flow<Resource<List<Game>>>
    fun getFavoriteGames(): Flow<List<Game>>
    suspend fun setFavoriteGame(game: Game, state: Boolean)
}