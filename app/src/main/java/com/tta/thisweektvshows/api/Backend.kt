package com.tta.thisweektvshows.api

object Backend {

    fun getIdToken(): String {
        return "8dd3a40cdacd660d79bce7c46bad942e"
    }

    fun authHeaders(): String {
        return getIdToken()
    }
}