package com.krakert.tracker.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.repository.CoinGeckoRepository
import kotlinx.coroutines.launch

class OverviewViewModel(application: Application) : AndroidViewModel(application) {

    private val geckoRepo = CoinGeckoRepository()

    fun getListCoins() {
        viewModelScope.launch {
            geckoRepo.getListCoins()
        }
    }
}