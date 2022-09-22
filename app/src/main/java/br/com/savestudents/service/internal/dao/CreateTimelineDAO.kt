package br.com.savestudents.service.internal.dao

import androidx.room.*
import br.com.savestudents.service.internal.entity.CreateTimelineItemEntity

@Dao
interface CreateTimelineDAO {
    @Query("SELECT * FROM create_timeline_table")
    fun getTimelineItems(): MutableList<CreateTimelineItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createTimelineItems(timelineItem: CreateTimelineItemEntity)

    @Query("DELETE FROM create_timeline_table WHERE id = :id")
    fun deleteTimelineItem(id: Int)

    @Query("DELETE FROM create_timeline_table")
    fun clearTimelineItemList()

}