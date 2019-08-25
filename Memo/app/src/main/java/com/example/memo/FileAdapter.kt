package com.example.memo

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.io.File

class FilesAdapter(
    private val context: Context,
    // ファイルのリスト
    private val files: List<File>,
    // タップ時のコールバック
    private val onFileClicked: (File) -> Unit
) : RecyclerView.Adapter<FilesAdapter.FileViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        val view = infrater.inflate(R.layout.list_item_row, parent, false)
        val viewHolder = FileViewHolder(view)

        view.setOnClickListener {
            // 選択されたメモ
            val file = files[viewHolder.adapterPosition]
            // コールバックを呼ぶ
            onFileClicked(file)
        }

        return viewHolder
    }

    override fun getItemCount() = files.size


    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val file = files[position]
        // ファイル名
        holder.title.text = file.name
        // 更新日時
        holder.updateTime.text = context.getString(R.string.last_modified_format, file.lastModified())

    }

    private val infrater = LayoutInflater.from(context)

    class FileViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title = view.findViewById<TextView>(R.id.title)
        val updateTime = view.findViewById<TextView>(R.id.lastModified)
    }
}
