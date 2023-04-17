package com.tta.thisweektvshows.api

import com.tta.thisweektvshows.api.codables.MovieListResponse
import com.tta.thisweektvshows.api.codables.MovieModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

public interface MovieApi {
    @Headers("Accept: application/json")
    @GET("trending/{media_type}/{time_window}")
    fun getTrendingMovies(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") authHeader: String?
    )
            : Call<MovieListResponse>

    @Headers("Accept: application/json")
    @GET("tv/{tv_id}")
    fun getMovieDetails(
        @Path("tv_id") tvId: Int?,
        @Query("api_key") authHeader: String?
    ): Call<MovieModel>

    @Headers("Accept: application/json")
    @GET("tv/{tv_id}/similar")
    fun getSimilarShows(
        @Path("tv_id") tvId: Int?,
        @Query("api_key") authHeader: String?
    )
            : Call<MovieListResponse>

    @Headers("Accept: application/json")
    @GET("search/tv")
    fun searchShows(
        @Query("query") query: String?,
        @Query("api_key") authHeader: String?
    )
            : Call<MovieListResponse>
}