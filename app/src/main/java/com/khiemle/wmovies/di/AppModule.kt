package com.khiemle.wmovies.di

import android.content.Context
import android.net.ConnectivityManager
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.khiemle.wmovies.data.repositories.AppDatabase
import com.khiemle.wmovies.domains.ConfigurationDomain
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class AppModule(private val applicationContext: Context) {

    @Provides
    @Singleton
    fun getGlide() : RequestManager {
        return Glide.with(applicationContext)
    }

    @Provides
    @Singleton
    fun getConnectivityManager() : ConnectivityManager {
        return applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @Provides
    @Singleton
    fun getAppDatabase() : AppDatabase? {
        return AppDatabase.buildDatabase(applicationContext)
    }


    @Provides
    @Singleton
    fun getConfigurationDomain(retrofit: Retrofit, appDatabase: AppDatabase?) : ConfigurationDomain {
        return ConfigurationDomain(retrofit, appDatabase)
    }

}