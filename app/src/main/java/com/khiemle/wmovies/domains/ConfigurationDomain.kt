package com.khiemle.wmovies.domains

import com.khiemle.wmovies.data.models.Configuration
import com.khiemle.wmovies.data.repositories.AppDatabase
import com.khiemle.wmovies.data.repositories.MovieRepository
import com.khiemle.wmovies.utils.plusAssign
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class ConfigurationDomain(retrofit: Retrofit, appDatabase: AppDatabase?) {
    private val movieRepository = MovieRepository(retrofit, appDatabase)
    var configuration: Configuration? = null
    private val compositeDisposable = CompositeDisposable()

    init {
        compositeDisposable += movieRepository.getConfigurations().subscribeOn(Schedulers.newThread())
                .subscribeWith(object : DisposableObserver<Configuration>() {
                    override fun onComplete() {
                    }
                    override fun onNext(result: Configuration) {
                        configuration = result
                    }
                    override fun onError(e: Throwable) {
                    }
                })
    }

    fun getBaseUrl() : String? {
        return configuration?.images?.secureBaseUrl
    }

    fun getPosterWidthQuality(quality: Int) : String? {
        return configuration?.images?.posterSizes?.let {
            val finalQuality = if (quality >= it.size) it.size-1 else quality
            return@let it[finalQuality]
        }
    }

    fun getProfileWidthQuality(quality: Int) : String? {
        return configuration?.images?.profileSizes?.let {
            val finalQuality = if (quality >= it.size) it.size-1 else quality
            return@let it[finalQuality]
        }
    }

    fun getBackdropWidthQuality(quality: Int) : String? {
        return configuration?.images?.backdropSizes?.let {
            val finalQuality = if (quality >= it.size) it.size-1 else quality
            return@let it[finalQuality]
        }
    }
}