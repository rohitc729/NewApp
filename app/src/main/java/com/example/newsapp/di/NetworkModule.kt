package com.example.newsapp.di

import com.example.newsapp.repository.NewsRepository
import com.example.newsapp.repository.NewsRepositoryImpl
import com.example.newsapp.retrofit.NewsApi
import com.example.newsapp.retrofit.Constant
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface  NetworkModule {

    @Binds
    fun provideNewRepository(newsRepositoryImpl: NewsRepositoryImpl): NewsRepository
    companion object{
        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        @Provides
        @Singleton
        fun provideNewApi(retrofit: Retrofit): NewsApi{
            return retrofit.create(NewsApi::class.java)
        }
    }
}