package com.krakert.tracker.viewmodel

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.repository.CoinGeckoRepository
import com.krakert.tracker.repository.FirebaseRepository
import com.krakert.tracker.state.ViewStateData
import com.krakert.tracker.state.ViewStateOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class OverviewViewModel: ViewModel() {
    private val fireBaseRepo: FirebaseRepository = FirebaseRepository()
    private val coinGeckoRepo: CoinGeckoRepository = CoinGeckoRepository()

    init {
        getFavoriteCoins()
    }

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Loading)
    val favoriteCoins = _viewState.asStateFlow()


    private val _viewStateData = MutableStateFlow<ViewStateOverview>(ViewStateData.Loading)
    val dataCoin = _viewStateData.asStateFlow()

    fun getFavoriteCoins() = viewModelScope.launch(Dispatchers.IO) {
        fireBaseRepo.getFavoriteCoins().collect { result ->
            try {
                if (result.Favorite.isNullOrEmpty()){
                    _viewState.value = ViewStateOverview.Empty
                } else {
                    _viewState.value = ViewStateOverview.Success(result)
                }

            } catch (e: FirebaseRepository.FireBaseExceptionError) {
                val errorMsg = "Something went wrong while retrieving data"

                Log.e(ContentValues.TAG, e.message ?: errorMsg)
                _viewState.value = ViewStateOverview.Error(e)
            }
        }
    }

    fun getHistoryCoin(coinId: String) = viewModelScope.launch {
        coinGeckoRepo.getHistoryCoin(coinId).collect() { result ->
            try {
                _viewStateData.value = ViewStateData.Success(result)
            }   catch (e: CoinGeckoRepository.CoinGeckoExceptionError) {
                val errorMsg = "Something went wrong while retrieving data"

                Log.e(ContentValues.TAG, e.message ?: errorMsg)
                _viewState.value = ViewStateOverview.Error(e)
            }
        }
    }
}

