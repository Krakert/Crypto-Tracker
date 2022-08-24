package com.krakert.tracker.ui

import android.graphics.PointF
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.FavoriteCoin
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.Currency
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.navigation.Screen
import com.krakert.tracker.ui.state.ViewStateOverview
import com.krakert.tracker.ui.theme.themeValues
import com.krakert.tracker.ui.viewmodel.OverviewViewModel

@Composable
fun ListOverview(viewModel: OverviewViewModel, navController: NavHostController) {
    viewModel.getFavoriteCoins()

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
        val response by viewModel.stateOverview.collectAsState()

        when (response) {
            is ViewStateOverview.Empty -> ShowEmptyState(R.string.txt_empty_overview, navController)
            is ViewStateOverview.Error -> ShowIncorrectState(R.string.txt_toast_error, viewModel)
            is ViewStateOverview.Loading -> {
                ShowLoadingState(
                    scrollState = scrollState,
                    listFavoriteCoins = (response as ViewStateOverview.Loading).favorite,
                    viewModel = viewModel
                )
            }
            is ViewStateOverview.Loaded -> {
                ShowStatsCoins(
                    scrollState = scrollState,
                    resultAPi = (response as ViewStateOverview.Loaded).httpResponse,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun ShowEmptyState(@StringRes text: Int, navController: NavHostController) {
    CenterElement {
        Text(
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            text = stringResource(text),
            fontSize = 16.sp,
            textAlign = TextAlign.Center

        )
        IconButton(Modifier.size(ButtonDefaults.LargeButtonSize), Icons.Rounded.PlusOne){
            navController.navigate(Screen.Add.route)
        }
    }
}

@Composable
private fun ShowIncorrectState(@StringRes text: Int, viewModel: OverviewViewModel){
    val context = LocalContext.current
    CenterElement {
        IconButton(
            Modifier
                .size(ButtonDefaults.LargeButtonSize)
                .padding(top = 8.dp), Icons.Rounded.Cached) {
            viewModel.getFavoriteCoins()
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
        Text(
            text = stringResource(text),
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun ShowLoadingState(
    scrollState: ScalingLazyListState,
    listFavoriteCoins: ArrayList<FavoriteCoin>,
    viewModel: OverviewViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val context = LocalContext.current
    val sharedPreference = SharedPreference.sharedPreference(context = context)
    val favoriteCoin = sharedPreference.FavoriteCoin

    viewModel.getAllDataByListCoinIds(listFavoriteCoins)

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
        // For each index in my favorites
        items(listFavoriteCoins.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CenterElement {
                    if (favoriteCoin == listFavoriteCoins[index].id) {
                        Text(
                            text = buildAnnotatedString {
                                append(listFavoriteCoins[index].name.replaceFirstChar { it.uppercase() })
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
                            text = listFavoriteCoins[index].name.replaceFirstChar { it.uppercase() },
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary,
                            fontSize = 20.sp,
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ShowStatsCoins(
    scrollState: ScalingLazyListState,
    resultAPi: Resource<MutableMap<String, MutableMap<String, Any>>>,
    navController: NavHostController
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
        resultAPi.data?.forEach { (k, v) ->

            item {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            onClick = {
                                navController.currentBackStackEntry?.savedStateHandle?.set(
                                    key = "Coin",
                                    value = k
                                )
                                navController.navigate(Screen.Details.route)
                            }
                        )
                ){
                    CenterElement {
                        if (favoriteCoin == k) {
                            Text(
                                text = buildAnnotatedString {
                                    append(v["name"].toString().replaceFirstChar { it.uppercase() })
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
                                text = v["name"].toString().replaceFirstChar { it.uppercase() },
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

                            @Suppress("UNCHECKED_CAST")
                            val marketChart = v["market_chart"] as List<List<Double>>

                            var maxData = marketChart[0][1].toFloat()
                            var minData = marketChart[0][1].toFloat()

                            marketChart.forEachIndexed { _, index ->
                                if (maxData < index[1].toFloat()) {
                                    maxData = index[1].toFloat()
                                }
                                if (minData > index[1].toFloat()){
                                    minData = index[1].toFloat()
                                }
                            }

                            val pointsMean = arrayListOf<Float>()
                            // Calculate mean over 5 point and add that value to the list
                            for (i in 0 until marketChart.size - 5 step 5){
                                pointsMean.add(med(listOf(
                                    marketChart[i][1].toFloat(),
                                    marketChart[i + 1][1].toFloat(),
                                    marketChart[i + 2][1].toFloat(),
                                    marketChart[i + 3][1].toFloat(),
                                    marketChart[i + 4][1].toFloat(),
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
                                    pointsCon1[i - 1].x, pointsCon1[i - 1].y, pointsCon2[i - 1].x, pointsCon2[i - 1].y,
                                    points[i].x, points[i].y
                                )
                            }

                            drawPath(
                                path = path,
                                color = themeValues[3].colors.secondary,
                                style = Stroke(width = 6f)
                            )
                        }

                        val textData = v[sharedPreference.Currency?.lowercase()]
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