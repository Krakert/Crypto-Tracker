package com.krakert.tracker.data.di

import com.krakert.tracker.data.tracker.ApiRepositoryImpl
import com.krakert.tracker.data.tracker.DatabaseRepositoryImpl
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl
import com.krakert.tracker.data.tracker.TrackerRepositoryImpl
import com.krakert.tracker.domain.tracker.ApiRepository
import com.krakert.tracker.domain.tracker.DatabaseRepository
import com.krakert.tracker.domain.tracker.PreferencesRepository
import com.krakert.tracker.domain.tracker.TrackerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindTrackerRepository(repository: TrackerRepositoryImpl): TrackerRepository

    @Binds
    fun bindCryptoApiRepository(repository: ApiRepositoryImpl): ApiRepository

    @Binds
    fun bindPreferencesRepository(repository: PreferencesRepositoryImpl): PreferencesRepository

    @Binds
    fun bindDatabaseRepository(repository: DatabaseRepositoryImpl): DatabaseRepository
}