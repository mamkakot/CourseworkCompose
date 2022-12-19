package com.example.courseworkcompose.di

import com.example.courseworkcompose.data.api.CleaningAppApiService
import com.example.courseworkcompose.data.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val baseUrl = "http://localhost:8000/api/"

    @Singleton
    @Provides
    fun provideCleaningAppRepository(
        api: CleaningAppApiService
    ) = Repository(api)

    @Singleton @Provides
    fun provideCleaningAppApi(): CleaningAppApiService {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(CleaningAppApiService::class.java)
    }
}