package com.krakert.tracker.ui.tracker.add

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.palette.graphics.Palette
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.krakert.tracker.ui.components.Loading
import com.krakert.tracker.ui.components.ShowProblem
import com.krakert.tracker.ui.tracker.add.model.ListCoinsItemDisplay
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_WIDTH_DP
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin

@Composable
fun TrackerAddCoinScreen(
    viewModel: AddCoinViewModel,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {

    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getListCoins()
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val scrollState = rememberScalingLazyListState()
    Scaffold(
        timeText = {
            if (!scrollState.isScrollInProgress) {
                TimeText()
            }
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = scrollState
            )
        }
    ) {
        when (val state = viewModel.addViewState.collectAsState().value) {
            is ViewStateAddCoin.Problem -> {
                ShowProblem(state.exception) {
                    when (state.exception) {
                        ProblemState.SSL -> viewModel.openSettings()
                        else -> viewModel.getListCoins()
                    }
                }
            }

            ViewStateAddCoin.Loading -> {
                Loading()
            }

            is ViewStateAddCoin.Success -> {
                ShowList(
                    scrollState = scrollState,
                    listCoins = state.coins.result,
                    viewModel = viewModel
                )
            }
        }

    }
}

@Composable
private fun ShowList(
    scrollState: ScalingLazyListState,
    listCoins: List<ListCoinsItemDisplay>,
    viewModel: AddCoinViewModel,
) {
    val context = LocalContext.current

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = 32.dp,
            start = 8.dp,
            end = 8.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        autoCentering = AutoCenteringParams(itemIndex = 0),
        state = scrollState
    ) {
        listCoins.forEach { coin ->
            item {
                ChipCoin(coin = coin) {
                    coin.isFavourite = !coin.isFavourite
                    viewModel.toggleFavoriteCoin(coin, context = context)
                }
            }
        }
    }
}

@Composable
private fun ChipCoin(coin: ListCoinsItemDisplay, onClick: () -> Unit) {
    var palette by remember { mutableStateOf<Palette?>(null) }
    var isFavorite by remember { mutableStateOf(coin.isFavourite) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape = RoundedCornerShape(50))
            .clickable {
                isFavorite = !isFavorite
                onClick()
            }
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(palette?.lightVibrantSwatch?.rgb ?: 0).copy(alpha = 0.5f),
                        MaterialTheme.colors.surface
                    )
                )
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(
                imageModel = { coin.imageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit
                ),
                modifier = Modifier
                    .size(32.dp)
                    .wrapContentSize(align = Alignment.Center),
                component = rememberImageComponent {
                    +PalettePlugin { palette = it }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f, true),
                text = coin.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector =
                if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }, contentDescription = null
            )
        }
    }

}

@Preview(widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP)
@Composable
fun PreviewChipCoin() {
    ChipCoin(coin = ListCoinsItemDisplay("bitcoin", "", "Text that should be to long", true)) {}
}
