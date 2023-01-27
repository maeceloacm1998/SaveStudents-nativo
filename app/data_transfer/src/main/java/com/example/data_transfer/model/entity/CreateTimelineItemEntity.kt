package com.example.data_transfer.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.data_transfer.model.CreateTimelineItem

@Entity(tableName = "create_timeline_table")
class CreateTimelineItemEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0

    @ColumnInfo(name = "date")
    var date: Long = 0L

    @ColumnInfo(name = "subject_name")
    var subjectName: String = ""

    @ColumnInfo(name = "type")
    var type: String = ""
}


fun List<CreateTimelineItemEntity>.asDomainModel() : List<CreateTimelineItem> {
    return this.map { item ->
        CreateTimelineItem(
            id = item.id,
            date = item.date,
            subjectName = item.subjectName,
            type = item.type
        )
    }
}