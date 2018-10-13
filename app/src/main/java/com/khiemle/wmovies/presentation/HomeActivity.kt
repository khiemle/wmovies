package com.khiemle.wmovies.presentation

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.khiemle.wmovies.R
import com.khiemle.wmovies.presentation.screens.NowPlayingFragment
import com.khiemle.wmovies.presentation.screens.TopRatedFragment
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_now_playing -> {
                val existsFragment = supportFragmentManager.findFragmentById(R.id.frameContent)
                existsFragment?.let {
                    if (it is NowPlayingFragment) return@OnNavigationItemSelectedListener true
                }
                openNowPlaying()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_top_rated -> {
                val existsFragment = supportFragmentManager.findFragmentById(R.id.frameContent)
                existsFragment?.let {
                    if (it is TopRatedFragment) return@OnNavigationItemSelectedListener true
                }
                openTopRated()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val existsFragment = supportFragmentManager.findFragmentById(R.id.frameContent)
        existsFragment?.let {
            return
        }
        openNowPlaying()

    }

    private fun openNowPlaying() {
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, NowPlayingFragment()).commit()
    }

    private fun openTopRated() {
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, TopRatedFragment()).commit()
    }

}
