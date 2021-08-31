package io.android.games.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import io.android.core.domain.usecase.GameUseCase

class GameListViewModel(gameUseCase: GameUseCase) : ViewModel() {
    val games = gameUseCase.getGames().asLiveData()
}