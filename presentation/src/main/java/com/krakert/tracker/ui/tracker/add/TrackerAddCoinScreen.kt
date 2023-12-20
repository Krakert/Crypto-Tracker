package com.krakert.tracker.ui.tracker.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.ScalingLazyListState
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import androidx.wear.compose.material.rememberScalingLazyListState
import com.krakert.tracker.ui.components.Loading
import com.krakert.tracker.ui.components.ShowProblem
import com.krakert.tracker.ui.tracker.add.component.ChipCoin
import com.krakert.tracker.ui.tracker.add.model.ListCoinsItemDisplay
import com.krakert.tracker.ui.tracker.model.ProblemState

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
                    viewModel.toggleFavoriteCoin(coin)
                }
            }
        }
    }
}