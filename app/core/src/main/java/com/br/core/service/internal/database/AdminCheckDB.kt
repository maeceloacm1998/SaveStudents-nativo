package com.br.core.service.internal.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.br.core.service.internal.dao.AdminCheckDAO
import com.example.data_transfer.model.entity.AdminCheckEntity

@Database(entities = [AdminCheckEntity::class], version = 2)
abstract class AdminCheckDB : RoomDatabase() {
    abstract fun adminCheckDAO(): AdminCheckDAO

    companion object {
        private lateinit var INSTANCE: AdminCheckDB
        private const val DATABASE_NAME = "admin_check_database"

        fun getDataBase(context: Context): AdminCheckDB {
            if(!::INSTANCE.isInitialized) {
                synchronized(AdminCheckDB::class.java) {
                    INSTANCE = Room.databaseBuilder(context, AdminCheckDB::class.java, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }

            return INSTANCE
        }
    }
}