package br.oficial.savestudents.service.internal.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "admin_check_table")
class AdminCheckEntity {
    @PrimaryKey
    @ColumnInfo(name = "admin_mode_on")
    var isAdminModeOn: Boolean = false
}

