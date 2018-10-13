package com.khiemle.wmovies.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.internal.bind.DateTypeAdapter
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import javax.inject.Singleton

@Module(includes = [OkHttpClientModule::class])
class NetModule {

    companion object {
        const val MOVIES_END_POINT = "https://api.themoviedb.org/3/"
    }

    @Provides
    @Singleton
    fun getGson() : Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Date::class.java, DateTypeAdapter())
                .create()
    }

    @Provides
    @Singleton
    fun getRetrofit(okHttpClient: OkHttpClient, gson: Gson) : Retrofit {
        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(MOVIES_END_POINT)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

}