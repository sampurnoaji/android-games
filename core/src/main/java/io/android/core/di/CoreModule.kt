package io.android.core.di

import androidx.room.Room
import io.android.core.BuildConfig
import io.android.core.data.repository.GameRepositoryImpl
import io.android.core.data.source.local.LocalDataSource
import io.android.core.data.source.local.room.GameDatabase
import io.android.core.data.source.remote.RemoteDataSource
import io.android.core.data.source.remote.network.ApiService
import io.android.core.domain.repository.GameRepository
import java.util.concurrent.TimeUnit
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val databaseModule = module {
    factory { get<GameDatabase>().gameDao() }
    single {
        val passphrase: ByteArray = SQLiteDatabase.getBytes("games".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(androidContext(), GameDatabase::class.java, "game.db")
            .fallbackToDestructiveMigration()
            .openHelperFactory(factory)
            .build()
    }
}

val networkModule = module {
    single { provideOkHttpClient() }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.amiiboapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

fun provideOkHttpClient(): OkHttpClient {
    val hostname = "www.amiiboapi.com"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/njwbWjzkl4OkPq2NlQXQBFOu9bzJu9Vp4Xxy1rG2jWU=")
        .add(hostname, "sha256/jQJTbIh0grw0/1TkHSumWb+Fs0Ggogr621gT3PvPKG0=")
        .add(hostname, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
        .build()
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .certificatePinner(certificatePinner)
    if (BuildConfig.DEBUG) {
        client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
    }
    return client.build()
}

val repositoryModule = module {
    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<GameRepository> {
        GameRepositoryImpl(
            localDataSource = get(), remoteDataSource = get()
        )
    }
}