package com.krakert.tracker.ui.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.repository.CryptoApiRepository
import com.krakert.tracker.ui.state.ViewStateDataCoins
import com.krakert.tracker.ui.state.ViewStateOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type


class OverviewViewModel(context: Context) : ViewModel() {
    private val cryptoApiRepository: CryptoApiRepository = CryptoApiRepository()
    private val sharedPreference = SharedPreference.sharedPreference(context)

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Loading)
    val favoriteCoins = _viewState.asStateFlow()
    // StateFlow
    private val _viewStateDataCoin = MutableStateFlow<ViewStateDataCoins>(ViewStateDataCoins.Loading)
    val dataCoin = _viewStateDataCoin.asStateFlow()

    fun getFavoriteCoins() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val dataSharedPreference = sharedPreference.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type

            val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(dataSharedPreference, typeOfT)

            if (listFavoriteCoins.isEmpty()) {
                _viewState.value = ViewStateOverview.Empty
            } else {
                _viewState.value = ViewStateOverview.Success(listFavoriteCoins)
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message ?: "Something went wrong while retrieving the list of coins")
            _viewState.value = ViewStateOverview.Error(e.message.toString())
        }
    }

    fun getAllDataByListCoinIds(listCoins: ArrayList<FavoriteCoin>){
        viewModelScope.launch {
            val idCoins = arrayListOf<String>()
            val time = System.currentTimeMillis()
            var errorString = ""
            var hadError = false
            listCoins.forEach {
                idCoins.add(it.id)
            }
            val mapData = cryptoApiRepository.getPriceCoins(
                idCoins = idCoins.joinToString(","),
                currency = sharedPreference.Currency.toString()
            )
            listCoins.forEach { index ->
                val result = cryptoApiRepository.getHistoryByCoinId(
                    coinId = index.id,
                    currency = sharedPreference.Currency.toString(),
                    days = sharedPreference.AmountDaysTracking.toString()
                )
                when (result) {
                    is Resource.Success -> {
                        result.data?.prices?.let {
                            mapData.data?.get(index.id)?.put("market_chart", it)
                        }
                        mapData.data?.get(index.id)?.put("time_stamp", time)
                    }
                    is Resource.Error -> {
                        hadError = true
                        when (result.message?.toInt()) {
                            429 -> errorString = "Please retry in 1 minute"
                            404 -> errorString = "Please try again"
                        }

                    }
                }
            }
            if (hadError){
                _viewStateDataCoin.value = ViewStateDataCoins.Error(errorString)
            } else {
                _viewStateDataCoin.value = ViewStateDataCoins.Success(mapData)
            }
        }
    }
}


