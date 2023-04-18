package com.tta.thisweektvshows.util

import android.content.Context
import android.content.SharedPreferences

object SharedPref {

    private const val NAME = "SharedPref"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val MY_FAVOURITE_MOVIES = "MYFAVOURITEMOVIES"

    fun init(context: Context) {
        preferences = context.getSharedPreferences(
            NAME,
            MODE
        )
    }

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    var myFavouriteMovies: Set<String>
        get() = preferences.getStringSet(MY_FAVOURITE_MOVIES, emptySet())?: emptySet<String>()
        set(_myFavouriteMovies) = preferences.edit().putStringSet(MY_FAVOURITE_MOVIES, _myFavouriteMovies).apply()

}
