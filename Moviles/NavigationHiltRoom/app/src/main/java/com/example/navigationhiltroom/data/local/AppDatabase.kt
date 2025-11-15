package com.example.navigationhiltroom.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.navigationhiltroom.data.local.dao.SeriesDao
import com.example.navigationhiltroom.data.local.dao.ResenyasDao
import com.example.navigationhiltroom.data.local.entities.SerieEntity
import com.example.navigationhiltroom.data.local.entities.ResenyaEntity


@Database(
    entities = [SerieEntity::class, ResenyaEntity::class],
    version = 5,
    exportSchema = false
)


abstract class AppDatabase : RoomDatabase(){

    abstract fun serieDao(): SeriesDao
    abstract fun resenyaDao(): ResenyasDao
}