package com.krakert.tracker.ui.tracker.add

import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.lazy.items
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import com.krakert.tracker.ui.components.Loading
import com.krakert.tracker.ui.components.OnDisplay
import com.krakert.tracker.ui.components.OnError
import com.krakert.tracker.ui.components.OnLoading
import com.krakert.tracker.ui.components.ShowProblem
import com.krakert.tracker.ui.tracker.add.component.ChipCoin
import com.krakert.tracker.ui.tracker.add.model.ListCoinsItemDisplay
import com.krakert.tracker.ui.tracker.model.ProblemState.SSL
import kotlinx.coroutines.launch

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

    val scrollState = ScalingLazyListState()
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
            is OnError -> {
                ShowProblem(state.errorDisplay) {
                    when (state.errorDisplay) {
                        SSL -> viewModel.openSettings()
                        else -> viewModel.getListCoins()
                    }
                }
            }

            OnLoading -> {
                Loading()
            }

            is OnDisplay -> {
                ShowList(
                    scrollState = scrollState,
                    listCoins = state.display.result,
                    viewModel = viewModel
                )
            }
        }

    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
private fun ShowList(
    scrollState: ScalingLazyListState,
    listCoins: List<ListCoinsItemDisplay>,
    viewModel: AddCoinViewModel,
) {

    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()

    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .onRotaryScrollEvent {
                coroutineScope.launch {
                    scrollState.scrollBy(it.verticalScrollPixels)
                    scrollState.animateScrollBy(0f)
                }
                true
            }
            .focusRequester(focusRequester)
            .focusable(),
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
        items(listCoins) { coin ->
            ChipCoin(coin = coin) {
                coin.isFavourite = !coin.isFavourite
                viewModel.toggleFavoriteCoin(coin)
            }
        }
    }
}