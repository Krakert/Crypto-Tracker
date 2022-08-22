package com.krakert.tracker.di

import com.krakert.tracker.repository.CachedCryptoRepository
import com.krakert.tracker.repository.CryptoRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {
  @Binds
  fun bindCryptoApiRepository(cachedCryptoRepository: CachedCryptoRepository): CryptoRepository
}