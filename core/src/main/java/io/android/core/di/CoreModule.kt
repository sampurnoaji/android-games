package io.android.core.di

import androidx.room.Room
import io.android.core.data.repository.GameRepositoryImpl
import io.android.core.data.source.local.LocalDataSource
import io.android.core.data.source.local.room.GameDatabase
import io.android.core.data.source.remote.RemoteDataSource
import io.android.core.data.source.remote.network.ApiService
import io.android.core.domain.repository.GameRepository
import io.android.core.util.AppExecutors
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<GameDatabase>().gameDao() }
    single {
        Room.databaseBuilder(androidContext(), GameDatabase::class.java, "game.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.amiiboapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    factory { AppExecutors() }
    single<GameRepository> {
        GameRepositoryImpl(
            localDataSource = get(), remoteDataSource = get(), appExecutors = get()
        )
    }
}