package com.krakert.tracker.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.krakert.tracker.R
import com.krakert.tracker.model.Coin
import com.krakert.tracker.navigation.Screen
import com.krakert.tracker.state.ViewStateData
import com.krakert.tracker.state.ViewStateOverview
import com.krakert.tracker.viewmodel.OverviewViewModel

@Composable
fun ListOverview(viewModel: OverviewViewModel, navController: NavHostController) {

    val scrollState = rememberScalingLazyListState()
    Scaffold(
        timeText = {
            if (!scrollState.isScrollInProgress) {
                TimeText()
            }
        },
        vignette = {
            // Only show a Vignette for scrollable screens. This code lab only has one screen,
            // which is scrollable, so we show it all the time.
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = scrollState
            )
        }
    ) {
        when (val listResult = viewModel.favoriteCoins.collectAsState().value) {
            is ViewStateOverview.Empty -> {
                ShowEmptyState(R.string.txt_empty_overview, navController)
            }
            is ViewStateOverview.Error -> {
                ShowIncorrectState(R.string.txt_toast_error, viewModel)
            }
            ViewStateOverview.Loading -> {
                Loading()
            }
            is ViewStateOverview.Success -> {
                println(listResult.favorite.Favorite)
                ShowStatsCoins(scrollState = scrollState, listResult = listResult.favorite.Favorite, viewModel = OverviewViewModel(), navController = navController)
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
fun ShowIncorrectState(@StringRes text: Int, viewModel: OverviewViewModel){
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
fun ShowStatsCoins(
    scrollState: ScalingLazyListState,
    listResult: List<Coin>?,
    viewModel: OverviewViewModel,
    navController: NavHostController
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
        verticalArrangement = Arrangement.Center,
        autoCentering = AutoCenteringParams(itemIndex = 0),
        state = scrollState
    ) {
        // For each index in my favorites
        listResult?.size?.let {
            items(it) { index ->
                viewModel.getHistoryCoin(listResult[index].idCoin.toString())
                Row(modifier = Modifier.fillMaxSize()) {
                    CenterElement {
                        Text(
                            text = listResult[index].id.toString(),
                            modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colors.primary,
                        )
                        // Here I load the data needed for the graph
                        when (val dataCoin = viewModel.dataCoin.collectAsState().value) {
                            is ViewStateData.Error -> {
                               Text(text = "Could not load the data")
                            }
                            is ViewStateData.Success -> {
                                println(dataCoin.marketChart.prices)
                            }
                            is ViewStateData.Loading -> {
                                Loading()
                            }
                        }
                        // Here we can load the latest price
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
                        text = stringResource(R.string.txt_add_coin_more),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center

                    )
                    IconButton(
                        Modifier.size(ButtonDefaults.LargeButtonSize),
                        Icons.Rounded.PlusOne
                    ) {
                        navController.navigate(Screen.Add.route)
                    }
                }
            }
        }
    }
}