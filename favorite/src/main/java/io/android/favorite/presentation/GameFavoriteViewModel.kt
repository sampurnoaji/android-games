package io.android.favorite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import io.android.core.domain.usecase.GameUseCase

class GameFavoriteViewModel(gameUseCase: GameUseCase): ViewModel() {
    val favorites = gameUseCase.getFavoriteGames().asLiveData()
}