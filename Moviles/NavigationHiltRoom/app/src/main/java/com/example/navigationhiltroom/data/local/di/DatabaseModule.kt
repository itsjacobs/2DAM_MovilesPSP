package com.example.navigationhiltroom.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.navigationhiltroom.data.commons.ConstantesDatabase
import com.example.navigationhiltroom.data.local.AppDatabase
import com.example.navigationhiltroom.data.local.dao.ResenyasDao
import com.example.navigationhiltroom.data.local.dao.SeriesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context : Context) : AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            ConstantesDatabase.APPDATABASE
        )
        .fallbackToDestructiveMigration()
        .build()
    }

    @Provides
    fun provideSerieDao(database: AppDatabase) : SeriesDao {
        return database.serieDao()
    }

    @Provides
    fun provideResenyaDao(database: AppDatabase) : ResenyasDao {
        return database.resenyaDao()
    }
}