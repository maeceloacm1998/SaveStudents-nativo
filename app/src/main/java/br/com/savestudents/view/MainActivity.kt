package br.com.savestudents.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.savestudents.R
import br.com.savestudents.service.internal.database.AdminCheckDB
import br.com.savestudents.view.activity.HomeActivity

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