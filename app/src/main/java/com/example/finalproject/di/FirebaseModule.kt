package com.example.finalproject.di

import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    private const val DATABASE_URL =
        "https://football-app-9af5b-default-rtdb.europe-west1.firebasedatabase.app/"

    @Provides
    @Singleton
    fun provideDatabase(): FirebaseDatabase = FirebaseDatabase.getInstance(DATABASE_URL)
}
