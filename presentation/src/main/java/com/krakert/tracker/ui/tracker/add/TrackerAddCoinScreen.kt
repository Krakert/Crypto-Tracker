package com.krakert.tracker.ui.tracker.add

import androidx.compose.runtime.Composable
import androidx.wear.compose.material.Text

@Composable
fun TrackerAddCoinScreen(viewModel: AddCoinViewModel) {
    Text(text = "TrackerAddCoinScreen")
//
//    LaunchedEffect(Unit) {
//        viewModel.getListCoins()
//    }
//
//    val scrollState = rememberScalingLazyListState()
//    Scaffold(
//        timeText = {
//            if (!scrollState.isScrollInProgress) {
//                TimeText()
//            }
//        },
//        vignette = {
//            Vignette(vignettePosition = VignettePosition.TopAndBottom)
//        },
//        positionIndicator = {
//            PositionIndicator(
//                scalingLazyListState = scrollState
//            )
//        }
//    ) {
//        when (val result = viewModel.listCoins.collectAsState().value) {
//            is ViewStateAddCoin.Problem -> {
//                ShowProblem(result.exception) {
//                    when (result.exception) {
//                        ProblemState.SSL -> viewModel.openSettings()
//                        else -> viewModel.getListCoins()
//                    }
//                }
//            }
//            ViewStateAddCoin.Loading -> {
//                Loading()
//            }
//            is ViewStateAddCoin.Success -> {
//                ShowList(
//                    scrollState = scrollState,
//                    listCoins = result.coins,
//                    viewModel = viewModel
//                )
//            }
//        }
//
//    }
}

//@Composable
//private fun ShowList(
//    scrollState: ScalingLazyListState,
//    listCoins: List<Coin>,
//    viewModel: AddCoinViewModel,
//) {
//    val context = LocalContext.current
//
//    ScalingLazyColumn(
//        modifier = Modifier
//            .fillMaxSize(),
//        contentPadding = PaddingValues(
//            top = 32.dp,
//            start = 8.dp,
//            end = 8.dp,
//            bottom = 32.dp
//        ),
//        verticalArrangement = Arrangement.spacedBy(8.dp),
//        autoCentering = AutoCenteringParams(itemIndex = 0),
//        state = scrollState
//    ) {
//        items(listCoins) { coin ->
//            ChipCoin(coin = coin) {
//                coin.isFavorite = !coin.isFavorite
//                viewModel.toggleFavoriteCoin(coin, context = context)
//            }
//        }
//    }
//}
//
//@Composable
//private fun ChipCoin(coin: Coin, onClick: () -> Unit) {
//    var palette by remember { mutableStateOf<Palette?>(null) }
//    var isFavorite by remember { mutableStateOf(coin.isFavorite) }
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(52.dp)
//            .clip(shape = RoundedCornerShape(50))
//            .background(
//                brush = Brush.horizontalGradient(
//                    colors = listOf(
//                        Color(palette?.lightVibrantSwatch?.rgb ?: 0).copy(alpha = 0.5f),
//                        MaterialTheme.colors.surface
//                    )
//                )
//            )
//            .padding(
//                horizontal = 8.dp,
//                vertical = 4.dp
//            )
//            .clickable {
//                isFavorite = !isFavorite
//                onClick()
//            },
//        contentAlignment = Alignment.Center
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxSize(),
//            verticalAlignment = Alignment.CenterVertically,
//        ) {
//            CoilImage(
//                imageModel = { coin.image },
//                imageOptions = ImageOptions(
//                    contentScale = ContentScale.Fit
//                ),
//                modifier = Modifier
//                    .size(32.dp)
//                    .wrapContentSize(align = Alignment.Center),
//                component = rememberImageComponent {
//                    +PalettePlugin { palette = it }
//                }
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Text(
//                modifier = Modifier.weight(1f, true),
//                text = coin.name,
//                maxLines = 1,
//                overflow = TextOverflow.Ellipsis
//            )
//            Spacer(modifier = Modifier.width(8.dp))
//            Icon(
//                modifier = Modifier.size(16.dp),
//                imageVector =
//                if (isFavorite) {
//                    Icons.Default.Favorite
//                } else {
//                    Icons.Default.FavoriteBorder
//                }, contentDescription = null
//            )
//        }
//    }
//
//}
//
//@Preview(widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP)
//@Composable
//fun PreviewChipCoin() {
//    ChipCoin(coin = Coin("bitcoin", "", "Text that should be to long", true)) {}
//}
