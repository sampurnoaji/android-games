package io.android.core.domain.usecase

import io.android.core.data.Resource
import io.android.core.domain.model.Game
import io.android.core.domain.repository.GameRepository
import kotlinx.coroutines.flow.Flow

class GameInteractor(private val repository: GameRepository): GameUseCase {
    override fun getGames(): Flow<Resource<List<Game>>> {
        return repository.getGames()
    }

    override fun getFavoriteGames(): Flow<List<Game>> {
        return repository.getFavoriteGames()
    }

    override suspend fun setFavoriteGame(game: Game, state: Boolean) {
        repository.setFavoriteGame(game, state)
    }
}