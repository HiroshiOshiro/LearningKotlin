package com.example.memo

import android.Manifest
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import java.io.File

class MainActivity : AppCompatActivity(), FileListFragment.OnFileSelectedListener, InputFrgment.OnFileOutputListener {

    // ドロワーの状態捜査用
    private var drawerToggle: ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (hasPermission()) setViews()


    }

    private fun setViews() {
        setContentView(R.layout.activity_main)

        //　レイアウトからドロワーを取得
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)

        // ドロワーレイアウトがある時のみドロワー設定する
        if (drawerLayout != null) {
            setupDrawer(drawerLayout)
        }
    }

    // パーミッション確認
    private fun hasPermission(): Boolean {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            // 持っていないならパーミッションを要求
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1
            )
            return false
        }

        return true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            setViews()
            drawerToggle?.syncState()
        } else {
            finish()
        }
    }

    // activity 生成後に呼ばれる
    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)

        // ドロワートグルの状態を同期する
        drawerToggle?.syncState()
    }

    // 画面回転時など、状態が変化したときに呼ばれる
    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        // 状態の変化をドロワーに伝える
        drawerToggle?.onConfigurationChanged(newConfig)
    }

    // ドロワー設定
    private fun setupDrawer(drawer: DrawerLayout) {
        val toggle = ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name)
        toggle.isDrawerIndicatorEnabled = true // ドロワーのトグルを有効にする
        drawer.addDrawerListener(toggle)

        drawerToggle = toggle

        // アクションバーの設定
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true) // ドロワー用のアイコンを表示
            setHomeButtonEnabled(true)
        }
    }

    // オプションメニューがタップされたときに呼ばれる
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // ドロワーに伝える
        if (drawerToggle?.onOptionsItemSelected(item) == true) {
            return true
        } else {
            return super.onOptionsItemSelected(item)
        }


    }

    override fun onFileSelected(file: File) {
        val fragment = supportFragmentManager.findFragmentById(R.id.input) as InputFrgment

        fragment.show(file)
    }


    override fun onFileOutput() {
        val fragment = supportFragmentManager.findFragmentById(R.id.list) as FileListFragment

        fragment.show()
    }
}
