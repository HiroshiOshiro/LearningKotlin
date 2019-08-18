package com.example.fragment

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MainActivity : AppCompatActivity(), ButtonFragment.OnButtonClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentByTag("labelFragement") == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, newLabelFragment(0), "labelFragement")
                .commit()
        }
    }

    override fun onButtonClicked() {
        val labelFragment = supportFragmentManager.findFragmentByTag("labelFragement") as LabelFragment
        labelFragment.updateCount()
    }
}
