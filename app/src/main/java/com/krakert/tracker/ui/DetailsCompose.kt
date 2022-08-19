package com.krakert.tracker.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.models.Currency
import com.krakert.tracker.ui.state.ViewStateDetailsCoins
import com.krakert.tracker.ui.tile.theme.themeValues
import com.krakert.tracker.ui.viewmodel.DetailsViewModel

@Composable
fun ShowDetails(coinId: String, viewModel: DetailsViewModel, navController: NavHostController) {

    when (val detailsCoins = viewModel.detailsCoin.collectAsState().value) {
        ViewStateDetailsCoins.Loading -> Loading()
        is ViewStateDetailsCoins.Error, is ViewStateDetailsCoins.Empty -> ShowIncorrectState(
            textIncorrectState = R.string.txt_toast_error,
            viewModel = viewModel,
            coinId = coinId
        )
        is ViewStateDetailsCoins.Success -> ShowDetailsCoins(detailsCoins, viewModel, coinId, navController)
    }
}

@Composable
fun ShowDetailsCoins(
    detailsCoins: ViewStateDetailsCoins.Success,
    viewModel: DetailsViewModel,
    coinId: String,
    navController: NavHostController
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
//            CenterElement {
//                CoilImage(
//                    imageModel = detailsCoins.details.image.thumb,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .wrapContentSize(align = Alignment.Center),
//
//                    )
//                Text(
//                    text = detailsCoins.details.name,
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 24.sp
//                )
//            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(R.string.txt_price_change_percentage_24h),
                    fontSize = 20.sp,
                    color = themeValues[0].colors.secondary
                )
                Text(
                    text = stringResource(R.string.txt_price_change_percentage_7d),
                    fontSize = 20.sp,
                    color = themeValues[0].colors.secondary
                )
            }
        }
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = buildAnnotatedString {
//                        append(String.format("%4.2f", detailsCoins.details.priceChangePercentage24h))
                        appendInlineContent("inlineContent", "[icon]")
                    },
                    fontSize = 20.sp,
//                    inlineContent = addIcon(detailsCoins.details.priceChangePercentage24h)
                )

                Text(
                    text = buildAnnotatedString {
//                        append(String.format("%4.2f", detailsCoins.details.priceChangePercentage7d))
                        appendInlineContent("inlineContent", "[icon]")
                    },
                    fontSize = 20.sp,
//                    inlineContent = addIcon(detailsCoins.details.priceChangePercentage7d)
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
//                                .append(detailsCoins.details.priceCurrent)
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
//                    Text(
//                        text = String.format("%,.0f", detailsCoins.details.circulatingSupply),
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                    )
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
//                            .append(detailsCoins.details.high24h.toString())
                    },
                    fontSize = 16.sp,
                )
                Text(
                    text = buildString {
                        append(currencyObject?.nameFull?.get(1))
                            .append(" ")
//                            .append(detailsCoins.details.low24h.toString())
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
//                                .append(String.format("%,.2f", detailsCoins.details.marketCap))

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
//                            append(
//                                String.format(
//                                    "%4.2f",
//                                    detailsCoins.details.marketCapChangePercentage24h
//                                )
//                            )
                            appendInlineContent("inlineContent", "[icon]")
                        },
                        fontSize = 20.sp,
//                        inlineContent = addIcon(detailsCoins.details.priceChangePercentage24h)
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
//                IconButton(
//                    Modifier.size(ButtonDefaults.LargeButtonSize),
//                    Icons.Rounded.Delete
//                ) {
//                    if (coin != null) {
//                        viewModel.removeCoinFromFavoriteCoins(coin = coin)
//                        navController.navigate(Screen.Overview.route)
//                        Toast.makeText(context, context.getString(R.string.txt_toast_removed, coin.name), Toast.LENGTH_SHORT).show()
//                    }
//                }
//                IconButton(
//                    Modifier.size(ButtonDefaults.LargeButtonSize),
//                    Icons.Rounded.Star
//                ) {
//                    if (coin != null) {
////                        sharedPreference.FavoriteCoin = coin.idCoin
//                        Toast.makeText(context, context.getString(R.string.txt_toast_set_tile, coin.name), Toast.LENGTH_SHORT).show()
//                    }
//                }
            }
        }
    }
}

@Composable
fun Divider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        CenterElement {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Gray)
            )
        }
    }
}


@Composable
private fun ShowIncorrectState(
    @StringRes textIncorrectState: Int,
    viewModel: DetailsViewModel,
    coinId: String
) {
    val context = LocalContext.current
    CenterElement {
        IconButton(
            Modifier
                .size(ButtonDefaults.LargeButtonSize)
                .padding(top = 8.dp), Icons.Rounded.Cached
        ) {
            viewModel.getDetailsCoinByCoinId(coinId)
            Toast.makeText(context, textIncorrectState, Toast.LENGTH_SHORT).show()
        }
        Text(
            text = stringResource(textIncorrectState),
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center,
        )
    }
}

private fun addIcon(value: Double): Map<String, InlineTextContent> {
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
                if (value > 0.0) {
                    Icon(Icons.Filled.ArrowDropUp, "", tint = MaterialTheme.colors.primaryVariant)
                } else {
                    Icon(Icons.Filled.ArrowDropDown, "", tint = MaterialTheme.colors.secondaryVariant)
                }
            }
        )
    )
    return inlineContent
}