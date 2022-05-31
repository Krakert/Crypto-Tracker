package com.krakert.tracker.ui

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.*
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.sharedPreference
import com.krakert.tracker.model.Currency

@Composable
fun ListSettings() {
    val context = LocalContext
    val sharedPreference = sharedPreference(context = context.current)
    var amountDaysTracking by remember { mutableStateOf(sharedPreference.AmountDaysTracking.toInt()) }
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
        // For each index in my favorites
        item {
            CenterElement {
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                        top = 16.dp
                    ),
                    text = "Chart time span in days",
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
                        sharedPreference.AmountDaysTracking = amountDaysTracking.toFloat()
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
        item {
            CenterElement {
                Text(
                    modifier = Modifier.padding(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp
                    ),
                    text = "Select your currency",
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
                            imageVector = ToggleChipDefaults.switchIcon(checked == index.name),
                            contentDescription =  if (checked == index.name) "On" else "Off"
                        )
                    },
                    onCheckedChange = {
                        checked = index.name
                        println(checked)
                        sharedPreference.Currency = checked
                    },
                    label = {
                        Text(
                            text = index.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )

            }
        }
    }

}