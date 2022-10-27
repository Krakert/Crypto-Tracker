package com.stakt.tracker.di

import com.stakt.tracker.repository.CachedCryptoRepository
import com.stakt.tracker.repository.CryptoRepository
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