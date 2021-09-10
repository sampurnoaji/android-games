package io.android.games.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.android.core.domain.model.Game
import io.android.core.domain.usecase.GameUseCase
import kotlinx.coroutines.launch

class GameDetailViewModel(private val gameUseCase: GameUseCase) : ViewModel() {
    fun setFavoriteGame(game: Game, newStatus: Boolean) {
        viewModelScope.launch {
            gameUseCase.setFavoriteGame(game, newStatus)
        }
    }
}