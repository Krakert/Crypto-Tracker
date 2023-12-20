package com.krakert.tracker.ui.tracker.add.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.palette.graphics.Palette
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.MaterialTheme
import androidx.wear.compose.material.Text
import com.krakert.tracker.presentation.R
import com.krakert.tracker.ui.tracker.add.model.ListCoinsItemDisplay
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_WIDTH_DP
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.palette.PalettePlugin

@Composable
fun ChipCoin(coin: ListCoinsItemDisplay, onClick: () -> Unit) {
    var palette by remember { mutableStateOf<Palette?>(null) }
    var isFavorite by remember { mutableStateOf(coin.isFavourite) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape = RoundedCornerShape(50))
            .clickable {
                isFavorite = !isFavorite
                onClick()
            }
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(palette?.lightVibrantSwatch?.rgb ?: 0).copy(alpha = 0.5f),
                        MaterialTheme.colors.surface
                    )
                )
            )
            .padding(
                horizontal = 8.dp,
                vertical = 4.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CoilImage(
                imageModel = { coin.imageUrl },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Fit
                ),
                modifier = Modifier
                    .size(32.dp)
                    .wrapContentSize(align = Alignment.Center),
                component = rememberImageComponent {
                    +PalettePlugin { palette = it }
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                modifier = Modifier.weight(1f, true),
                text = coin.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(8.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector =
                if (isFavorite) {
                    Icons.Default.Favorite
                } else {
                    Icons.Default.FavoriteBorder
                }, contentDescription = stringResource(
                    id = R.string.button_with_icon,
                    "Heart"
                )
            )
        }
    }

}

@Preview(widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP)
@Composable
fun PreviewChipCoin() {
    ChipCoin(coin = ListCoinsItemDisplay("bitcoin", "", "Text that should be to long", true)) {}
}
