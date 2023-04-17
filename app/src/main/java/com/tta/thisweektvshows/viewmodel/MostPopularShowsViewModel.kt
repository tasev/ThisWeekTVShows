package com.tta.thisweektvshows.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tta.thisweektvshows.api.Backend
import com.tta.thisweektvshows.api.codables.MovieListResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MostPopularShowsViewModel : BaseViewModel() {

    private val getTrendingMoviesTrigger = MutableLiveData<MovieListResponse?>()
    val getTrendingMoviesLiveData: LiveData<MovieListResponse?> = getTrendingMoviesTrigger

    fun getTrendingMovies(mediaType: String, timeWindow: String){
        CoroutineScope(Dispatchers.IO).launch {
            headers = Backend.authHeaders()
        }.invokeOnCompletion {
            movieApi.getTrendingMovies(mediaType, timeWindow,headers)
                .enqueue(object : Callback<MovieListResponse> {
                    override fun onResponse(
                        call: Call<MovieListResponse>,
                        response: Response<MovieListResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movieListResponse = response.body()
                            getTrendingMoviesTrigger.postValue(movieListResponse)
                        } else {
                            getTrendingMoviesTrigger.postValue(null)
                        }
                    }
                    override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                        getTrendingMoviesTrigger.postValue(null)
                    }
                })
        }

    }

    fun searchShows(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            headers = Backend.authHeaders()
        }.invokeOnCompletion {
            movieApi.searchShows(query, headers)
                .enqueue(object : Callback<MovieListResponse> {
                    override fun onResponse(
                        call: Call<MovieListResponse>,
                        response: Response<MovieListResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movieListResponse = response.body()
                            getTrendingMoviesTrigger.postValue(movieListResponse)
                        } else {
                            getTrendingMoviesTrigger.postValue(null)
                        }
                    }
                    override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                        getTrendingMoviesTrigger.postValue(null)
                    }
                })
        }

    }
}