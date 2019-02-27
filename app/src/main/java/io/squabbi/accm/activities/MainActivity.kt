package io.squabbi.accm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.squabbi.accm.R
import io.squabbi.accm.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mToolbar = toolbar_main
        var mNavbar = botNav_view

        mNavbar.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            // TODO: allow the user to set default page
            val item = mNavbar.menu.getItem(0)
            onNavigationItemSelected(item)
        }
    }


    override fun onNavigationItemSelected(m: MenuItem): Boolean {
        when (m.itemId) {
            R.id.botNav_home -> {
                val mainFragment = MainFragment.newInstance()
                loadFragment(mainFragment)
                return true
            }
            R.id.botNav_profiles -> {
                // TODO: Show Profiles fragment
                return true
            }
            R.id.botNav_settings -> {
                // TODO: Show settings fragment
                return true
            }
        }

        return false
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frameLayout_main, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
