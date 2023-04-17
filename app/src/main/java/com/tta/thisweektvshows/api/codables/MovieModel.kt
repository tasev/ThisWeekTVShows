package com.tta.thisweektvshows.api.codables

import com.google.gson.annotations.SerializedName

data class MovieModel(
    @SerializedName("name")
    var name: String? = "",
    var email: String? = "",
    @SerializedName("poster_path")
    var posterPath: String? = "",
    @SerializedName("id")
    var id: Int? = -1,
    @SerializedName("overview")
    var overview: String? = ""
)