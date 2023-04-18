package com.tta.thisweektvshows.ui.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.tta.thisweektvshows.BuildConfig
import com.tta.thisweektvshows.NavScreen
import com.tta.thisweektvshows.R
import com.tta.thisweektvshows.util.SharedPref
import com.tta.thisweektvshows.viewmodel.ShowDetailsViewModel

@SuppressLint("RememberReturnType")
@Preview
@Composable
fun ShowDetailsScreen(
    navController: NavController = rememberNavController(),
    showDetailsViewModel: ShowDetailsViewModel = viewModel(),
    tvId: Int = -1
) {
    val myFavouriteMovies: HashSet<String> = SharedPref.myFavouriteMovies.toHashSet()

    var isOneOfMyFavouriteMovies by remember { mutableStateOf(myFavouriteMovies.contains("$tvId")) }

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val imageSize = ((screenWidth - 20.dp) * 0.2f)

    if (showDetailsViewModel.getMovieDetailsLiveData.value == null) {
        showDetailsViewModel.getTrendingMovies(tvId)
    }

    if (showDetailsViewModel.getSimilarShowsLiveData.value == null) {
        showDetailsViewModel.getSimilarShows(tvId)
    }

    val movieDetailsResponse by showDetailsViewModel.getMovieDetailsLiveData.observeAsState()

    val similarShowsData by showDetailsViewModel.getSimilarShowsLiveData.observeAsState()



    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            // Header image
            ConstraintLayout {
                val (mainImage, isFavouriteImage) = createRefs()
                AsyncImage(
                    model = "${BuildConfig.BASE_IMAGE_URL}${movieDetailsResponse?.posterPath}",
                    contentDescription = "Header image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.6f)
                        .clip(shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)),
                    contentScale = ContentScale.Crop
                )
                AsyncImage(model = if(isOneOfMyFavouriteMovies) R.drawable.heart else R.drawable.heart_outline,
                    contentDescription = "is Favourite image",
                    modifier = Modifier
                        .constrainAs(isFavouriteImage) {
                            top.linkTo(parent.top)
                            end.linkTo(parent.end)
                        }
                        .clickable {
                            if(isOneOfMyFavouriteMovies){
                                myFavouriteMovies.remove("$tvId")
                            }else {
                                myFavouriteMovies.add("$tvId")
                            }
                            isOneOfMyFavouriteMovies= !isOneOfMyFavouriteMovies
                            SharedPref.myFavouriteMovies = myFavouriteMovies
                        }
                        .width(80.dp)
                        .height(80.dp)
                        .padding(16.dp))
            }
            // Title
            Text(
                text = movieDetailsResponse?.name ?: "",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(10.dp)
            )

            // Description
            Text(
                text = movieDetailsResponse?.overview ?: "",
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 8.dp)
            )

            // Similar Shows
            Text(
                text = "Similar Shows",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier.padding(10.dp)
            )

            // Horizontal images
            LazyRow(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
            ) {

                items(similarShowsData?.results?.size ?: 0) { index ->
                    AsyncImage(
                        model = "${BuildConfig.BASE_IMAGE_URL}${similarShowsData?.results?.get(index)?.posterPath ?: ""}",
                        contentDescription = "Similar Show image $index",
                        modifier = Modifier
                            .width(imageSize)
                            .aspectRatio(0.7f)
                            .clickable {
                                val tvIdSimilar = similarShowsData?.results?.get(index)?.id ?: -1

                                navController.navigate("${NavScreen.ShowDetails.route}/${tvIdSimilar}")

                                // if you want to stay at the same screen instead of creating a new instance of ShowDetailsScreen
                                // showDetailsViewModel.getTrendingMovies(tvIdSimilar)
                                // showDetailsViewModel.getSimilarShows(tvIdSimilar)
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}