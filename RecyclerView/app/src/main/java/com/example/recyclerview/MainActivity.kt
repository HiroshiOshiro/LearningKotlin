package com.example.recyclerview

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.timeZoneListView)

        val adapter = SampleAdapter(this) { timeZone ->
            Toast.makeText(this, timeZone.displayName, Toast.LENGTH_SHORT).show()
        }

        recyclerView.adapter = adapter

//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // グリッド
        recyclerView.layoutManager = GridLayoutManager(this, 2)
    }
}

// RecyclerView用のサンプルアダプタ
class SampleAdapter(context: Context, private val onItemClicked: (TimeZone) -> Unit) :
    RecyclerView.Adapter<SampleAdapter.SampleViewHolder>() {

    private val inflater = LayoutInflater.from(context)

    private val timeZones = TimeZone.getAvailableIDs().map { id ->
        TimeZone.getTimeZone(id)
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SampleViewHolder {
        // view
        val view = inflater.inflate(R.layout.list_time_zone_row, p0, false)

        val viewHolder = SampleViewHolder(view)

        view.setOnClickListener {
            // アダプター上の位置
            val position = viewHolder.adapterPosition

            val timeZone = timeZones[position]
            // call back
            onItemClicked(timeZone)
        }

        return viewHolder
    }

    override fun getItemCount() = timeZones.size

    override fun onBindViewHolder(holder: SampleViewHolder, position: Int) {
        val timeZone = timeZones[position]

        holder.timeZone.text = timeZone.id
    }

    // ViewHolder
    class SampleViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val timeZone = view.findViewById<TextView>(R.id.timeZoneTextView)
    }
}
