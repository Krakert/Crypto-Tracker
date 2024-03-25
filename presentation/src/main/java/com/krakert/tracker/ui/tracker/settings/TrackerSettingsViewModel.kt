package com.krakert.tracker.ui.tracker.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.tracker.GetAllPreferences
import com.krakert.tracker.domain.tracker.ResetPreferences
import com.krakert.tracker.domain.tracker.SetAmountDaysTracking
import com.krakert.tracker.domain.tracker.SetCacheRateTime
import com.krakert.tracker.domain.tracker.SetPreferencesCurrency
import com.krakert.tracker.ui.components.ContentState
import com.krakert.tracker.ui.components.OnDisplay
import com.krakert.tracker.ui.components.OnLoading
import com.krakert.tracker.ui.tracker.model.Currency
import com.krakert.tracker.ui.tracker.settings.mapper.PreferencesDisplayMapper
import com.krakert.tracker.ui.tracker.settings.model.SettingsDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TrackerSettingsViewModel
@Inject constructor(
    private val setAmountDaysTracking: SetAmountDaysTracking,
    private val resetPreferences: ResetPreferences,
    private val setCacheRateTime: SetCacheRateTime,
    private val setPreferencesCurrency: SetPreferencesCurrency,
    private val getAllPreferences: GetAllPreferences,
    private val preferencesDisplayMapper: PreferencesDisplayMapper
) : ViewModel() {

    private val mutableStateSettings = MutableStateFlow<ContentState<SettingsDisplay>>(OnLoading)
    val settingViewState = mutableStateSettings.asStateFlow()

    fun getSettings() {
        viewModelScope.launch {
            mutableStateSettings.emit(
                OnDisplay(
                    preferencesDisplayMapper.map(getAllPreferences())
                )
            )
        }
    }

    fun resetSettings() = resetPreferences

    fun setAmountOfDays(amountDaysTracking: Int) = setAmountDaysTracking(amountDaysTracking)

    fun setCacheRate(minutesCache: Int) = setCacheRateTime(minutesCache)

    fun setCurrency(currency: Currency) = setPreferencesCurrency(currency.name)

}