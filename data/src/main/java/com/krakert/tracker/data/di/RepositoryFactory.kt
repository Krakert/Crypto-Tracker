package com.krakert.tracker.data.di

import com.krakert.tracker.data.tracker.ApiRepositoryImpl
import com.krakert.tracker.data.tracker.PreferencesRepositoryImpl
import com.krakert.tracker.data.tracker.TrackerRepositoryImpl
import com.krakert.tracker.domain.tracker.ApiRepository
import com.krakert.tracker.domain.tracker.PreferencesRepository
import com.krakert.tracker.domain.tracker.TrackerRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface RepositoryFactory {

    @Binds
    @Singleton
    fun bindTrackerRepository(repository: TrackerRepositoryImpl): TrackerRepository

    @Binds
    @Singleton
    fun bindCryptoApiRepository(repository: ApiRepositoryImpl): ApiRepository

    @Binds
    @Singleton
    fun bindPreferencesRepository(repository: PreferencesRepositoryImpl): PreferencesRepository

}