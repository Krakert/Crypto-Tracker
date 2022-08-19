package com.krakert.tracker.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.palette.graphics.Palette
import androidx.wear.compose.material.*
import androidx.wear.compose.material.ChipDefaults.gradientBackgroundChipColors
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.models.Coin
import com.krakert.tracker.ui.state.ViewStateAddCoin
import com.krakert.tracker.ui.viewmodel.AddCoinViewModel
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.palette.BitmapPalette
import java.lang.reflect.Type

@Composable
fun ListAddCoin( viewModel: AddCoinViewModel = viewModel()) {

    LaunchedEffect(Unit){
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
        when (val listResult = viewModel.listCoins.collectAsState().value) {
            ViewStateAddCoin.Empty -> {
                ShowIncorrectState(R.string.txt_toast_reload, viewModel)
            }
            is ViewStateAddCoin.Error -> {
                ShowIncorrectState(R.string.txt_toast_error, viewModel)
            }
            ViewStateAddCoin.Loading -> {
                Loading()
            }
            is ViewStateAddCoin.Success -> {
                ShowList(scrollState = scrollState, listResult = listResult.coins.data, viewModel = viewModel)
            }
        }

    }
}

@Composable
private fun ShowIncorrectState(@StringRes text: Int, viewModel: AddCoinViewModel) {
    val context = LocalContext.current
    CenterElement {
        IconButton(Modifier.size(ButtonDefaults.LargeButtonSize), Icons.Rounded.Cached) {
            viewModel.getListCoins()
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
        }
        Text(
            text = stringResource(text),
            modifier = Modifier.padding(top = 8.dp),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
        )
    }
}

@Composable
private fun ShowList(scrollState: ScalingLazyListState, listResult: List<Coin>?, viewModel: AddCoinViewModel) {
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
                    AddChipCoin(listResult[index]){
                        viewModel.addCoinToFavoriteCoins(listResult[index], context = context)
                    }
                }
            }
        }
    }
}

@Composable
fun AddChipCoin(coin: Coin, onClick: () -> Unit) {
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
            LoadImage(url = coin.getIcon(), onPaletteAvailable = { palette = it })
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


@Composable
fun LoadImage(url: String, onPaletteAvailable: (Palette) -> Unit){
    CoilImage(
        imageModel = url,
        contentScale = ContentScale.Fit,
        modifier = Modifier
            .size(24.dp)
            .wrapContentSize(align = Alignment.Center),
        bitmapPalette = BitmapPalette {
            onPaletteAvailable(it)
        }
    )
}

@Composable
fun Loading() {
    CenterElement {
        CircularProgressIndicator(modifier = Modifier.size(55.dp))
    }
}

@Composable
fun CenterElement(content: @Composable () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        content()
    }
}

@Composable
fun IconButton(modifier: Modifier, imageVector: ImageVector, onClick: () -> Unit) {
    // Button
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}
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
