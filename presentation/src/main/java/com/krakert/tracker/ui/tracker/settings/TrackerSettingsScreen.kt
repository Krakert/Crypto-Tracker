package com.krakert.tracker.ui.tracker.settings

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.wear.compose.material.AutoCenteringParams
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.InlineSlider
import androidx.wear.compose.material.InlineSliderDefaults
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.ToggleChip
import androidx.wear.compose.material.ToggleChipDefaults
import androidx.wear.compose.material.items
import androidx.wear.compose.material.rememberScalingLazyListState
import com.krakert.tracker.R
import com.krakert.tracker.ui.components.CenterElement
import com.krakert.tracker.ui.components.Divider
import com.krakert.tracker.ui.components.IconButton
import com.krakert.tracker.ui.components.Loading
import com.krakert.tracker.ui.components.ShowProblem
import com.krakert.tracker.ui.tracker.model.Currency
import com.krakert.tracker.ui.tracker.settings.ViewStateSettings.Loading
import com.krakert.tracker.ui.tracker.settings.ViewStateSettings.Problem
import com.krakert.tracker.ui.tracker.settings.ViewStateSettings.Success


@Composable
fun TrackerSettingsScreen(
    viewModel: TrackerSettingsViewModel,
    lifeCycleOwner: LifecycleOwner = LocalLifecycleOwner.current
) {


    DisposableEffect(lifeCycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getSettings()
            }
        }
        lifeCycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifeCycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val context = LocalContext.current

    val scrollState = rememberScalingLazyListState()

    when (val response = viewModel.settingViewState.collectAsState().value) {
        is Loading -> Loading()
        is Problem -> {
            ShowProblem(response.exception) {
//                when (response.exception) {
//                    ProblemState.SSL -> viewModel.openSettings()
//                    ProblemState.EMPTY -> navController.navigate(Screen.Add.route)
//                    else -> viewModel.getAllOverviewData()
//                }
            }
        }

        is Success -> {

            var amountDaysTracking by remember { mutableStateOf(response.data.daysOfTracking) }
            var minutesCache by remember { mutableStateOf(response.data.minutesOfCache) }
            var checked by remember { mutableStateOf(response.data.currency) }

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
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp,
                                top = 16.dp
                            ),
                            text = stringResource(R.string.txt_settings_chart_length),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        InlineSlider(
                            value = amountDaysTracking,
                            onValueChange = {
                                amountDaysTracking = if (it == 0) {
                                    1
                                } else {
                                    it
                                }
                                viewModel.setAmountOfDays(amountDaysTracking)
                            },
                            valueProgression = 0..14,
                            increaseIcon = { Icon(InlineSliderDefaults.Increase, "Increase") },
                            decreaseIcon = { Icon(InlineSliderDefaults.Decrease, "Decrease") },
                        )
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp
                            ),
                            text = "Day(s): $amountDaysTracking",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                item { Divider() }
                item {
                    CenterElement {
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp,
                                top = 8.dp
                            ),
                            text = stringResource(R.string.txt_settings_minutes_cache),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        InlineSlider(
                            value = minutesCache,
                            onValueChange = {
                                minutesCache = if (it == 1) {
                                    2
                                } else {
                                    it
                                }
                                viewModel.setCacheRate(minutesCache)
                            },
                            valueProgression = 1..10,
                            increaseIcon = { Icon(InlineSliderDefaults.Increase, "Increase") },
                            decreaseIcon = { Icon(InlineSliderDefaults.Decrease, "Decrease") },
                        )
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp
                            ),
                            text = "Minutes: $minutesCache",
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                item { Divider() }
                item {
                    CenterElement {
                        Text(
                            modifier = Modifier.padding(
                                start = 8.dp,
                                end = 8.dp,
                                bottom = 8.dp
                            ),
                            text = stringResource(R.string.txt_settings_set_currency),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                items(enumValues<Currency>()) { index ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        ToggleChip(
                            modifier = Modifier.fillMaxWidth(),
                            checked = checked == index,
                            toggleControl = {
                                Icon(
                                    imageVector = ToggleChipDefaults.radioIcon(checked == index),
                                    contentDescription = if (checked == index) "On" else "Off"
                                )
                            },
                            onCheckedChange = {
                                checked = index
                                viewModel.setCurrency(index)
//                                sharedPreference.Currency = checked
                            },
                            label = {
                                Text(
                                    text = index.name.uppercase(),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )

                    }
                }
                item { Divider() }
                item {
                    CenterElement {
                        Text(
                            text = stringResource(R.string.txt_settings_reset),
                            modifier = Modifier.padding(bottom = 8.dp, top = 8.dp),
                            textAlign = TextAlign.Center,
                        )
                        IconButton(
                            Modifier.size(ButtonDefaults.LargeButtonSize),
                            Icons.Rounded.RestartAlt
                        ) {
                            viewModel.resetSettings()
                            Toast.makeText(
                                context,
                                context.getString(R.string.txt_toast_reset),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
//                item {
//                    Text(
//                        text = buildString {
//                            append("Version name: ")
//                                .append(BuildConfig.VERSION_NAME)
//                                .append(".")
//                                .append(BuildConfig.BUILD_TYPE)
//                                .append("\n Build date: ")
//                                .append(BuildConfig.BUILD_TIME)
//                                .append("\n Version ID: ")
//                                .append(BuildConfig.VERSION_CODE)
//                        },
//                        textAlign = TextAlign.Center,
//                        fontSize = 10.sp,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
            }
        }

    }


}

//@Preview (
//    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
//    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
//    uiMode = WEAR_PREVIEW_UI_MODE,
//    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
//    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
//)
//@Composable
//fun PreviewSettings(){
//    ListSettings(null)
//}