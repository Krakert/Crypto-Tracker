package com.krakert.tracker.ui.tracker.detail.components

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.krakert.tracker.ui.components.CenterElement
import com.krakert.tracker.ui.components.Divider
import com.krakert.tracker.ui.theme.themeValues

@Composable
fun DetailSingleItem(
    @StringRes title: Int, text: String, fontSize: TextUnit, color: Color = Color.Unspecified
) {
    CenterElement {
        Text(
            text = stringResource(title),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = themeValues[0].colors.secondary
        )
        Text(
            text = text,
            textAlign = TextAlign.Center,
            fontSize = fontSize,
            overflow = TextOverflow.Ellipsis,
            color = color
        )
        Divider()
    }
}
