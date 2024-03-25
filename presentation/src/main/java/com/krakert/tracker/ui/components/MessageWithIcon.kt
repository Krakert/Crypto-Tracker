package com.krakert.tracker.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.tracker.model.MessageWithIcon
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.*
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_API_LEVEL
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_BACKGROUND_COLOR_BLACK
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_HEIGHT_DP
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_WIDTH_DP
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_SHOW_BACKGROUND
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_UI_MODE

@Composable
fun ShowMessageWithIcon(content: MessageWithIcon, onClick: () -> Unit) {
    CenterElement {
        IconButton(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            imageVector = content.icon,
            contentDescription = stringResource(
                R.string.button_with_icon,
                content.icon.name
            ),
            onClick = { onClick() }
        )
        Text(
            modifier = Modifier.padding(all = 8.dp),
            text = stringResource(content.txt),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    }
}

class ProblemsPreviewParameterProvider : PreviewParameterProvider<MessageWithIcon> {
    override val values = sequenceOf(
        UNKNOWN,
        SSL,
        EMPTY,
        API_LIMIT,
        COULD_NOT_LOAD,
        NO_CONNECTION,
        NO_RESULT
    )
}

@Preview(
    group = "State",
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    apiLevel = WEAR_PREVIEW_API_LEVEL,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun PreviewShowMessageWithIcon(
    @PreviewParameter(ProblemsPreviewParameterProvider::class) problem: MessageWithIcon
) {
    ShowMessageWithIcon(problem) { }
}