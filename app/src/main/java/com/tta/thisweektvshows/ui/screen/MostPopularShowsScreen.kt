package com.tta.thisweektvshows.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.tta.thisweektvshows.NavScreen
import com.tta.thisweektvshows.api.codables.MovieModel
import com.tta.thisweektvshows.ui.components.MostPopularShowMainItemView
import com.tta.thisweektvshows.viewmodel.MostPopularShowsViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Preview
@Composable
fun MostPopularShowsScreen(
    navController: NavController = rememberNavController(),
    mostPopularShowsViewModel: MostPopularShowsViewModel = viewModel()
) {

    if (mostPopularShowsViewModel.getTrendingMoviesLiveData.value == null) {
        mostPopularShowsViewModel.getTrendingMovies("tv", "week")
    }

    val movieListResponse by mostPopularShowsViewModel.getTrendingMoviesLiveData.observeAsState()

    var query by remember { mutableStateOf("") }
    var searchJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    fun search(query: String) {
        searchJob?.cancel()
        searchJob = coroutineScope.launch {
            delay(500)
            if(query.isNotBlank()){
                mostPopularShowsViewModel.searchShows(query)
            }else{
                mostPopularShowsViewModel.getTrendingMovies("tv", "week")
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        TextField(
            value = query,
            onValueChange = {
                query = it
                search(it)
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            singleLine = true,
            trailingIcon = {
                Icon(
                    painter = painterResource(id = com.google.android.material.R.drawable.ic_search_black_24),
                    contentDescription = "Search Icon"
                )
            }
        )

        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
            items(movieListResponse?.results?.size?:0) { index ->
                val clickedMovieRow = movieListResponse?.results?.get(index)?: MovieModel()
                MostPopularShowMainItemView(clickedMovieRow) {
                    navController.navigate("${NavScreen.ShowDetails.route}/${clickedMovieRow.id ?: 0}")
                }
            }
        }
    }
}