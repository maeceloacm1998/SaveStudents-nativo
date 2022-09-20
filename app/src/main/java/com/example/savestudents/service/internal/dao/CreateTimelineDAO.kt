package com.example.savestudents.service.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.savestudents.service.internal.entity.CreateTimelineItemEntity

@Dao
interface CreateTimelineDAO {
    @Query("SELECT * FROM create_timeline_table")
    fun getTimelineItems(): MutableList<CreateTimelineItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createTimelineItems(timelineItem: CreateTimelineItemEntity)
}