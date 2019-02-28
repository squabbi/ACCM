package io.squabbi.accm.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.squabbi.accm.R
import io.squabbi.accm.fragments.MainFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mMainViewModel: MainViewModel
    private lateinit var mMainFragment: MainFragment
//    private lateinit var mProfilesFragment: ProfilesFragment
//    private lateinit var mSettingsFragment: SettingsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var navbar = botNav_view

        // Set ViewModel
        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        // Set SupportActionBar
        setSupportActionBar(findViewById(R.id.toolbar_main))

        // Initialise fragments
        mMainFragment = MainFragment.newInstance()

        navbar.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            // TODO: allow the user to set default page
            val item = navbar.menu.getItem(0)
            onNavigationItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_bar, menu)
        return true
    }

    override fun onNavigationItemSelected(m: MenuItem): Boolean {
        when (m.itemId) {
            R.id.botNav_home -> {
                loadFragment(mMainFragment)
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

//        val currentFragment = supportFragmentManager.primaryNavigationFragment
//        if (currentFragment != null) {
//            transaction.detach(currentFragment)
//        }

        transaction.replace(R.id.frameLayout_main, fragment)
        transaction.commitAllowingStateLoss()
    }
}
