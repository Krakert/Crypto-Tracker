package com.krakert.tracker.ui.tracker.detail.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.krakert.tracker.ui.components.CenterElement
import com.krakert.tracker.ui.theme.LocalDimensions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun TrackerDetailHeader(imageUrl: String, name: String) {
    CenterElement {
        CoilImage(
            imageModel = { imageUrl },
            imageOptions = ImageOptions(contentScale = ContentScale.Fit),
            modifier = Modifier
                .size(LocalDimensions.current.detailImageSize)
                .wrapContentSize(align = Alignment.Center),
        )
        Text(
            text = name,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.primary,
            fontSize = 24.sp
        )
    }
}