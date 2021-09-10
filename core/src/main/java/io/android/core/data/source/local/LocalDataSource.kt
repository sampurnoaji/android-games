package io.android.core.data.source.local

import io.android.core.data.source.local.entity.GameEntity
import io.android.core.data.source.local.room.GameDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val gameDao: GameDao) {
    fun getGames(): Flow<List<GameEntity>> = gameDao.getGames()
    fun getFavoriteGames(): Flow<List<GameEntity>> = gameDao.getFavoriteGames()
    suspend fun insertGames(games: List<GameEntity>) = gameDao.insertGames(games)
    suspend fun updateFavoriteGame(game: GameEntity, newState: Boolean) {
        game.isFavorite = newState
        gameDao.updateFavoriteGame(game)
    }
}