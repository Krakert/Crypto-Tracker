package com.krakert.tracker.ui.tracker.settings

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.RestartAlt
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.krakert.tracker.BuildConfig
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.MinutesCache
import com.krakert.tracker.SharedPreference.sharedPreference
import com.krakert.tracker.models.*
import com.krakert.tracker.models.ui.Currency
import com.krakert.tracker.ui.shared.CenterElement
import com.krakert.tracker.ui.shared.Divider
import com.krakert.tracker.ui.shared.IconButton
import com.krakert.tracker.ui.viewmodel.SettingsViewModel

@Composable
fun TrackerSettingsScreen(viewModel: SettingsViewModel?) {
    val context = LocalContext.current
    val sharedPreference = sharedPreference(context = context)
    var amountDaysTracking by remember { mutableStateOf(sharedPreference.AmountDaysTracking) }
    var minutesCache by remember { mutableStateOf(sharedPreference.MinutesCache)}
    val scrollState = rememberScalingLazyListState()
    var checked by remember { mutableStateOf(sharedPreference.Currency) }
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
                        viewModel?.setAmountDaysTracking(amountDaysTracking)
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
                            viewModel?.setCacheRate(minutesCache)
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
        items(enumValues<Currency>()){ index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                ToggleChip(
                    modifier = Modifier.fillMaxWidth(),
                    checked = checked == index.name,
                    toggleControl = {
                        Icon(
                            imageVector = ToggleChipDefaults.radioIcon(checked == index.name),
                            contentDescription =  if (checked == index.name) "On" else "Off"
                        )
                    },
                    onCheckedChange = {
                        checked = index.name
                        viewModel?.setCurrency(index.name)
                        sharedPreference.Currency = checked
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
                    viewModel?.resetSettings()
                    Toast.makeText(context, context.getString(R.string.txt_toast_reset), Toast.LENGTH_SHORT).show()
                }
            }
        }
        item {
            Text(text = buildString {
                append("Version name: ")
                    .append(BuildConfig.VERSION_NAME)
                    .append(".")
                    .append(BuildConfig.BUILD_TYPE)
                    .append("\n Build date: ")
                    .append(BuildConfig.BUILD_TIME)
                    .append("\n Version ID: ")
                    .append(BuildConfig.VERSION_CODE)
            },
                textAlign = TextAlign.Center,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview (
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun PreviewSettings(){
    ListSettings(null)
}