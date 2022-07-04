package com.krakert.tracker.tile

import androidx.wear.tiles.DeviceParametersBuilders.DeviceParameters
import androidx.wear.tiles.DimensionBuilders
import androidx.wear.tiles.DimensionBuilders.expand
import androidx.wear.tiles.LayoutElementBuilders.*
import androidx.wear.tiles.RequestBuilders.ResourcesRequest
import androidx.wear.tiles.RequestBuilders.TileRequest
import androidx.wear.tiles.ResourceBuilders
import androidx.wear.tiles.ResourceBuilders.Resources
import androidx.wear.tiles.TileBuilders.Tile
import androidx.wear.tiles.TileService
import androidx.wear.tiles.TimelineBuilders.Timeline
import androidx.wear.tiles.TimelineBuilders.TimelineEntry
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.guava.future


// dimensions
private val PROGRESS_BAR_THICKNESS = DimensionBuilders.dp(6f)
private val BUTTON_SIZE = DimensionBuilders.dp(48f)
private val BUTTON_RADIUS = DimensionBuilders.dp(24f)
private val BUTTON_PADDING = DimensionBuilders.dp(12f)
private val VERTICAL_SPACING_HEIGHT = DimensionBuilders.dp(8f)

// identifiers
private const val ID_IMAGE_START_RUN = "image_start_run"
private const val ID_CLICK_START_RUN = "click_start_run"

private const val RESOURCES_VERSION = "1"

class CoinTileService : TileService() {


    private val serviceScope = CoroutineScope(Dispatchers.IO)

    override fun onTileRequest(requestParams: TileRequest) = serviceScope.future {

        val sharedPreference = SharedPreference.sharedPreference(context = applicationContext)
        val tileCoin = sharedPreference.FavoriteCoin

        // Retrieves device parameters to later retrieve font styles for any text in the Tile.
        val deviceParams = requestParams.deviceParameters!!

        if (tileCoin != "") {
//            val result = tileCoin?.let { CoinRepoTile.getDetails(it, applicationContext) }

            // Creates Tile.
            Tile.Builder()
                .setResourcesVersion(RESOURCES_VERSION)

                .setTimeline(
                    Timeline.Builder()
                        .addTimelineEntry(
                            TimelineEntry.Builder()
                                .setLayout(
                                    Layout.Builder()
//                                        .setRoot(
//                                            layoutSet(result!!, deviceParams)
//                                        )
                                        .build()
                                )
                                .build()
                        )
                        .build()
                ).build()

        } else {
            // Creates Tile.
            Tile.Builder()
                .setResourcesVersion(RESOURCES_VERSION)

                .setTimeline(
                    Timeline.Builder()
                        .addTimelineEntry(
                            TimelineEntry.Builder()
                                .setLayout(
                                    Layout.Builder()
                                        .setRoot(
                                            layoutNotSet(deviceParams)
                                        )
                                        .build()
                                )
                                .build()
                        )
                        .build()
                ).build()
        }
    }


    override fun onResourcesRequest(requestParams: ResourcesRequest) = serviceScope.future {
        Resources.Builder()
            .setVersion(RESOURCES_VERSION)
            .addIdToImageMapping(
                ID_IMAGE_START_RUN,
                ResourceBuilders.ImageResource.Builder()
                    .setAndroidResourceByResId(
                        ResourceBuilders.AndroidImageResourceByResId.Builder()
//                            .setResourceId(R.drawable.ic_baseline_start)
                            .build()
                    )
                    .build()
            )
            .build()
    }


    private fun layoutSet(result: DataTileCoin, deviceParameters: DeviceParameters) =
        Box.Builder()
            // Sets width and height to expand and take up entire Tile space.
            .setWidth(expand())
            .setHeight(expand())

            .addContent(
                Column.Builder()
                    // Adds a [Text] via local function.
                    .addContent(
                        Text(result.name, deviceParameters)
                    )
                    .addContent(
                        Text(result.priceCurrent.toString(), deviceParameters)
                    )
                    // Adds a [Text] via local function.
//                .addContent(
//                    totalStepsText(
//                        resources.getString(R.string.goal, goalProgress.goal),
//                        deviceParameters
//                    )
//                )
                    // Adds a [Spacer].
                    .addContent(Spacer.Builder().setHeight(VERTICAL_SPACING_HEIGHT).build())
                    // Adds an [Image] via local function.
//                .addContent(startRunButton())
                    .build()
            )

            .build()

    private fun layoutNotSet(deviceParameters: DeviceParameters) =
        Box.Builder()
            // Sets width and height to expand and take up entire Tile space.
            .setWidth(expand())
            .setHeight(expand())

            .addContent(
                Column.Builder()
                    // Adds a [Text] via local function.
                    .addContent(
                        Text("you have not yet set a favorite coin for the Tile", deviceParameters)
                    )
//                    .addContent(startRunButton())
                    .build()
            )
            .build()


    private fun Text(current: String, deviceParameters: DeviceParameters) =
        Text.Builder()
            .setText(current)
            .setFontStyle(FontStyles.title3(deviceParameters).build())
            .setOverflow(TEXT_OVERFLOW_TRUNCATE)
            .build()


}

