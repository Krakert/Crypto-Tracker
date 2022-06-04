package com.krakert.tracker

import android.content.ComponentName
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.wear.tiles.manager.TileUiClient
import com.krakert.tracker.databinding.ActivityMainBinding
import com.krakert.tracker.navigation.NavGraph
import com.krakert.tracker.tile.CoinTileService


class MainActivity : ComponentActivity() {

    private lateinit var tileUiClient: TileUiClient
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        setContent {
            TrackerMain()
        }

        // Enable this for tile
//        tileUiClient = TileUiClient(
//            context = this,
//            component = ComponentName(this,  CoinTileService::class.java),
//            parentView = binding.root
//        )
//        tileUiClient.connect()

    }

    @Composable
    fun TrackerMain() {
        NavGraph()
    }
}