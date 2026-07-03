package com.example.finalproject.di

import com.example.finalproject.data.repository.ExploreRepository
import com.example.finalproject.data.repository.ExploreRepositoryImpl
import com.example.finalproject.data.repository.QuizRepository
import com.example.finalproject.data.repository.QuizRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExploreRepository(impl: ExploreRepositoryImpl): ExploreRepository

    @Binds
    @Singleton
    abstract fun bindQuizRepository(impl: QuizRepositoryImpl): QuizRepository
}
