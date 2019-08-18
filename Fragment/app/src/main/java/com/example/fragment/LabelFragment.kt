package com.example.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class LabelFragment : Fragment() {

    private var count = 0
    private lateinit var counterLabel: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // onSaveInstanceStateで設定した値を受け取る
        count = savedInstanceState?.getInt("count") ?: arguments?.getInt("count") ?: 0
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.label_fragement, container, false)

        counterLabel = view.findViewById(R.id.textView)
        counterLabel.text = count.toString()
        return view
    }


    fun updateCount() {
        count++
        counterLabel.text = count.toString()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("count", count)
    }
}

fun newLabelFragment(value: Int): LabelFragment {
    val fragment = LabelFragment()

    val args = Bundle()
    args.putInt("count", value)

    fragment.arguments = args
    return fragment
}