package com.krakert.tracker.data.di

import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl
import com.krakert.tracker.data.tracker.TrackerRepositoryImpl
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
    fun bindCryptoApiRepository(Repository: TrackerRepositoryImpl): TrackerRepository

    @Binds
    fun bindPreferencesRepository(Repository: PreferencesRepositoryImpl): PreferencesRepository
}