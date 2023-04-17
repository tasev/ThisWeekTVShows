package com.tta.thisweektvshows.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.AsyncImage
import com.tta.thisweektvshows.BuildConfig
import com.tta.thisweektvshows.R
import com.tta.thisweektvshows.api.codables.MovieModel

@Composable
fun MostPopularShowMainItemView(
    movieModel: MovieModel,
    _onClick: () -> Unit = {}
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { _onClick.invoke() }
            .aspectRatio(0.7f).padding(dimensionResource(id = R.dimen.margin_xxs))
    ) {
        val (image, titleText, subtitleText) = createRefs()
        AsyncImage(
            model = "${BuildConfig.BASE_IMAGE_URL}${movieModel.posterPath}",
            contentDescription = "Background Image",
            modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    height = Dimension.fillToConstraints
                    width = Dimension.fillToConstraints
                }
                .background(Color.White),
            contentScale = ContentScale.Crop
        )

        Text(
            text = movieModel.name?:"",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .constrainAs(titleText) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.matchParent
                    height = Dimension.wrapContent
                }
                .background(Color.White.copy(alpha = 0.9f))
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}


@Preview
@Composable
fun MyItemPreview() {
    MostPopularShowMainItemView(
        MovieModel("Movie", "email@gmail.com", "")
    )
}
