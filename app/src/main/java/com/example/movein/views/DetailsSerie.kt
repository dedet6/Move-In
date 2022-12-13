package com.example.movein.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.movein.models.TmdbSerie
import com.example.movein.viewmodels.MainViewModel

@Composable
fun DetailsSerie(
    classes: WindowSizeClass,
    viewmodel: MainViewModel,
    idSerie: String?
) {
    val classeLargeur = classes.widthSizeClass
    val detailsSerie = viewmodel.serie.collectAsState()
    if (idSerie != null) {
        viewmodel.getSeriesDetails(idSerie)
    }
    val span: (LazyGridItemSpanScope) -> GridItemSpan = { GridItemSpan(3) }
    when (classeLargeur) {
        WindowWidthSizeClass.Compact-> {
            LazyVerticalGrid(columns = GridCells.Fixed(2),
                modifier = Modifier
                    .background(Color.Black)) {
                item(span = span) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.backdrop_path,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(bottom = 20.dp),
                        contentScale = ContentScale.Crop
                    )
                }
                item(span = span) {
                    Titre(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    Synopsis(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    ReleasedDate(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    Genres(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    Headliners()
                }
                items(detailsSerie.value.credits.cast) { credit ->
                    HeadlinersList(credit = credit)
                }
            }
        }
        else -> {
            LazyVerticalGrid(columns = GridCells.Fixed(3),
                modifier = Modifier
                    .background(Color.Black)) {
                item(span = span) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.backdrop_path,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(bottom = 20.dp)
                            .height(200.dp)
                    )
                }
                item(span = span) {
                    Titre(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    Synopsis(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    ReleasedDate(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    Genres(detailsSerie = detailsSerie)
                }
                item(span = span) {
                    Headliners()
                }
                items(detailsSerie.value.credits.cast) { credit ->
                    HeadlinersList(credit = credit)
                }
            }
        }
    }
}

@Composable
fun Titre(detailsSerie: State<TmdbSerie>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(bottom = 20.dp)) {
        Text(text = detailsSerie.value.name, fontSize = 20.sp, modifier = Modifier
            .background(Color.White)
            .padding(10.dp),
            fontWeight = FontWeight.Bold)

    }
}

@Composable
fun Synopsis(detailsSerie: State<TmdbSerie>) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.background(Color.White)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500" + detailsSerie.value.poster_path,
            contentDescription = "",
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        )
        Spacer(Modifier.height(10.dp))
        Column {
            Text(
                text = "Synopsis",
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(10.dp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = detailsSerie.value.overview,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(10.dp)
            )
        }

    }
}

@Composable
fun ReleasedDate(detailsSerie: State<TmdbSerie>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
        .background(Color.Black)
        .padding(top = 30.dp, bottom = 30.dp)) {
        Text(
            text = "Released on " + detailsSerie.value.first_air_date,
            fontSize = 14.sp,
            modifier = Modifier
                .background(Color.White)
                .padding(10.dp)
        )
    }
}

@Composable
fun Genres(detailsSerie: State<TmdbSerie>) {
    Row(
        horizontalArrangement = Arrangement.SpaceAround,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp)
    ) {
        detailsSerie.value.genres.forEach { genre ->
            Text(text= genre.name, fontSize = 14.sp, modifier = Modifier
                .background(Color.White)
                .padding(10.dp))
        }
    }
}