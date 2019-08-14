package com.example.worldclock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val timeZone = TimeZone.getDefault()

        val timeZoneLabel = findViewById<TextView>(R.id.timeZone)
        timeZoneLabel.text = timeZone.displayName

        val addButton = findViewById<Button>(R.id.addButton)
        addButton.setOnClickListener {
            val intent = Intent(this, TimeZoneSelectActivity::class.java)
            startActivityForResult(intent, 1)
        }

        showWorldClocks()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1
            && resultCode == Activity.RESULT_OK
            && data != null
        ) {

            val timeZone = data.getStringExtra("timeZone")

            val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
            val savedTimeZones = pref.getStringSet("time_zone", mutableSetOf())

            // タイムゾーンを追加
            savedTimeZones.add(timeZone)

            // 保存
            pref.edit().putStringSet("time_zone", savedTimeZones).apply()

            showWorldClocks()
        }

    }

    private fun showWorldClocks() {
        val pref = getSharedPreferences("prefs", Context.MODE_PRIVATE)
        val timeZone = pref.getStringSet("time_zone", setOf())

        val list = findViewById<ListView>(R.id.clockList)
        list.adapter = TimeZoneAdapter(context = this, timeZones = timeZone.toTypedArray())
    }
}
