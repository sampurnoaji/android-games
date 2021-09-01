package io.android.games.detail

import androidx.lifecycle.ViewModel
import io.android.core.domain.model.Game
import io.android.core.domain.usecase.GameUseCase

class GameDetailViewModel(private val gameUseCase: GameUseCase) : ViewModel() {
    fun setFavoriteGame(game: Game, newStatus: Boolean) =
        gameUseCase.setFavoriteGame(game, newStatus)
}