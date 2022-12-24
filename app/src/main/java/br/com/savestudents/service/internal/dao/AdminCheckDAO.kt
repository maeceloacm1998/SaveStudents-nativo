package br.com.savestudents.service.internal.dao

import androidx.room.*
import br.com.savestudents.service.internal.entity.AdminCheckEntity

@Dao
interface AdminCheckDAO {
    @Query("SELECT * FROM admin_check_table")
    fun getAdminModeStatus(): AdminCheckEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAdminModeStatus(adminCheck: AdminCheckEntity)

    @Query("UPDATE admin_check_table SET admin_mode_on= :adminModeOn ")
    fun updateAdminModeStatus(adminModeOn: Boolean)
}