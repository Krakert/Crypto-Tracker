package com.krakert.tracker

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.fragment.app.FragmentActivity
import com.krakert.tracker.navigation.NavGraph

class MainActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TrackerMain()
        }
    }

    @Composable
    fun TrackerMain() {
        NavGraph()
    }
}