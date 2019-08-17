package com.example.rssreader

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context

class PollingJob() : JobService() {
    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {
        Thread {
            // RSSをダウンロードする
            val response = httpGet("https://www.sbbit.jp/rss/HotTopics.rss")

            if (response != null) {
                // RSSオブジェクトにパースする
                val rss = parseRss(response)

                // SharedPreference
                val prefs = getSharedPreferences("pref_polling", Context.MODE_PRIVATE)

                // 前回取得時の時間
                val lastFetchedTime = prefs.getLong("last_published_time", 0L)
                // 通知する
                if (lastFetchedTime > 0 && lastFetchedTime < rss.pubDate.time) {
                    notifyUpdate(this)
                }
                
                // 取得時感を保存する
                prefs.edit().putLong("last_published_time", rss.pubDate.time).apply()
            }
        }.start()

        return true
    }
}