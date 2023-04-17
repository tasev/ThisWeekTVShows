package com.tta.thisweektvshows.viewmodel

import androidx.lifecycle.ViewModel
import com.tta.thisweektvshows.BuildConfig
import com.tta.thisweektvshows.api.MovieApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

abstract class BaseViewModel : ViewModel() {

    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val movieApi = retrofit.create(MovieApi::class.java)

    var headers: String = ""

}
