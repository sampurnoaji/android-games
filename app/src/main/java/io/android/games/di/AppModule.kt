package io.android.games.di

import io.android.core.domain.usecase.GameInteractor
import io.android.core.domain.usecase.GameUseCase
import io.android.games.detail.GameDetailViewModel
import io.android.games.list.GameListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GameUseCase> { GameInteractor(get()) }
}

val viewModelModule = module {
    viewModel { GameListViewModel(get()) }
    viewModel { GameDetailViewModel(get()) }
}