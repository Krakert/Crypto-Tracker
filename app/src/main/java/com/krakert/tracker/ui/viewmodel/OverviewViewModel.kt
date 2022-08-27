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
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.ui.FavoriteCoin
import com.krakert.tracker.models.ui.GraphItem
import com.krakert.tracker.models.ui.OverviewMergedCoinData
import com.krakert.tracker.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
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
        private val CryptoRepository: CryptoRepository,
        private val sharedPreferences : SharedPreferences
    ): ViewModel() {

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Empty)
    val overviewViewState = _viewState.asStateFlow()


    //TODO: is viewmodelscope really necessary here?
    fun fetchAllOverviewData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val dataSharedPreference = sharedPreferences.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
            var listFavoriteCoins = arrayListOf<FavoriteCoin>()

            if (dataSharedPreference.isNotEmpty()){
                listFavoriteCoins = Gson().fromJson(dataSharedPreference, typeOfT)
            }

            if(listFavoriteCoins.isEmpty()) {
                //TODO: Extract to strings.xml
                _viewState.value = ViewStateOverview.Error("Please add one or more favorite coins")
            } else {
                getAllDataByListCoinIds(listFavoriteCoins)
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message ?: "Something went wrong while retrieving the list of coins")
            _viewState.value = ViewStateOverview.Error(e.message.toString())
        }
    }

    private fun getAllDataByListCoinIds(listCoins: ArrayList<FavoriteCoin>){
        viewModelScope.launch {
            val time = System.currentTimeMillis()

            CryptoRepository.getPriceCoins(
                idCoins = getCoinsIdString(listCoins),
                currency = sharedPreferences.Currency.toString()
            ).collect { priceCoin ->
                when(priceCoin) {
                    is Resource.Success -> {
                        val overviewCoinList = arrayListOf<OverviewMergedCoinData>()

                        listCoins.forEach { item ->
                            CryptoRepository.getHistoryByCoinId(
                                coinId = item.id,
                                currency = sharedPreferences.Currency.toString(),
                                days = sharedPreferences.AmountDaysTracking.toString()
                            ).collect { graphData ->
                                when (graphData) {
                                    is Resource.Success -> {
                                        graphData.data?.prices?.let { marketChart ->
                                            priceCoin.data?.get(item.id)?.put("market_chart", marketChart)
                                        }
                                        priceCoin.data?.get(item.id)?.put("time_stamp", time)

                                        //TODO: Is this oke? Like this?
                                        val dataChart = arrayListOf<GraphItem>()
                                        graphData.data?.prices?.forEach { datapoint ->
                                            dataChart.add(
                                                GraphItem(
                                                    price = datapoint[1],
                                                    timestamp = datapoint[0].toLong()
                                                )
                                            )

                                        }
                                        val currency = sharedPreferences.Currency
                                        overviewCoinList.add(OverviewMergedCoinData(
                                            id = item.id,
                                            name = item.name,
                                            priceCoin.data?.get(item.id)?.get(currency).toString(),
                                            graphData = dataChart,
                                            timestamp = time)
                                        )
                                    }
                                    is Resource.Error -> {
                                        _viewState.value = ViewStateOverview.Error("History for $item failed")
                                    }
                                    else -> {}
                                }
                            }
                        }
                        //All out success
                        _viewState.value = ViewStateOverview.Success(overviewCoinList)
                    }
                    is Resource.Error -> {
                        _viewState.value = ViewStateOverview.Error("Cant get price coin data")
                    }
                    else -> {}
                }
            }
        }
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