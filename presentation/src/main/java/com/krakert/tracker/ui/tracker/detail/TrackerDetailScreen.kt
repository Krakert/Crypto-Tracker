package com.krakert.tracker.ui.tracker.detail

import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.rotary.onRotaryScrollEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import androidx.wear.compose.foundation.ExperimentalWearFoundationApi
import androidx.wear.compose.foundation.lazy.AutoCenteringParams
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListState
import androidx.wear.compose.foundation.rememberActiveFocusRequester
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.PositionIndicator
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.components.CenterElement
import com.krakert.tracker.ui.components.Divider
import com.krakert.tracker.ui.components.IconButton
import com.krakert.tracker.ui.components.Loading
import com.krakert.tracker.ui.components.ShowProblem
import com.krakert.tracker.ui.theme.themeValues
import com.krakert.tracker.ui.tracker.detail.ViewStateDetails.Loading
import com.krakert.tracker.ui.tracker.detail.ViewStateDetails.Problem
import com.krakert.tracker.ui.tracker.detail.ViewStateDetails.Success
import com.krakert.tracker.ui.tracker.detail.model.DetailCoinDisplay
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.launch

@Composable
fun TrackerDetailScreen(
    coinId: String,
    viewModel: DetailsViewModel,
    navController: NavHostController,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getDetailsByCoinId(coinId)
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }


    when (val response = viewModel.detailsCoin.collectAsState().value) {
        is Loading -> Loading()
        is Problem -> {
            ShowProblem(response.exception) {
                when (response.exception) {
                    ProblemState.SSL -> viewModel.openSettings()
                    else -> viewModel.getDetailsByCoinId(coinId = coinId)
                }
            }
        }
        is Success -> ShowDetailsCoin(
            detailsCoins = response.details,
            viewModel = viewModel,
            navController = navController)
    }
}

@OptIn(ExperimentalWearFoundationApi::class)
@Composable
fun ShowDetailsCoin(
    detailsCoins: DetailCoinDisplay,
    viewModel: DetailsViewModel,
    navController: NavHostController?,
) {

    val context = LocalContext.current

    val focusRequester = rememberActiveFocusRequester()
    val coroutineScope = rememberCoroutineScope()
    val scrollState = ScalingLazyListState()

    Scaffold(positionIndicator = {
        PositionIndicator(
            scalingLazyListState = scrollState
        )
    }) {
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
                top = 8.dp,
                start = 8.dp,
                end = 8.dp,
                bottom = 32.dp
            ),
            verticalArrangement = Arrangement.Center,
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = scrollState
        ) {
            item {
                CenterElement {
                    CoilImage(
                        imageModel = { detailsCoins.imageUrl },
                        imageOptions = ImageOptions(contentScale = ContentScale.Fit),
                        modifier = Modifier
                            .size(40.dp)
                            .wrapContentSize(align = Alignment.Center),
                    )
                    Text(
                        text = detailsCoins.name,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colors.primary,
                        fontSize = 24.sp
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.txt_price_change_percentage_24h),
                            fontSize = 20.sp,
                            color = themeValues[0].colors.secondary
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(
                                    String.format(
                                        "%4.2f",
                                        detailsCoins.marketData.priceChangePercentage24h
                                    )
                                )
                                appendInlineContent("inlineContent", "[icon]")
                            },
                            fontSize = 20.sp,
                            inlineContent = addIcon(detailsCoins.marketData.priceChangePercentage24h)
                        )
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = stringResource(R.string.txt_price_change_percentage_7d),
                            fontSize = 20.sp,
                            color = themeValues[0].colors.secondary
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(
                                    String.format(
                                        "%4.2f",
                                        detailsCoins.marketData.priceChangePercentage7d
                                    )
                                )
                                appendInlineContent("inlineContent", "[icon]")
                            },
                            fontSize = 20.sp,
                            inlineContent = addIcon(detailsCoins.marketData.priceChangePercentage7d)
                        )
                    }
                }
            }
            item { Divider() }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = stringResource(R.string.txt_latest_price),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = themeValues[0].colors.secondary
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = detailsCoins.marketData.currentPrice,
                            textAlign = TextAlign.Center,
                            fontSize = 28.sp,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colors.primary
                        )
                    }
                }
            }
            item { Divider() }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = stringResource(R.string.txt_details_circulation),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = themeValues[0].colors.secondary
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = detailsCoins.marketData.circulatingSupply,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
            item { Divider() }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = stringResource(R.string.txt_details_max_min),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = themeValues[0].colors.secondary
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = detailsCoins.marketData.low24h,
                        fontSize = 16.sp,
                    )
                    Text(
                        text = detailsCoins.marketData.high24h,
                        fontSize = 16.sp,
                    )
                }
            }
            item { Divider() }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = stringResource(R.string.txt_details_market_cap),
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                            color = themeValues[0].colors.secondary
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = detailsCoins.marketData.marketCap,
                            textAlign = TextAlign.Center,
                            fontSize = 18.sp,
                        )
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    CenterElement {
                        Text(
                            text = buildAnnotatedString {
                                append(
                                    String.format(
                                        "%4.2f",
                                        detailsCoins.marketData.marketCapChangePercentage24h
                                    )
                                )
                                append(" %")

                                appendInlineContent("inlineContent", "[icon]")
                            },
                            fontSize = 20.sp,
                            inlineContent = addIcon(detailsCoins.marketData.marketCapChangePercentage24h)
                        )
                    }
                }
            }
            item { Divider() }
            item {
                CenterElement {
                    Text(
                        text = stringResource(R.string.txt_details_remove_set),
                        modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    IconButton(
                        modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
                        imageVector = Icons.Rounded.Delete,
                        contentDescription = stringResource(
                            R.string.button_with_icon,
                            Icons.Rounded.Delete.name
                        ),
                        onClick = {
                            viewModel.removeCoinFromFavoriteCoins(
                                id = detailsCoins.id,
                                name = detailsCoins.name
                            )
                            navController?.popBackStack()
                            Toast.makeText(
                                context, context.getString(
                                    R.string.txt_toast_removed,
                                    detailsCoins.name
                                ), Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                    IconButton(
                        modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
                        imageVector = Icons.Rounded.Star,
                        contentDescription = stringResource(
                            id = R.string.button_with_icon,
                            Icons.Rounded.Star.name
                        ),
                        onClick = {
                            // TODO = sharedPreference.FavoriteCoin = coinId
                            Toast.makeText(
                                context, context.getString(
                                    R.string.txt_toast_set_tile,
                                    detailsCoins.name
                                ), Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

private fun addIcon(value: Double?): Map<String, InlineTextContent> {
    val inlineContent = mapOf(
        Pair(
            "inlineContent",
            InlineTextContent(
                Placeholder(
                    width = 24.sp,
                    height = 24.sp,
                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                )
            ) {
                if (value != null) {
                    if (value > 0.0) {
                        Icon(Icons.Filled.ArrowDropUp,
                            "",
                            tint = MaterialTheme.colors.primaryVariant)
                    } else {
                        Icon(Icons.Filled.ArrowDropDown,
                            "",
                            tint = MaterialTheme.colors.secondaryVariant)
                    }
                } else {
                    Icon(Icons.Filled.Remove, "", tint = MaterialTheme.colors.error)
                }
            }
        )
    )
    return inlineContent
}