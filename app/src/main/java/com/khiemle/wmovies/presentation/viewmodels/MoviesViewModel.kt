package com.khiemle.wmovies.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.data.repositories.MovieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

class MoviesViewModel(retrofit: Retrofit): ViewModel() {

    class Factory(private val retrofit: Retrofit) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(retrofit) as T
        }
    }

    val isLoading = ObservableField(false)

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
        compositeDisposable += movieRepository.getMovies(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<List<Movie>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(result: List<Movie>) {
                        result?.let {
                            movies.value = it
                        }
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