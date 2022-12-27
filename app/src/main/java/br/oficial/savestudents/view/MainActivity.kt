package br.oficial.savestudents.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.oficial.savestudents.R
import br.oficial.savestudents.service.internal.database.AdminCheckDB
import br.oficial.savestudents.view.activity.HomeActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAdminCheckDb()
        renderHomeActivity()
    }

    private fun initAdminCheckDb() {
        AdminCheckDB.getDataBase(applicationContext).adminCheckDAO()
    }

    private fun renderHomeActivity() {
        val intent = HomeActivity.newInstance(applicationContext)
        startActivity(intent)
    }
}