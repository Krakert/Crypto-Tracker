package com.krakert.tracker.ui

import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.wear.compose.material.*
import com.krakert.tracker.R
import com.krakert.tracker.model.Coin
import com.krakert.tracker.state.ViewStateAddCoin
import com.krakert.tracker.viewmodel.AddCoinViewModel

@Composable
fun ListAddCoin(viewModel: AddCoinViewModel, navController: NavHostController) {

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
                ShowList(scrollState = scrollState, listResult = listResult.coins.Coins, viewModel = viewModel)
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
                    Chip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        onClick = {
                            viewModel.addCoinToFavoriteCoins(listResult[index], context)
                        },
                        label = {
                            Text(
                                text = listResult[index].name.toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = "triggers meditation action",
                                modifier = Modifier
                                    .size(24.dp)
                                    .wrapContentSize(align = Alignment.Center)
                            )
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun Loading() {
    CenterElement {
        CircularProgressIndicator(modifier = Modifier.size(75.dp))
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