package com.khiemle.wmovies.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.data.repositories.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

class MoviesViewModel(retrofit: Retrofit, appDatabase: AppDatabase?, private val moviesListType: MoviesListType): ViewModel() {

    class NowPlayingFactory(private val retrofit: Retrofit,private val appDatabase: AppDatabase?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(retrofit, appDatabase, MoviesListType.NOW_PLAYING) as T
        }
    }
    class TopRatedFactory(private val retrofit: Retrofit, private val appDatabase: AppDatabase?) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesViewModel(retrofit, appDatabase, MoviesListType.TOP_RATED) as T
        }
    }

    val isLoading = ObservableField(false)
    val status = ObservableField(Result(RequestStatus.IDLE, null))

    private val movieRepository = MovieRepository(retrofit, appDatabase)
    lateinit var movies : LiveData<PagedList<Movie>>

    private val compositeDisposable = CompositeDisposable()

    init {
        movieRepository.getMoviesWithPaging(moviesListType, object : PagedList.BoundaryCallback<Movie>() {
            override fun onZeroItemsLoaded() {
                isLoading.get()?.let {
                    if (it) return
                }
                loadMoreMovies(1)
            }
            override fun onItemAtEndLoaded(itemAtEnd: Movie) {
                loadMoreMovies(itemAtEnd.page+1)
            }
        })?.let {
            movies = it
        }
    }

    fun loadMoreMovies(page: Int) {
        isLoading.set(true)
        status.set(Result(RequestStatus.LOADING, null))
        compositeDisposable += movieRepository.getMovies(page, moviesListType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<List<Movie>>() {
                    override fun onComplete() {
                        isLoading.set(false)
                        status.set(Result(RequestStatus.SUCCESSFUL, null))
                    }
                    override fun onNext(result: List<Movie>) {
                        result?.let {
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

    fun clearAndReload(type: MoviesListType) {
        compositeDisposable += movieRepository.clearLocalDataSource(moviesListType)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Int>() {
                    override fun onComplete() {}
                    override fun onNext(result: Int) {}
                    override fun onError(e: Throwable) {}
        })
    }
}