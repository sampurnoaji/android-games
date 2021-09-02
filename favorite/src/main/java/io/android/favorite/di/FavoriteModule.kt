package io.android.favorite.di

import io.android.favorite.presentation.GameFavoriteViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val favoriteModule = module {
    viewModel { GameFavoriteViewModel(get()) }
}