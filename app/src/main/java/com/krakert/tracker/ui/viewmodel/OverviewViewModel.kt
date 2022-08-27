@file:Suppress("UnstableApiUsage")

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
import com.krakert.tracker.SharedPreference.getFavoriteCoinList
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.responses.MarketChart
import com.krakert.tracker.models.ui.FavoriteCoin
import com.krakert.tracker.models.ui.GraphItem
import com.krakert.tracker.models.ui.OverviewMergedCoinData
import com.krakert.tracker.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

sealed class ViewStateOverview {
    // Represents different states for the overview screen
    object Empty : ViewStateOverview()
    object Loading : ViewStateOverview()
    data class Success(val data: List<OverviewMergedCoinData>) : ViewStateOverview()
    data class Error(val exception: String) : ViewStateOverview()
}

@HiltViewModel
class OverviewViewModel
    @Inject constructor(
        private val cryptoRepository: CryptoRepository,
        private val sharedPreferences : SharedPreferences
    ): ViewModel() {

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Empty)
    val overviewViewState = _viewState.asStateFlow()

    fun fetchAllOverviewData(){
        val favoriteCoinList = sharedPreferences.getFavoriteCoinList()

        viewModelScope.launch {
            cryptoRepository.getPriceCoins(
                idCoins = getCoinsIdString(favoriteCoinList),
                currency = sharedPreferences.Currency.toString()
            ).collect { priceCoin ->
                when(priceCoin) {
                    is Resource.Success -> {
                        val overviewCoinList = getHistoryForCoins(favoriteCoinList, priceCoin)

                        //All out success
                        _viewState.value = ViewStateOverview.Success(overviewCoinList)
                    }
                    is Resource.Error -> {
                        _viewState.value = ViewStateOverview.Error("Cant get price coin data")
                    }
                    is Resource.Loading -> {
                        _viewState.value = ViewStateOverview.Loading
                    }
                    else -> {
                        Log.e("PriceCoin", "Not a valid resource state triggered")
                    }
                }
            }
        }
    }

    private suspend fun getHistoryForCoins(
        listCoins: ArrayList<FavoriteCoin>,
        priceCoin: Resource<Map<String, MutableMap<String, Any>>>,
    ) : ArrayList<OverviewMergedCoinData> {
        val timestamp = System.currentTimeMillis()
        val overviewCoinList = arrayListOf<OverviewMergedCoinData>()

        listCoins.forEach { item ->
            cryptoRepository.getHistoryByCoinId(
                coinId = item.id,
                currency = sharedPreferences.Currency.toString(),
                days = sharedPreferences.AmountDaysTracking.toString()
            ).collect { graphData ->
                when (graphData) {
                    is Resource.Success -> {
                        graphData.data?.prices?.let { marketChart ->
                            priceCoin.data?.get(item.id)?.put("market_chart", marketChart)
                        }
                        priceCoin.data?.get(item.id)?.put("time_stamp", timestamp)

                        val dataChart = buildGraphData(graphData)

                        val currency = sharedPreferences.Currency

                        overviewCoinList.add(
                            OverviewMergedCoinData(
                                id = item.id,
                                name = item.name,
                                priceCoin.data?.get(item.id)?.get(currency).toString(),
                                graphData = dataChart,
                                timestamp = timestamp
                            )
                        )
                    }
                    is Resource.Error -> {
                        Log.e("HistoryItem", "History for $item failed")
                    }
                    else -> {
                        Log.e("HistoryItem", "Not a valid resource state triggered")
                    }
                }
            }
        }

        return overviewCoinList
    }

    private fun buildGraphData(graphData: Resource<MarketChart>): ArrayList<GraphItem> {
        val dataChart = arrayListOf<GraphItem>()
        graphData.data?.prices?.forEach { datapoint ->
            dataChart.add(
                GraphItem(
                    price = datapoint[1],
                    timestamp = datapoint[0].toLong()
                )
            )

        }
        return dataChart
    }

    //TODO: StringBuilder might be nice
    private fun getCoinsIdString(
        listCoins: ArrayList<FavoriteCoin>,
    ) : String {
        val idCoins = arrayListOf<String>()

        listCoins.forEach {
            idCoins.add(it.id)
        }

        return idCoins.joinToString(",")
    }
}