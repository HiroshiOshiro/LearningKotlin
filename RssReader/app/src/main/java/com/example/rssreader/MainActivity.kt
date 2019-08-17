package com.example.rssreader


import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<Rss> {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // ここをsupportLoaderManagerに変更する
//        loaderManager.initLoader(1, null, this)
        supportLoaderManager.initLoader(1, null, this)

        // 通知チャンネルを作成する
        createChannel(this)

        // 定期的に新しい記事がないかチェックするジョブ
        val fetchJob = JobInfo.Builder(
            1, ComponentName(this, PollingJob::class.java)
        )
            .setPeriodic(TimeUnit.MINUTES.toMillis(5))
            .setPersisted(true) // 端末再起動しても有効
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY) // ネットワーク接続されていること
            .build()

        // ジョブを登録する
        getSystemService(JobScheduler::class.java).schedule(fetchJob)
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Rss> {
        return RssLoader(this)
    }

    override fun onLoadFinished(loader: Loader<Rss>, data: Rss?) {
        if (data != null) {
            // recycler view
            val recyclerView = findViewById<RecyclerView>(R.id.articles)

            val adapter = ArticleAdpter(this, data.articles) { article ->
                // 記事タップしたときの処理
                val intent = CustomTabsIntent.Builder().build()
                intent.launchUrl(this, Uri.parse(article.link))
            }

            recyclerView.adapter = adapter

            // グリット表示
            val layoutManager = GridLayoutManager(this, 2)

            recyclerView.layoutManager = layoutManager
        }
    }

    override fun onLoaderReset(p0: Loader<Rss>) {
        // do nothing
    }
}
