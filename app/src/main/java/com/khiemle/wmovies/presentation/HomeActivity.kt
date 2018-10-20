package com.khiemle.wmovies.presentation

import android.app.AlertDialog
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import com.khiemle.wmovies.R
import com.khiemle.wmovies.presentation.screens.CastsAndCrewsFragment
import com.khiemle.wmovies.presentation.screens.MovieDetailsFragment
import com.khiemle.wmovies.presentation.screens.NowPlayingFragment
import com.khiemle.wmovies.presentation.screens.TopRatedFragment
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_home.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity() {

    @Inject lateinit var connectivityManager: ConnectivityManager

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
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val existsFragment = supportFragmentManager.findFragmentById(R.id.frameContent)
        existsFragment?.let {
            return
        }
        if (isConnected()) {
            openNowPlaying()
        } else {
            showNetworkError()
        }

    }


    fun showNetworkError() {
        runOnUiThread {
            val dialog = AlertDialog.Builder(this).setTitle("Alert").setMessage("Internet is'nt available").setPositiveButton("Retry") { _, _ ->
                if (isConnected()) {
                    openNowPlaying()
                } else {
                    showNetworkError()
                }
            }
            .setNegativeButton("Cancel") { _,_ -> }.create()
            dialog.show()
        }
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

    fun openListContributors(id: Long) {
        val fragment = CastsAndCrewsFragment()
        val bundle = Bundle()
        bundle.putLong(CastsAndCrewsFragment.ARG_MOVIE_ID, id)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.frameContent, fragment).addToBackStack(CastsAndCrewsFragment::class.java.simpleName).commit()
    }

    fun showBottomNavigation(showed: Boolean) {
        when (showed) {
            true -> {
                navigation.visibility = View.VISIBLE
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
            false -> {
                navigation.visibility = View.GONE
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
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

    fun isConnected() : Boolean {
        if (connectivityManager.activeNetworkInfo != null && connectivityManager.activeNetworkInfo.isConnected) {
            return true;
        }
        return false
    }
}
