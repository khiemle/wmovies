package com.khiemle.wmovies.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khiemle.wmovies.data.models.Contributor
import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.data.repositories.AppDatabase
import com.khiemle.wmovies.data.repositories.MovieRepository
import com.khiemle.wmovies.domains.ConfigurationDomain
import com.khiemle.wmovies.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class CastsAndCrewsViewModel(retrofit: Retrofit, appDatabase: AppDatabase?, val configurationDomain: ConfigurationDomain): ViewModel() {

    val listContributors = MutableLiveData<List<Contributor>>()
    val movie = MutableLiveData<Movie>()
    private val movieRepository = MovieRepository(retrofit, appDatabase)
    private val compositeDisposable = CompositeDisposable()

    class Factory(val retrofit: Retrofit, val appDatabase: AppDatabase?, val configurationDomain: ConfigurationDomain) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CastsAndCrewsViewModel(retrofit, appDatabase, configurationDomain) as T
        }
    }

    fun getMovie(moviedId: Long) {
        compositeDisposable += movieRepository.getMovie(moviedId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movie>() {
                    override fun onComplete() {
                    }

                    override fun onNext(t: Movie) {
                        movie.value = t
                    }

                    override fun onError(e: Throwable) {
                    }
                })
    }

    fun getListContributors(moviedId: Long) {
        compositeDisposable += movieRepository.getMovieContributors(moviedId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Contributor>>() {
                    override fun onComplete() {
                    }

                    override fun onNext(result: List<Contributor>) {
                        listContributors.value = result
                    }

                    override fun onError(e: Throwable) {
                    }

                })
    }

    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }
}