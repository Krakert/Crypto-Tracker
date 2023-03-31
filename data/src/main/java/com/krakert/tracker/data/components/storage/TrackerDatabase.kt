package com.krakert.tracker.data.components.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.krakert.tracker.data.components.storage.mapper.DataConverters
import com.krakert.tracker.data.tracker.entity.database.DBCoinCurrentDataEntity
import com.krakert.tracker.data.tracker.entity.database.DBListCoinItemEntity

@Database(
    entities = [DBListCoinItemEntity::class, DBCoinCurrentDataEntity::class],
    version = 1,
    exportSchema = false)
@TypeConverters(DataConverters::class)
abstract class TrackerDatabase : RoomDatabase() {

    abstract fun trackerDao(): TrackerDao

}