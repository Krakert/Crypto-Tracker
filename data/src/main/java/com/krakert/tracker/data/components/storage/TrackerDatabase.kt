package com.krakert.tracker.data.components.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krakert.tracker.data.components.storage.mapper.DataConverters
import com.krakert.tracker.data.components.tracker.entity.CoinCurrentDataEntity
import com.krakert.tracker.data.components.tracker.entity.ListCoinsEntity

@Database(
    entities = [ListCoinsEntity::class, CoinCurrentDataEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class TrackerDatabase : RoomDatabase() {

    abstract fun trackerDao(): TrackerDao

}