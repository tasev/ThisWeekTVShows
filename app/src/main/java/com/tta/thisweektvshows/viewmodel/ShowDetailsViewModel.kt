package com.tta.thisweektvshows.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tta.thisweektvshows.api.Backend
import com.tta.thisweektvshows.api.codables.MovieListResponse
import com.tta.thisweektvshows.api.codables.MovieModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShowDetailsViewModel : BaseViewModel() {

    private val getMovieDetailsTrigger = MutableLiveData<MovieModel?>()
    val getMovieDetailsLiveData: LiveData<MovieModel?> = getMovieDetailsTrigger


    fun getTrendingMovies(tvId: Int) {
        if(tvId==-1) return
        CoroutineScope(Dispatchers.IO).launch {
            headers = Backend.authHeaders()
        }.invokeOnCompletion {
            movieApi.getMovieDetails(tvId, headers)
                .enqueue(object : Callback<MovieModel> {
                    override fun onResponse(
                        call: Call<MovieModel>,
                        response: Response<MovieModel>
                    ) {
                        if (response.isSuccessful) {
                            val movieDetails = response.body()
                            getMovieDetailsTrigger.postValue(movieDetails)
                        } else {
                            getMovieDetailsTrigger.postValue(null)
                        }
                    }

                    override fun onFailure(call: Call<MovieModel>, t: Throwable) {
                        getMovieDetailsTrigger.postValue(null)
                    }
                })
        }
    }

    private val getSimilarShowsTrigger = MutableLiveData<MovieListResponse?>()
    val getSimilarShowsLiveData: LiveData<MovieListResponse?> = getSimilarShowsTrigger


    fun getSimilarShows(tvId: Int){
        if(tvId==-1) return
        CoroutineScope(Dispatchers.IO).launch {
            headers = Backend.authHeaders()
        }.invokeOnCompletion {
            movieApi.getSimilarShows(tvId, headers)
                .enqueue(object : Callback<MovieListResponse> {
                    override fun onResponse(
                        call: Call<MovieListResponse>,
                        response: Response<MovieListResponse>
                    ) {
                        if (response.isSuccessful) {
                            val movieListResponse = response.body()
                            getSimilarShowsTrigger.postValue(movieListResponse)
                        } else {
                            getSimilarShowsTrigger.postValue(null)
                        }
                    }
                    override fun onFailure(call: Call<MovieListResponse>, t: Throwable) {
                        getSimilarShowsTrigger.postValue(null)
                    }
                })
        }

    }
}