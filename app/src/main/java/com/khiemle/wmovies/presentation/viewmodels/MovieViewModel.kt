package com.khiemle.wmovies.presentation.viewmodels

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.khiemle.wmovies.data.models.Movie
import com.khiemle.wmovies.data.repositories.Credits
import com.khiemle.wmovies.data.repositories.MovieRepository
import com.khiemle.wmovies.domains.ConfigurationDomain
import com.khiemle.wmovies.utils.plusAssign
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit

class MovieViewModel(retrofit: Retrofit, val configurationDomain: ConfigurationDomain): ViewModel() {
    class Factory(val retrofit: Retrofit, private val configurationDomain: ConfigurationDomain) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MovieViewModel(retrofit, configurationDomain) as T
        }
    }

    val isLoading = ObservableField(false)

    private val movieRepository = MovieRepository(retrofit)
    var movie = MutableLiveData<Movie>()
    var credits = MutableLiveData<Credits>()

    private val compositeDisposable = CompositeDisposable()

    fun getImageCoverUrl() : String? {
        return movie.value?.let {
            return@let "${configurationDomain.getBaseUrl()}${configurationDomain.getBackdropWidthQuality(1)}${it.backdropPath}"
        }
    }

    fun getOverview() : String? {
        return movie.value?.let {
            return@let it.overview
        }
    }


    fun getSummaryInfo() : String? {

        val genres = movie.value?.genres?.let {
            var result = ""
            if (it.isNotEmpty()) {
                it.forEach {
                    result = "$result, ${it.name}"
                }
                return@let "| ${result.substring(1)}"
            } else {
                return@let ""
            }
        }

        val runtime = movie.value?.runtime?.let {
            return@let "${it/60}h ${it%60}min"
        }

        val voteAverage = movie.value?.voteAverage?.let {
            return@let "\u2605$it/10"
        }


        return "$voteAverage | $runtime $genres"
    }

    fun getTitleWithReleasedYear() : String? {
        return movie.value?.let {
            return@let "${it.title} (${it.releaseDate?.substring(0, 4)})"
        }
    }

    fun getCredits(id: Long) {
        compositeDisposable += movieRepository.getMovieCredits(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Credits>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(result: Credits) {
                        result?.let {
                            credits.value = it
                        }
                    }

                    override fun onError(e: Throwable) {

                    }


                })

    }

    fun getMovie(id: Long) {
        isLoading.set(true)
        compositeDisposable += movieRepository.getMovieDetails(id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableObserver<Movie>() {
                    override fun onComplete() {
                        isLoading.set(false)
                    }

                    override fun onNext(result: Movie) {
                        result?.let {
                            movie.value = it
                            getCredits(id)
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