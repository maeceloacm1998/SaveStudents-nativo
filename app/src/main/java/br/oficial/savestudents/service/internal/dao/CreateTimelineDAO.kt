package br.oficial.savestudents.service.internal.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.oficial.savestudents.service.internal.entity.CreateTimelineItemEntity

@Dao
interface CreateTimelineDAO {
    @Query("SELECT * FROM create_timeline_table")
    fun getTimelineItems(): MutableList<CreateTimelineItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createTimelineItems(timelineItem: CreateTimelineItemEntity)

    @Query("UPDATE create_timeline_table SET date= :date, subject_name= :subjectName, type= :type WHERE id = :id")
    fun updateTimelineItemPerId(id: Int, date: Long, subjectName: String, type: String)

    @Query("DELETE FROM create_timeline_table WHERE id = :id")
    fun deleteTimelineItem(id: Int)

    @Query("DELETE FROM create_timeline_table")
    fun clearTimelineItemList()

}