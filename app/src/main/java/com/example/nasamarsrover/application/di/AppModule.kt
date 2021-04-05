package com.example.nasamarsrover.application.di

import android.util.Log
import com.example.nasamarsrover.*
import com.example.nasamarsrover.data.local.MarsRoverPreferenceRepository
import com.example.nasamarsrover.data.local.MarsRoverPreferenceRepositoryImpl
import com.example.nasamarsrover.data.remote.NasaMarsRoverApiHelper
import com.example.nasamarsrover.data.remote.NasaMarsRoverApiHelperImpl
import com.example.nasamarsrover.data.remote.NasaMarsRoverService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineExceptionHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()


    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): NasaMarsRoverService =
        retrofit.create(NasaMarsRoverService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: NasaMarsRoverApiHelperImpl): NasaMarsRoverApiHelper = apiHelper

    @Provides
    @Singleton
    fun provideCoroutineExceptionHandler(): CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, exception ->
            exception.message?.let { msg ->
                Log.d("coroutineExceptionTag", msg)
            }
        }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideMarsRoverPreferenceRepository (preferenceRepository: MarsRoverPreferenceRepositoryImpl): MarsRoverPreferenceRepository {
        return preferenceRepository
    }
}