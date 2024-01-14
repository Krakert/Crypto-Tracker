package com.krakert.tracker.ui.tracker.detail.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.components.CenterElement
import com.krakert.tracker.ui.components.Divider
import com.krakert.tracker.ui.theme.themeValues

@Composable
fun DetailRowMinMax24H(low24h: String, high24h: String) {
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

        Text(
            text = low24h,
            fontSize = 16.sp,
        )
        Text(
            text = high24h,
            fontSize = 16.sp,
        )
    }
    Divider()
}