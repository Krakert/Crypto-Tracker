package com.krakert.tracker.ui.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    fun resetSettings() {
        viewModelScope.launch {
            sharedPreferences.edit().clear().apply()
        }
    }
}