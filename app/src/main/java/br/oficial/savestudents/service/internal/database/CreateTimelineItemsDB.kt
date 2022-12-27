package br.oficial.savestudents.service.internal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.oficial.savestudents.service.internal.dao.CreateTimelineDAO
import br.oficial.savestudents.service.internal.entity.CreateTimelineItemEntity

@Database(entities = [CreateTimelineItemEntity::class], version = 1)
abstract class CreateTimelineItemsDB : RoomDatabase() {
    abstract fun createTimelineDAO(): CreateTimelineDAO

    companion object {
        private lateinit var INSTANCE: CreateTimelineItemsDB
        private const val DATABASE_NAME = "crate_timeline_database"

        fun getDataBase(context: Context): CreateTimelineItemsDB {
            if(!::INSTANCE.isInitialized) {
                synchronized(CreateTimelineItemsDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context, CreateTimelineItemsDB::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}