package com.example.basicappforinterview.di

import com.example.basicappforinterview.data.repository.RepositoryImpl
import com.example.basicappforinterview.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repositoryImpl: RepositoryImpl): Repository
}