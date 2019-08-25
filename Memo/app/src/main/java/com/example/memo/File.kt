package com.example.memo

import android.os.Environment
import android.text.format.DateFormat
import java.io.*
import java.util.*

private fun getFileDir(): File {
    val publicDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)

    if (publicDir != null) {
        // 存在しないディレクトリが返された場合、作成する
        if (!publicDir.exists()) publicDir.mkdirs()
        return publicDir
    } else {
        // getExternalStoragePublicDirectoryがnullの時、別のディレクトリをとる
        val dir = File(Environment.getExternalStorageDirectory(), "MemoFiles")
        // まだ作成されていない場合は、作成する
        if (!dir.exists()) dir.mkdirs()
        return dir
    }
}

// ファイルの一覧を返す
fun getFiles() = getFileDir().listFiles().toList()

// ファイルを出力する
fun outputFile(original: File?, content: String): File {
    // ファイル名は"memo-タイムスタンプとする"
    val timeStamp = DateFormat.format("yyyy-MM-dd-hh-mm-ss", Date())
    val file = original ?: File(getFileDir(), "memo-$timeStamp")

    val writer = BufferedWriter(FileWriter(file))
    writer.use {
        it.write(content)
        it.flush()
    }

    return file
}

// ファイルを読み込む
fun inputFile(file: File): String {
    val reader = BufferedReader(FileReader(file))
    return reader.readLines().joinToString("\n")
}