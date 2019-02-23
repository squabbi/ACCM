package io.squabbi.accm.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import io.squabbi.accm.R
import io.squabbi.accm.activities.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment: Fragment() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_main, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set ViewModel
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // Observe
        mainViewModel.mAccInfoLiveData.observe(this, Observer {
                info ->
            // Update UI
            info?.let { textView_batteryStatus.text = info.status }
        })
    }

    companion object {
        fun newInstance(): MainFragment = MainFragment()
    }
}