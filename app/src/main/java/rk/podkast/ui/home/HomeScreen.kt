package rk.podkast.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import rk.podkast.data.model.EpisodeDto
import rk.podkast.data.model.PodcastDto

@Composable
fun HomeScreen(modifier: Modifier = Modifier, contentPadding: PaddingValues) {
    val viewModel: HomeViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        if (uiState.isLoading) CircularProgressIndicator()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = contentPadding
        ) {
            if (uiState.banner.isNotEmpty()) item {
                HomeBanner(episodeDto = uiState.banner[0])
            }
            item {
                HomeSection(
                    sectionTitle = "Popular Podcasts", items = uiState.popularPodcasts.shuffled()
                )
            }
            item {
                HomeSection(
                    sectionTitle = "Recently Played", items = uiState.popularPodcasts.shuffled()
                )
            }
            item {
                HomeSection(sectionTitle = "For You", items = uiState.popularPodcasts.shuffled())
            }


        }
    }
}

@Composable
fun TopTitle(
    modifier: Modifier, title: String
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title)
        TextButton(onClick = { }) {
            Text(text = "See All")
        }
    }
}

@Composable
fun HomeBanner(
    episodeDto: EpisodeDto
) {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .height(120.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(0.dp, 0.dp, 0.dp, 0.dp, 0.dp, 0.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 10.dp)
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color.DarkGray),
                model = episodeDto.image,
                contentScale = ContentScale.Crop,
                contentDescription = ""

            )
            Column(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .weight(1f)
                    .padding(horizontal = 8.dp, vertical = 16.dp)
            ) {
                Text(
                    text = episodeDto.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Episode Description is here",
                    style = MaterialTheme.typography.labelSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            FilledIconButton(
                onClick = { /*TODO*/ },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.padding(horizontal = 12.dp)
            ) {
                Icon(imageVector = Icons.Rounded.PlayArrow, contentDescription = "")
            }

        }
    }
}


@Composable
fun HomeSection(
    sectionTitle: String, onSeeAll: () -> Unit = {}, items: List<PodcastDto>
) {
    Column {
        TopTitle(
            title = sectionTitle, modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            contentPadding = PaddingValues(horizontal = 8.dp)
        ) {
            items(items) { podcast ->
                AsyncImage(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    model = podcast.imageUrl,
                    contentDescription = ""
                )
            }
        }
    }


}


