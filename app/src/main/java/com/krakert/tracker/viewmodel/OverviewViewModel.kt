package com.krakert.tracker.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.model.DataCoin
import com.krakert.tracker.model.FavoriteCoin
import com.krakert.tracker.repository.CoinGeckoRepository
import com.krakert.tracker.repository.FirebaseRepository
import com.krakert.tracker.state.ViewStateDataCoins
import com.krakert.tracker.state.ViewStateOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class OverviewViewModel(context: Context) : ViewModel() {
    private val fireBaseRepo: FirebaseRepository = FirebaseRepository()
    private val coinGeckoRepo: CoinGeckoRepository = CoinGeckoRepository(context)

    init {
        getFavoriteCoins()
    }

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Loading)
    val favoriteCoins = _viewState.asStateFlow()

    private val _viewStateDataCoin = MutableStateFlow<ViewStateDataCoins>(ViewStateDataCoins.Loading)
    val dataCoin = _viewStateDataCoin.asStateFlow()

    fun getFavoriteCoins() = viewModelScope.launch(Dispatchers.IO) {
        fireBaseRepo.getFavoriteCoins().collect { result ->
            try {
                if (result.Coins.isNullOrEmpty()) {
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

    fun getAllDataByListCoinIds(listResult: List<FavoriteCoin>) {
        val data = arrayListOf<DataCoin>()
        viewModelScope.launch {
            try {
                listResult.forEach { index ->
                    data.add(
                        DataCoin(
                            history = coinGeckoRepo.getHistoryByCoinId(index.idCoin.toString()),
                            currentPrice = coinGeckoRepo.getLatestPriceByCoinId(index.idCoin.toString())
                        )
                    )
                }
                _viewStateDataCoin.value = ViewStateDataCoins.Success(data)
            } catch (e: CoinGeckoRepository.CoinGeckoExceptionError) {
                val errorMsg = "Something went wrong while retrieving data"

                Log.e(ContentValues.TAG, e.message ?: errorMsg)
                _viewStateDataCoin.value = ViewStateDataCoins.Error(e)
            }
        }
    }
}


