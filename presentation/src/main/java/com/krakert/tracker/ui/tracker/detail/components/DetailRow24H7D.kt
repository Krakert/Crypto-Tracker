package com.krakert.tracker.ui.tracker.detail.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.components.Divider
import com.krakert.tracker.ui.theme.themeValues
import com.krakert.tracker.ui.tracker.detail.addIcon


@Composable
fun DetailRow24H7D(priceChangePercentage24h: Double, priceChangePercentage7d: Double) {
    Row(
        modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly
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
                    append(
                        String.format(
                            "%4.2f", priceChangePercentage24h
                        )
                    )
                    appendInlineContent("inlineContent", "[icon]")
                }, fontSize = 20.sp, inlineContent = addIcon(priceChangePercentage24h)
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
                    append(
                        String.format(
                            "%4.2f", priceChangePercentage7d
                        )
                    )
                    appendInlineContent("inlineContent", "[icon]")
                }, fontSize = 20.sp, inlineContent = addIcon(priceChangePercentage7d)
            )
        }
    }
    Divider()
}