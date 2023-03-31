package com.krakert.tracker.ui.tracker.overview

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class OverviewViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val application: Application
) : ViewModel() {
}