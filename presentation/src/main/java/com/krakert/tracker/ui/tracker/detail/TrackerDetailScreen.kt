package com.krakert.tracker.ui.tracker.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController

//
//@Preview(
//    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
//    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
//    uiMode = WEAR_PREVIEW_UI_MODE,
//    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
//    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
//)
//@Composable
//fun PreviewShowDetails() {
//    ShowDetailsCoin(detailsCoins =
//    DetailsCoin(
//        name = "Bitcoin",
//        image = null,
//        currentPrice = "16.043,86",
//        priceChangePercentage24h = -2.12,
//        priceChangePercentage7d = -2.1,
//        circulatingSupply = 19214981.0,
//        high24h = 16609.39,
//        low24h = 15738.18,
//        marketCap = 317737907422.0,
//        marketCapChangePercentage24h = 2.1,
//    ), coinId = "bitcoin", navController = null, viewModel = null)
//}

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
//                viewModel.coinId = coinId
//                viewModel.getDetailsCoinByCoinId()
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

//    when (val response = viewModel.detailsCoin.collectAsState().value) {
//        is Loading -> Loading()
//        is Problem -> {
//            ShowProblem(response.exception) {
//                when (response.exception) {
//                    ProblemState.SSL -> viewModel.openSettings()
//                    else -> viewModel.getDetailsCoinByCoinId()
//                }
//            }
//        }
//        is Success -> ShowDetailsCoin(
//            detailsCoins = response.details,
//            viewModel = viewModel,
//            coinId = coinId,
//            navController = navController)
//    }
}

//@Composable
//fun ShowDetailsCoin(
//    detailsCoins: DetailsCoin,
//    viewModel: DetailsViewModel?,
//    coinId: String,
//    navController: NavHostController?,
//) {
//
//    val context = LocalContext.current
//    val sharedPreference = SharedPreference.sharedPreference(context = context)
//    val currencyObject = sharedPreference.Currency?.let { Currency.valueOf(it) }
//
//    val scrollState = rememberScalingLazyListState()
//    ScalingLazyColumn(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentPadding = PaddingValues(
//            top = 8.dp,
//            start = 8.dp,
//            end = 8.dp,
//            bottom = 32.dp
//        ),
//        verticalArrangement = Arrangement.Center,
//        autoCentering = AutoCenteringParams(itemIndex = 0),
//        state = scrollState
//    ) {
//        item {
//            CenterElement {
//                CoilImage(
//                    imageModel = { detailsCoins.image?.large },
//                    imageOptions = ImageOptions(
//                        contentScale = ContentScale.Fit),
//                    modifier = Modifier
//                        .size(40.dp)
//                        .wrapContentSize(align = Alignment.Center),
//                )
//                Text(
//                    text = detailsCoins.name,
//                    textAlign = TextAlign.Center,
//                    color = MaterialTheme.colors.primary,
//                    fontSize = 24.sp
//                )
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Column(
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = stringResource(R.string.txt_price_change_percentage_24h),
//                        fontSize = 20.sp,
//                        color = themeValues[0].colors.secondary
//                    )
//                    Text(
//                        text = buildAnnotatedString {
//                            append(String.format("%4.2f", detailsCoins.priceChangePercentage24h))
//                            appendInlineContent("inlineContent", "[icon]")
//                        },
//                        fontSize = 20.sp,
//                        inlineContent = addIcon(detailsCoins.priceChangePercentage24h)
//                    )
//                }
//                Column(
//                            horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = stringResource(R.string.txt_price_change_percentage_7d),
//                        fontSize = 20.sp,
//                        color = themeValues[0].colors.secondary
//                    )
//                    Text(
//                        text = buildAnnotatedString {
//                            append(String.format("%4.2f", detailsCoins.priceChangePercentage7d))
//                            appendInlineContent("inlineContent", "[icon]")
//                        },
//                        fontSize = 20.sp,
//                        inlineContent = addIcon(detailsCoins.priceChangePercentage7d)
//                    )
//                }
//            }
//        }
//        item { Divider() }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = stringResource(R.string.txt_latest_price),
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                        color = themeValues[0].colors.secondary
//                    )
//                }
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = buildString {
//                            append(currencyObject?.nameFull?.get(1))
//                                .append(" ")
//                                .append(detailsCoins.currentPrice)
//                        },
//                        textAlign = TextAlign.Center,
//                        fontSize = 28.sp,
//                        overflow = TextOverflow.Ellipsis,
//                        color = MaterialTheme.colors.primary
//                    )
//                }
//            }
//        }
//        item { Divider() }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = stringResource(R.string.txt_details_circulation),
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                        color = themeValues[0].colors.secondary
//                    )
//                }
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = String.format("%,.0f", detailsCoins.circulatingSupply),
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                    )
//                }
//            }
//        }
//        item { Divider() }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = stringResource(R.string.txt_details_max_min),
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                        color = themeValues[0].colors.secondary
//                    )
//                }
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.SpaceEvenly
//            ) {
//                Text(
//                    text = buildString {
//                        append(currencyObject?.nameFull?.get(1))
//                            .append(" ")
//                            .append(detailsCoins.low24h)
//                    },
//                    fontSize = 16.sp,
//                )
//                Text(
//                    text = buildString {
//                        append(currencyObject?.nameFull?.get(1))
//                            .append(" ")
//                            .append(detailsCoins.high24h)
//                    },
//                    fontSize = 16.sp,
//                )
//            }
//        }
//        item { Divider() }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = stringResource(R.string.txt_details_market_cap),
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                        color = themeValues[0].colors.secondary
//                    )
//                }
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = buildString {
//                            append(currencyObject?.nameFull?.get(1))
//                                .append(" ")
//                                .append(String.format("%,.2f", detailsCoins.marketCap))
//
//                        },
//                        textAlign = TextAlign.Center,
//                        fontSize = 18.sp,
//                    )
//                }
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//            ) {
//                CenterElement {
//                    Text(
//                        text = buildAnnotatedString {
//                            append(
//                                String.format(
//                                    "%4.2f",
//                                    detailsCoins.marketCapChangePercentage24h
//                                )
//                            )
//                            append(" %")
//
//                            appendInlineContent("inlineContent", "[icon]")
//                        },
//                        fontSize = 20.sp,
//                        inlineContent = addIcon(detailsCoins.marketCapChangePercentage24h)
//                    )
//                }
//            }
//        }
//        item { Divider() }
//        item {
//            CenterElement {
//                Text(
//                    text = stringResource(R.string.txt_details_remove_set),
//                    modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
//                    textAlign = TextAlign.Center
//                )
//            }
//        }
//        item {
//            Row(
//                modifier = Modifier.fillMaxSize(),
//                horizontalArrangement = Arrangement.SpaceEvenly,
//            ) {
//                IconButton(
//                    Modifier.size(ButtonDefaults.LargeButtonSize),
//                    Icons.Rounded.Delete
//                ) {
//                    viewModel?.removeCoinFromFavoriteCoins(coinId = coinId)
//                    navController?.popBackStack()
//                    Toast.makeText(context, context.getString(R.string.txt_toast_removed,
//                        detailsCoins.name), Toast.LENGTH_SHORT).show()
//                }
//                IconButton(
//                    Modifier.size(ButtonDefaults.LargeButtonSize),
//                    Icons.Rounded.Star
//                ) {
//                    sharedPreference.FavoriteCoin = coinId
//                    Toast.makeText(context, context.getString(R.string.txt_toast_set_tile,
//                        detailsCoins.name), Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//    }
//}
//
//private fun addIcon(value: Double?): Map<String, InlineTextContent> {
//    val myId = "inlineContent"
//
//    val inlineContent = mapOf(
//        Pair(
//            myId,
//            InlineTextContent(
//                Placeholder(
//                    width = 24.sp,
//                    height = 24.sp,
//                    placeholderVerticalAlign = PlaceholderVerticalAlign.Center
//                )
//            ) {
//                if (value != null) {
//                    if (value > 0.0) {
//                        Icon(Icons.Filled.ArrowDropUp,
//                            "",
//                            tint = MaterialTheme.colors.primaryVariant)
//                    } else {
//                        Icon(Icons.Filled.ArrowDropDown,
//                            "",
//                            tint = MaterialTheme.colors.secondaryVariant)
//                    }
//                } else {
//                    Icon(Icons.Filled.Remove, "", tint = MaterialTheme.colors.error)
//                }
//            }
//        )
//    )
//    return inlineContent
//}