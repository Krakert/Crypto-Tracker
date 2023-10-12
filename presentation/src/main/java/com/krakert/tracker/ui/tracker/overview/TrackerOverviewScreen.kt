package com.krakert.tracker.ui.tracker.overview

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.ButtonDefaults
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
import com.krakert.tracker.R
import com.krakert.tracker.ui.components.CenterElement
import com.krakert.tracker.ui.components.Divider
import com.krakert.tracker.ui.components.IconButton
import com.krakert.tracker.ui.components.Loading
import com.krakert.tracker.ui.components.Screen
import com.krakert.tracker.ui.components.ShowProblem
import com.krakert.tracker.ui.theme.themeValues
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.Loading
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.Problem
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.Success
import com.krakert.tracker.ui.tracker.overview.model.OverviewCoinDisplay

@Composable
fun TrackerOverviewScreen(
    viewModel: OverviewViewModel,
    navController: NavHostController,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {
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
        DisposableEffect(lifeCycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_RESUME) {
                    viewModel.getAllOverviewData()
                }
            }
            lifeCycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifeCycleOwner.lifecycle.removeObserver(observer)
            }
        }

        when (val response = viewModel.overviewViewState.collectAsState().value) {
            is Loading -> Loading()
            is Problem -> {
                ShowProblem(response.exception) {
                    when (response.exception) {
                        ProblemState.SSL -> viewModel.openSettings()
                        ProblemState.EMPTY -> navController.navigate(Screen.Add.route)
                        else -> viewModel.getAllOverviewData()
                    }
                }
            }

            is Success ->
                ShowStatsCoins(
                    scrollState = scrollState,
                    result = response.data,
                    navController = navController
                )
        }
    }
}

@Composable
fun ShowStatsCoins(
    scrollState: ScalingLazyListState,
    result: OverviewCoinDisplay,
    navController: NavHostController,
) {
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(
            top = 8.dp,
            bottom = 32.dp
        ),
        verticalArrangement = Arrangement.Center,
        autoCentering = AutoCenteringParams(itemIndex = 0),
        state = scrollState
    ) {
        result.result.forEach {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "Coin",
                                    value = it.id
                                )
                                navController.navigate(Screen.Details.route)
                            }
                        )
                ) {
                    CenterElement {
                        if (result.tileCoin == it.id) {
                            Text(
                                text = buildAnnotatedString {
                                    append(it.name)
                                    appendInlineContent("inlineContent", "[icon]")
                                },
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary,
                                fontSize = 20.sp,
                                inlineContent = addIconFavorite()
                            )
                        } else {
                            Text(
                                text = it.name,
                                modifier = Modifier.padding(bottom = 8.dp),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary,
                                fontSize = 20.sp,
                            )
                        }


                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(105.dp)
                                .padding(bottom = 8.dp)
                        ) {
                            // Get the canvas size
                            val canvasWidth = size.width
                            val canvasHeight = size.height

                            // Calculate the transformation
                            val pathWidth = it.marketChart.getBounds().width
                            val pathHeight = it.marketChart.getBounds().height
                            val scaleX = canvasWidth / pathWidth
                            val scaleY = canvasHeight / pathHeight

                            // Calculate translation factors to center the path horizontally
                            val translateX = -(it.marketChart.getBounds().left * scaleX)
                            val translateY = (canvasHeight - pathHeight * scaleY) / 2

                            // Draw the path with the applied transformation
                            drawContext.canvas.apply {
                                scale(scaleX, scaleY)
                                translate(translateX, translateY)
                                drawPath(
                                    path = it.marketChart,
                                    color = themeValues[3].colors.secondary,
                                    style = Stroke(width = 6.0f)
                                )
                            }
                        }
                        Text(text = it.currentPrice)
                        Divider()
                    }
                }
            }
        }
        // Last item of the row, add more coins to track
        item {
            Row(modifier = Modifier.fillMaxSize()) {
                CenterElement {
                    Text(
                        modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                        text = stringResource(R.string.txt_add_more_change_settings),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center

                    )
                }
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                IconButton(
                    Modifier.size(ButtonDefaults.LargeButtonSize),
                    Icons.Rounded.PlusOne
                ) {
                    navController.navigate(Screen.Add.route)
                }
                IconButton(
                    Modifier.size(ButtonDefaults.LargeButtonSize),
                    Icons.Rounded.Settings
                ) {
                    navController.navigate(Screen.Settings.route)
                }
            }
        }
    }
}

private fun addIconFavorite(): Map<String, InlineTextContent> {
    val myId = "inlineContent"

    val inlineContent = mapOf(
        Pair(
            myId,
            InlineTextContent(
                Placeholder(
                    width = 16.sp,
                    height = 16.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                Icon(Icons.Filled.Star, "", tint = MaterialTheme.colors.secondaryVariant)
            }
        )
    )
    return inlineContent
}