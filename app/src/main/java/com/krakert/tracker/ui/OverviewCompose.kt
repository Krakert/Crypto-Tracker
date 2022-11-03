package com.krakert.tracker.ui

import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
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
import androidx.wear.compose.material.*
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.FavoriteCoin
import com.krakert.tracker.models.ui.Currency
import com.krakert.tracker.models.ui.OverviewMergedCoinData
import com.krakert.tracker.models.ui.ProblemState
import com.krakert.tracker.navigation.Screen
import com.krakert.tracker.ui.shared.*
import com.krakert.tracker.ui.theme.themeValues
import com.krakert.tracker.ui.viewmodel.OverviewViewModel
import com.krakert.tracker.ui.viewmodel.ViewStateOverview.*

@Composable
fun ListOverview(viewModel: OverviewViewModel, navController: NavHostController, lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current) {

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
                if (event == Lifecycle.Event.ON_RESUME){
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
                ShowProblem(response.exception){
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
                    resultAPi = response.data,
                    navController = navController
                )
        }
    }
}

@Composable
fun ShowStatsCoins(
    scrollState: ScalingLazyListState,
    resultAPi: List<OverviewMergedCoinData>,
    navController: NavHostController,
) {

    val path = Path()
    val context = LocalContext.current
    val sharedPreference = SharedPreference.sharedPreference(context = context)
    val currencyObject = sharedPreference.Currency?.let { Currency.valueOf(it) }
    val favoriteCoin = sharedPreference.FavoriteCoin


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
        resultAPi.forEach {
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
                        if (favoriteCoin == it.id) {
                            Text(
                                text = buildAnnotatedString {
                                    append(it.name.replaceFirstChar { it.uppercase() })
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
                                text = it.name.replaceFirstChar { it.uppercase() },
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
                            val points = arrayListOf<PointF>()
                            val pointsCon1 = arrayListOf<PointF>()
                            val pointsCon2 = arrayListOf<PointF>()

                            var maxData = it.graphData[0].price.toFloat()
                            var minData = it.graphData[0].price.toFloat()
//
                            it.graphData.forEach {
                                if (maxData < it.price.toFloat()) {
                                    maxData = it.price.toFloat()
                                }
                                if (minData > it.price.toFloat()) {
                                    minData = it.price.toFloat()
                                }
                            }

                            val pointsMean = arrayListOf<Float>()
                            // Calculate mean over 5 point and add that value to the list
                            for (i in 0 until it.graphData.size - 5 step 5) {
                                pointsMean.add(med(listOf(
                                    it.graphData[i].price.toFloat(),
                                    it.graphData[i + 1].price.toFloat(),
                                    it.graphData[i + 2].price.toFloat(),
                                    it.graphData[i + 3].price.toFloat(),
                                    it.graphData[i + 4].price.toFloat(),
                                )))
                            }

                            val distance = size.width / (pointsMean.size + 1)
                            var currentX = 0F

                            pointsMean.forEach { point ->
                                val y = (point - maxData) / (minData - maxData) * size.height
                                val x = currentX + distance
                                points.add(PointF(x, y))
                                currentX += distance
                            }

                            for (i in 1 until points.size) {
                                pointsCon1.add(PointF((points[i].x + points[i - 1].x) / 2, points[i - 1].y))
                                pointsCon2.add(PointF((points[i].x + points[i - 1].x) / 2, points[i].y))
                            }


                            path.reset()
                            path.moveTo(points.first().x, points.first().y)
                            for (i in 1 until points.size) {
                                path.cubicTo(
                                    pointsCon1[i - 1].x,
                                    pointsCon1[i - 1].y,
                                    pointsCon2[i - 1].x,
                                    pointsCon2[i - 1].y,
                                    points[i].x,
                                    points[i].y
                                )
                            }

                            drawPath(
                                path = path,
                                color = themeValues[3].colors.secondary,
                                style = Stroke(width = 6f)
                            )
                        }

                        val textData = it.currentPrice
                        Text(text = buildString {
                            append(currencyObject?.nameFull?.get(1))
                                .append(" ")
                                .append(textData)
                        })
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

fun med(list: List<Float>) = list.sorted().let {
    if (it.size % 2 == 0)
        (it[it.size / 2] + it[(it.size - 1) / 2]) / 2
    else
        it[it.size / 2]
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