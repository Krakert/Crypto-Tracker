package com.krakert.tracker.ui.tracker.add

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import androidx.wear.compose.material.*
import androidx.wear.compose.material.ChipDefaults.gradientBackgroundChipColors
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.models.responses.Coin
import com.krakert.tracker.models.ui.ProblemState
import com.krakert.tracker.ui.shared.Loading
import com.krakert.tracker.ui.shared.ShowProblem
import com.krakert.tracker.ui.viewmodel.AddCoinViewModel
import com.krakert.tracker.ui.viewmodel.ViewStateAddCoin
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin
import java.lang.reflect.Type

@Composable
fun TrackerAddCoinScreen(viewModel: AddCoinViewModel) {

    LaunchedEffect(Unit) {
        viewModel.getListCoins()
    }

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
        when (val result = viewModel.listCoins.collectAsState().value) {
            is ViewStateAddCoin.Problem -> {
                ShowProblem(result.exception){
                    when (result.exception) {
                        ProblemState.SSL -> viewModel.openSettings()
                        else -> viewModel.getListCoins()
                    }
                }
            }
            ViewStateAddCoin.Loading -> {
                Loading()
            }
            is ViewStateAddCoin.Success -> {
                ShowList(scrollState = scrollState,
                    listResult = result.coins,
                    viewModel = viewModel)
            }
        }

    }
}

@Composable
private fun ShowList(
    scrollState: ScalingLazyListState,
    listResult: List<Coin>?,
    viewModel: AddCoinViewModel,
) {
    val context = LocalContext.current
    var listFavoriteCoins = ArrayList<Coin>()
    val sharedPreference = SharedPreference.sharedPreference(context)
    val dataSharedPreferences = sharedPreference.FavoriteCoins.toString()
    val typeOfT: Type = object : TypeToken<ArrayList<Coin>>() {}.type

    if (dataSharedPreferences.isNotEmpty()) {
        listFavoriteCoins = Gson().fromJson(dataSharedPreferences, typeOfT)
    }

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
        listResult?.size?.let {
            items(it) { index ->
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    AddChipCoin(listResult[index]) {
                        viewModel.addCoinToFavoriteCoins(listResult[index], context = context)
                    }
                }
            }
        }
    }
}

@Composable
private fun AddChipCoin(coin: Coin, onClick: () -> Unit) {
    var palette by remember { mutableStateOf<Palette?>(null) }

    Chip(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        colors = gradientBackgroundChipColors(
            startBackgroundColor = Color(palette?.lightVibrantSwatch?.rgb ?: 0).copy(alpha = 0.5f),
            endBackgroundColor = MaterialTheme.colors.surface,
            gradientDirection = LayoutDirection.Ltr
        ),
        icon = {
            CoilImage(
                imageModel = { coin.image },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit),
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
                component = rememberImageComponent {
                    +PalettePlugin { palette = it }
                }
            )
        },
        onClick = { onClick() },
        label = {
            Text(
                text = coin.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
    )
}


//@Composable
//private fun LoadImage(url: String, palette: Palette) {
//
//}

//@Preview(
//    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
//    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
//    apiLevel = WEAR_PREVIEW_API_LEVEL,
//    uiMode = WEAR_PREVIEW_UI_MODE,
//    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
//    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
//)
//@Composable
//fun ShowIncorrectStatePreview() {
////        ShowIncorrectState(text = R.string.txt_toast_error, viewModel = AddCoinViewModel())
//    CenterElement {
//        IconButton(Modifier.size(ButtonDefaults.LargeButtonSize), Icons.Rounded.Cached){}
//        Text(
//            text = stringResource(R.string.txt_toast_error),
//            modifier = Modifier.padding(top = 8.dp),
//            textAlign = TextAlign.Center,
//            color = MaterialTheme.colors.primary,
//        )
//    }
//}
