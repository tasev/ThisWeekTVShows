package com.tta.thisweektvshows.api.codables

import com.google.gson.annotations.SerializedName

data class MovieListResponse(
    var page: Int,
    @SerializedName("results")
    var results: ArrayList<MovieModel>
)