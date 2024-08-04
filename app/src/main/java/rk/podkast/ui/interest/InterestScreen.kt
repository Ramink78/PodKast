package rk.podkast.ui.interest

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemSpanScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import rk.podkast.R
import rk.podkast.data.GenreRepositoryImpl
import rk.podkast.data.database.AppDatabase
import rk.podkast.data.database.entity.Genre
import rk.podkast.ui.theme.PodKastTheme
import kotlin.math.ceil
import kotlin.math.roundToInt


@Composable
fun InterestScreen(
    modifier: Modifier = Modifier,
) {
    val density = LocalDensity.current
    val gridItemSize = 56.dp
    val gridItemInnerPadding = 4.dp
    val context = LocalContext.current
    val viewModel: InterestScreenViewModel = viewModel(
        factory =
        InterestScreenViewModel.Companion.Factory(
            GenreRepositoryImpl(
                AppDatabase.getInstance(context.applicationContext).genreDao(),
                context.applicationContext
            )
        )
    )
    val textMeasurer = rememberTextMeasurer()
    val textStyle = MaterialTheme.typography.labelLarge
    val uiState by viewModel.uiState.collectAsState()

    val fabPadding = 24.dp
    val fabSize = 56.dp
    val listContentBottomPadding =
        fabSize + fabPadding + WindowInsets.navigationBars.asPaddingValues()
            .calculateBottomPadding()
    Box(modifier = modifier) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
            columns = GridCells.FixedSize(gridItemSize),
            contentPadding = PaddingValues(
                start = 8.dp,
                end = 8.dp,
                bottom = listContentBottomPadding
            )
        ) {
            item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                Header()
            }
            items(
                uiState.allGenres, key = { it.type },
                span = {
                    with(density) {
                        val textToMeasure = context.getString(it.type.nameRes)
                        val textLayoutResult = textMeasurer.measure(
                            text = textToMeasure,
                            style = textStyle, softWrap = false, maxLines = 1
                        )
                        val requiredSpan = calculateRequiredSpan(
                            textLayoutResult = textLayoutResult,
                            horizontalPaddingInPx = gridItemInnerPadding.toPx().roundToInt(),
                            spanSizeInPx = gridItemSize.toPx().roundToInt()
                        )
                        GridItemSpan(requiredSpan)
                    }

                }
            ) {
                GenreItem(
                    modifier = Modifier
                        .height(gridItemSize),
                    text = stringResource(id = it.type.nameRes),
                    onSelect = { viewModel.toggleGenre(it.type) },
                    isSelected = uiState.interestGenres.contains(Genre(it.type)),
                    textStyle = textStyle,
                    textPadding = gridItemInnerPadding
                )

            }


        }
        FloatingActionButton(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(fabPadding)
                .align(Alignment.BottomEnd),
            onClick = {},
            containerColor = MaterialTheme.colorScheme.primary,
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight,
                contentDescription = ""
            )
        }
    }
}

private fun LazyGridItemSpanScope.calculateRequiredSpan(
    textLayoutResult: TextLayoutResult, horizontalPaddingInPx: Int, spanSizeInPx: Int
): Int {
    val requireWidthForText = textLayoutResult.size.width + 2 * horizontalPaddingInPx
    return ceil((requireWidthForText.toFloat() / spanSizeInPx)).toInt()
        .coerceIn(1..maxLineSpan)
}

@Composable
private fun Header() {
    Column(
        Modifier.padding(vertical = 48.dp)
    ) {
        Text(
            text = stringResource(R.string.interest_screen_welcome),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.interest_screen_guide),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = .6f),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

@Composable
private fun GenreItem(
    modifier: Modifier = Modifier,
    text: String,
    onSelect: () -> Unit = {},
    isSelected: Boolean = false,
    textPadding: Dp = 4.dp,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {
    val strokeColor by animateColorAsState(
        targetValue =
        if (isSelected) Color.Unspecified else MaterialTheme.colorScheme.outlineVariant,
        label = ""
    )
    val cardColor by animateColorAsState(
        targetValue =
        if (isSelected) MaterialTheme.colorScheme.surfaceContainerHighest else Color.Unspecified,
        label = ""
    )
    val textColor by animateColorAsState(
        targetValue =
        if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface,
        label = ""
    )
    OutlinedCard(
        modifier = modifier,
        onClick = onSelect,
        colors = CardDefaults.outlinedCardColors(
            containerColor = cardColor,
        ),
        shape = RoundedCornerShape(10.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = SolidColor(strokeColor))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(textPadding), contentAlignment = Alignment.Center
        ) {
            Text(
                textAlign = TextAlign.Center,
                style = textStyle,
                text = text, color = textColor, maxLines = 1, softWrap = false
            )
        }

    }

}


@Preview
@Composable
private fun InterestScreenPreview() {
    PodKastTheme(darkTheme = true) {
        InterestScreen(modifier = Modifier.fillMaxSize())
    }
}