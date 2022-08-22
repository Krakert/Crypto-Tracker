package com.krakert.tracker.ui.viewmodel

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.repository.CachedCryptoRepository
import com.krakert.tracker.repository.CryptoRepository
import com.krakert.tracker.ui.state.ViewStateDataCoins
import com.krakert.tracker.ui.state.ViewStateOverview
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel
    @Inject constructor(
        private val cachedCryptoRepository: CryptoRepository,
        private val sharedPreferences : SharedPreferences
    ): ViewModel() {

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Loading)
    val favoriteCoins = _viewState.asStateFlow()
    // StateFlow
    private val _viewStateDataCoin = MutableStateFlow<ViewStateDataCoins>(ViewStateDataCoins.Loading)
    val dataCoin = _viewStateDataCoin.asStateFlow()

    fun getFavoriteCoins() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val dataSharedPreference = sharedPreferences.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type

            val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(dataSharedPreference, typeOfT)

            _viewState.value = if (listFavoriteCoins.isEmpty()) {
                ViewStateOverview.Empty
            } else {
                ViewStateOverview.Success(listFavoriteCoins)
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

            listCoins.forEach {
                idCoins.add(it.id)
            }

            cachedCryptoRepository.getPriceCoins(
                idCoins = idCoins.joinToString(","),
                currency = sharedPreferences.Currency.toString()
            ).collect {
                val mapData = it
                when(mapData) {
                    is Resource.Success -> {

                        listCoins.forEach { index ->
                            val result = cachedCryptoRepository.getHistoryByCoinId(
                                coinId = index.id,
                                currency = sharedPreferences.Currency.toString(),
                                days = sharedPreferences.AmountDaysTracking.toString()
                            )
                            when (result) {
                                is Resource.Success -> {
//                                    mapData.data.prices?.let {
//                                        it.data[index.id]?.put("market_chart", it)
//                                    }
                                        Log.e("test", "test")
//                                    mapData.data?.get(index.id)?.put("time_stamp", time)
                                }
                                is Resource.Error -> {
                                    _viewStateDataCoin.value = ViewStateDataCoins.Error(errorString)

//                                    when (result.message?.toInt()) {
//                                        429 -> errorString = "Please retry in 1 minute"
//                                        404 -> errorString = "Please try again"
//                                    }

                                }
                            }
                        }

                        _viewStateDataCoin.value = ViewStateDataCoins.Success(it)
                    }
                    is Resource.Error -> {
                        _viewStateDataCoin.value = ViewStateDataCoins.Error(errorString)

                    }
                    else -> {}
                }

            }
        }
    }
}


