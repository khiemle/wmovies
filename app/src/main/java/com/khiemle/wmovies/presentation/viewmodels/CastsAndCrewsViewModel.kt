package com.khiemle.wmovies.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Retrofit

class CastsAndCrewsViewModel(retrofit: Retrofit): ViewModel() {
    class Factory(val retrofit: Retrofit) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CastsAndCrewsViewModel(retrofit) as T
        }
    }
}