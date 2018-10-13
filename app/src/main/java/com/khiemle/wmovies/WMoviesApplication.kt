package com.khiemle.wmovies

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.multidex.MultiDexApplication
import com.khiemle.wmovies.di.AppModule
import com.khiemle.wmovies.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


class WMoviesApplication: MultiDexApplication(), HasActivityInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().appModule(AppModule(this)).build().inject(this)
    }
}