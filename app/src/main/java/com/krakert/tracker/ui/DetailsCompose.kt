package com.krakert.tracker.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
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
import com.krakert.tracker.models.*
import com.krakert.tracker.models.ui.Currency
import com.krakert.tracker.models.ui.DetailsCoin
import com.krakert.tracker.models.ui.ProblemState
import com.krakert.tracker.ui.shared.*
import com.krakert.tracker.ui.theme.themeValues
import com.krakert.tracker.ui.viewmodel.DetailsViewModel
import com.krakert.tracker.ui.viewmodel.ViewStateDetailsCoins.*
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Preview(
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun PreviewShowDetails() {
    ShowDetailsCoin(detailsCoins =
    DetailsCoin(
        name = "Bitcoin",
        image = null,
        currentPrice = "16.043,86",
        priceChangePercentage24h = -2.12,
        priceChangePercentage7d = -2.1,
        circulatingSupply = 19214981.0,
        high24h = 16609.39,
        low24h = 15738.18,
        marketCap = 317737907422.0,
        marketCapChangePercentage24h = 2.1,
    ), coinId = "bitcoin", navController = null, viewModel = null)
}

@Composable
fun ShowDetails(
    coinId: String,
    viewModel: DetailsViewModel,
    navController: NavHostController,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
) {

    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.coinId = coinId
                viewModel.getDetailsCoinByCoinId()
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
                    else -> viewModel.getDetailsCoinByCoinId()
                }
            }
        }
        is Success -> ShowDetailsCoin(
            detailsCoins = response.details,
            viewModel = viewModel,
            coinId = coinId,
            navController = navController)
    }
}

@Composable
fun ShowDetailsCoin(
    detailsCoins: DetailsCoin,
    viewModel: DetailsViewModel?,
    coinId: String,
    navController: NavHostController?,
) {

    val context = LocalContext.current
    val sharedPreference = SharedPreference.sharedPreference(context = context)
    val currencyObject = sharedPreference.Currency?.let { Currency.valueOf(it) }

    val scrollState = rememberScalingLazyListState()
    ScalingLazyColumn(
        modifier = Modifier
            .fillMaxSize(),
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
                    imageModel = { detailsCoins.image?.large },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Fit),
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
                            append(String.format("%4.2f", detailsCoins.priceChangePercentage24h))
                            appendInlineContent("inlineContent", "[icon]")
                        },
                        fontSize = 20.sp,
                        inlineContent = addIcon(detailsCoins.priceChangePercentage24h)
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
                            append(String.format("%4.2f", detailsCoins.priceChangePercentage7d))
                            appendInlineContent("inlineContent", "[icon]")
                        },
                        fontSize = 20.sp,
                        inlineContent = addIcon(detailsCoins.priceChangePercentage7d)
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
                        text = buildString {
                            append(currencyObject?.nameFull?.get(1))
                                .append(" ")
                                .append(detailsCoins.currentPrice)
                        },
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
                        text = String.format("%,.0f", detailsCoins.circulatingSupply),
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
                    text = buildString {
                        append(currencyObject?.nameFull?.get(1))
                            .append(" ")
                            .append(detailsCoins.low24h)
                    },
                    fontSize = 16.sp,
                )
                Text(
                    text = buildString {
                        append(currencyObject?.nameFull?.get(1))
                            .append(" ")
                            .append(detailsCoins.high24h)
                    },
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
                        text = buildString {
                            append(currencyObject?.nameFull?.get(1))
                                .append(" ")
                                .append(String.format("%,.2f", detailsCoins.marketCap))

                        },
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
                                    detailsCoins.marketCapChangePercentage24h
                                )
                            )
                            append(" %")

                            appendInlineContent("inlineContent", "[icon]")
                        },
                        fontSize = 20.sp,
                        inlineContent = addIcon(detailsCoins.marketCapChangePercentage24h)
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
                    Modifier.size(ButtonDefaults.LargeButtonSize),
                    Icons.Rounded.Delete
                ) {
                    viewModel?.removeCoinFromFavoriteCoins(coinId = coinId)
                    navController?.popBackStack()
                    Toast.makeText(context, context.getString(R.string.txt_toast_removed,
                        detailsCoins.name), Toast.LENGTH_SHORT).show()
                }
                IconButton(
                    Modifier.size(ButtonDefaults.LargeButtonSize),
                    Icons.Rounded.Star
                ) {
                    sharedPreference.FavoriteCoin = coinId
                    Toast.makeText(context, context.getString(R.string.txt_toast_set_tile,
                        detailsCoins.name), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}

private fun addIcon(value: Double?): Map<String, InlineTextContent> {
    val myId = "inlineContent"

    val inlineContent = mapOf(
        Pair(
            myId,
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