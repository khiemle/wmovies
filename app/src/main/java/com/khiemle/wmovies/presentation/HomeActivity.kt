package com.khiemle.wmovies.presentation

import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.khiemle.wmovies.R
import com.khiemle.wmovies.presentation.screens.MovieDetailsFragment
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

    fun openMovieDetails(id: Long){
        val fragment = MovieDetailsFragment()
        val bundle = Bundle()
        bundle.putLong(MovieDetailsFragment.ARG_MOVIE_ID, id)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).addToBackStack(MovieDetailsFragment::class.simpleName).commit()
    }

    fun showBottomNavigation(showed: Boolean) {
        when (showed) {
            true -> {
                navigation.visibility = View.VISIBLE
                supportActionBar?.let { it.setDisplayHomeAsUpEnabled(false) }
            }
            false -> {
                navigation.visibility = View.GONE
                supportActionBar?.let { it.setDisplayHomeAsUpEnabled(true) }
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean{
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        val existsFragment = supportFragmentManager.findFragmentById(R.id.frameContent)
        existsFragment?.let {
            if (existsFragment is MovieDetailsFragment) {
                showBottomNavigation(true)
            }
        }
        super.onBackPressed()
    }
}
