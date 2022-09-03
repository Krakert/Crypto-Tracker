package com.krakert.tracker.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import com.krakert.tracker.models.*

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
fun Loading() {
    CenterElement {
        CircularProgressIndicator(modifier = Modifier.size(55.dp))
    }
}

@Composable
fun IconButton(modifier: Modifier, imageVector: ImageVector, onClick: () -> Unit) {
    // Button
    Button(
        modifier = modifier,
        onClick = onClick,
    ) {
//        CenterElement {
            Icon(
                imageVector = imageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
//        }

    }
}

@Composable
fun Divider() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        CenterElement {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(color = Color.Gray)
            )
        }
    }
}


@Preview(
    group = "Icon"
)
@Composable
fun PreviewIconButton() {
    IconButton(
        modifier = Modifier
            .size(ButtonDefaults.SmallButtonSize)
            .padding(all = 8.dp),
        imageVector = Icons.Rounded.Search){
        println("testing")
    }
}

// Text Preview
@Preview(
    group = "Icon",
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    apiLevel = WEAR_PREVIEW_API_LEVEL,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun PreviewLoading() {
    Loading()
}

@Preview (
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_ROW_HEIGHT_DP,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
    )
@Composable
fun PreviewDivider(){
    Divider()
}