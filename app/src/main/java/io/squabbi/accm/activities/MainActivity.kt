package io.squabbi.accm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import io.squabbi.accm.R
import io.squabbi.accm.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mToolbar = toolbar_main
        val mNavbar = botNav_view

        mNavbar.setOnNavigationItemSelectedListener { m ->
            when (m.itemId) {
                R.id.botNav_home -> {
                    mToolbar.title = "Home"
                    val mainFragment = MainFragment.newInstance()
                    loadFragment(mainFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.botNav_profiles -> mToolbar.setTitle("Profiles")
                R.id.botNav_settings -> mToolbar.setTitle("Settings")
            }

            false
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
