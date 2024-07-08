package com.rohanlodhi.newsapplication.di

import com.rohanlodhi.newsapplication.data.repository.NewsRepositoryImpl
import com.rohanlodhi.newsapplication.domain.repository.NewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNewsRepository(
        newsRepositoryImpl: NewsRepositoryImpl
    ): NewsRepository
}
