package com.khiemle.wmovies.presentation.viewmodels

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.data.repositories.MovieRepository
import com.khiemle.wmovies.data.repositories.MoviesListType
import com.khiemle.wmovies.data.repositories.RequestStatus
import com.khiemle.wmovies.data.repositories.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

class MoviesViewModel(retrofit: Retrofit, private val moviesListType: MoviesListType): ViewModel() {

    class NowPlayingFactory(private val retrofit: Retrofit) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(retrofit, MoviesListType.NOW_PLAYING) as T
        }
    }
    class TopRatedFactory(private val retrofit: Retrofit) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(retrofit, MoviesListType.TOP_RATED) as T
        }
    }

    val isLoading = ObservableField(false)
    val status = ObservableField(Result(RequestStatus.IDLE, null))

    private val movieRepository = MovieRepository(retrofit)
    var movies = MutableLiveData<List<Movie>>()

    fun loadedMoviesList() : Boolean {
        movies.value?.isNotEmpty()?.let {
            return it
        }
        return false
    }
    private val compositeDisposable = CompositeDisposable()

    fun getMovies(page: Int) {
        isLoading.set(true)
        status.set(Result(RequestStatus.LOADING, null))
        compositeDisposable += movieRepository.getMovies(page, moviesListType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Movie>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(result: List<Movie>) {
                        result?.let {
                            movies.value = it
                            status.set(Result(RequestStatus.SUCCESSFUL, null))
                        }
                    }

                    override fun onError(e: Throwable) {
                        status.set((Result(RequestStatus.ERROR, e.message)))
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