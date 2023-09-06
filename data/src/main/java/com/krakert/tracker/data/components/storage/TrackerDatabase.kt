package com.krakert.tracker.data.components.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import com.krakert.tracker.data.tracker.entity.database.DBCoinCurrentDataEntity
import com.krakert.tracker.data.tracker.entity.database.DBListCoinItemEntity

@Database(
    entities = [DBListCoinItemEntity::class, DBCoinCurrentDataEntity::class],
    version = 1,
    exportSchema = false)
abstract class TrackerDatabase : RoomDatabase() {

    abstract fun trackerDao(): TrackerDao

}