package com.example.memo

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.File

class FileListFragment : Fragment() {
    // タップした時のコールバック
    interface OnFileSelectedListener {
        fun onFileSelected(file: File)
    }

    private lateinit var recyclerView: RecyclerView

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        // アクティビティがコールバックを実装していなかったら、例外とする
        if (context !is OnFileSelectedListener) {
            throw RuntimeException("$context must implement OnFileSelectedListener")
        }
    }

    fun show() {
        // context のnullチェック
        val context = context ?: return

        val adapter = FilesAdapter(context, getFiles()) { file ->
            // タップされたら、コールバックを呼ぶ
            (context as OnFileSelectedListener).onFileSelected(file)
        }

        recyclerView.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        recyclerView = view.findViewById(R.id.filesList)
        // 縦方向のレイアウトマネージャー
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.layoutManager = layoutManager

        show()
        return view
    }
}