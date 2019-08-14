package com.example.worldclock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ListView

class TimeZoneSelectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_zone_select)

        // キャンセル時
        setResult(Activity.RESULT_CANCELED)

        val list = findViewById<ListView>(R.id.clockList)
        val adapter = TimeZoneAdapter(this)
        list.adapter = adapter

        list.setOnItemClickListener { _, _, position, _ ->
            // 選択されたアイテム
            val timeZone = adapter.getItem(position)

            val result = Intent()
            result.putExtra(getString(R.string.INTENT_TIMEZONE), timeZone)

            setResult(Activity.RESULT_OK, result)

            finish()
        }
    }
}
