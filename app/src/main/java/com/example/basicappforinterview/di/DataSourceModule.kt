package com.example.basicappforinterview.di

import com.example.basicappforinterview.data.datasource.LocalDataSource
import com.example.basicappforinterview.data.datasource.LocalDataSourceImpl
import com.example.basicappforinterview.data.datasource.RemoteDataSource
import com.example.basicappforinterview.data.datasource.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class DataSourceModule {
    @Binds
    abstract fun provideRemoteDataSource(dataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    abstract fun provideLocalDataSource(dataSourceImpl: LocalDataSourceImpl): LocalDataSource
}