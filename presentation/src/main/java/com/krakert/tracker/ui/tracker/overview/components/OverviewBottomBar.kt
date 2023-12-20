package com.krakert.tracker.ui.tracker.overview.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.components.IconButton
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_API_LEVEL
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_BACKGROUND_COLOR_BLACK
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_HEIGHT_DP
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_WIDTH_DP
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_SHOW_BACKGROUND
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_UI_MODE


@Preview(
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    apiLevel = WEAR_PREVIEW_API_LEVEL,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun PreviewOverviewBottomBar() {
    OverviewBottomBar(openListCoins = {}, openSettings = {})
}

@Composable
fun OverviewBottomBar(openSettings: () -> Unit, openListCoins: () -> Unit) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
                text = stringResource(R.string.txt_add_more_change_settings),
                fontSize = 16.sp,
                textAlign = TextAlign.Center

            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
            IconButton(
                modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
                imageVector = Icons.Rounded.PlusOne,
                contentDescription = stringResource(
                    R.string.button_with_icon,
                    Icons.Rounded.PlusOne.name
                ),
                onClick = { openListCoins() }
            )
            IconButton(
                modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
                imageVector = Icons.Rounded.Settings,
                contentDescription = stringResource(
                    R.string.button_with_icon,
                    Icons.Rounded.Settings.name
                ),
                onClick = { openSettings() }
            )
        }
    }

}