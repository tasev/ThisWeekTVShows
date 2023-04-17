package com.tta.thisweektvshows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tta.thisweektvshows.ui.screen.MostPopularShowsScreen
import com.tta.thisweektvshows.ui.screen.ShowDetailsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation()
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavScreen.getStartDestination()
    ) {
        composable(NavScreen.MostPopularShows.route) { MostPopularShowsScreen(navController) }
        composable("${NavScreen.ShowDetails.route}/{movieId}",
            arguments = listOf(navArgument("movieId") {
                type = NavType.IntType
            })
        ) { ShowDetailsScreen(navController, tvId = it.arguments?.getInt("movieId") ?: -1) }
    }
}


sealed class NavScreen(val route: String) {
    object MostPopularShows : NavScreen("MostPopularShows")
    object ShowDetails : NavScreen("ShowDetails")

    companion object {
        fun getStartDestination() = MostPopularShows.route
    }
}