package com.shovon.navigationdrawer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.navigation.NavigationView
import com.shovon.navigationdrawer.fragment.account.AccountFragment
import com.shovon.navigationdrawer.fragment.category.CategoryFragment
import com.shovon.navigationdrawer.fragment.home.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBar)
        supportActionBar?.title = getString(R.string.app_name)

        val drawerToggle:ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this, drawerLayout, toolBar, (R.string.open), (R.string.close)
        ){

        }

        drawerToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        fragmentTransaction(HomeFragment())

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_home -> {
                fragmentTransaction(HomeFragment())
            }
            R.id.menu_category -> {
                fragmentTransaction(CategoryFragment())
            }
            R.id.menu_account -> {
                fragmentTransaction(AccountFragment())
            }
            R.id.menu_exit -> {
                finishAffinity()
                finish()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun fragmentTransaction(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}
