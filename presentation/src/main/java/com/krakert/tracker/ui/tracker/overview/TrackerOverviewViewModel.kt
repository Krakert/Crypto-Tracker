package com.krakert.tracker.ui.tracker.overview

import android.app.Application
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.tracker.GetFavouriteCoins
import com.krakert.tracker.domain.tracker.GetListCoinsToAdd
import com.krakert.tracker.domain.tracker.GetPrices
import com.krakert.tracker.domain.tracker.model.CoinOverview
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class ViewStateOverview {
    // Represents different states for the overview screen
    object Loading : ViewStateOverview()
    data class Success(val data: CoinOverview) : ViewStateOverview()
    data class Problem(val exception: ProblemState?) : ViewStateOverview()
}

@HiltViewModel
class OverviewViewModel
@Inject constructor(
    private val application: Application,
    private val getFavouriteCoins: GetFavouriteCoins,
    private val getPrices: GetPrices,
) : ViewModel() {
    private val mutableStateOverview = MutableStateFlow<ViewStateOverview>(Loading)
    val overviewViewState = mutableStateOverview.asStateFlow()

    fun getAllOverviewData() {
        viewModelScope.launch {
            mutableStateOverview.emit(Loading)

            }
        }

//        val favoriteCoinList = sharedPreferences.getFavoriteCoinList()
//
//        if (favoriteCoinList.isNotEmpty()) {
//            viewModelScope.launch {
//                if (!cryptoRepository.isOnline()) {
//                    _viewState.value = ViewStateOverview.Problem(ProblemState.NO_CONNECTION)
//                } else {
//                    cryptoRepository.getPriceCoins(
//                        idCoins = getCoinsIdString(favoriteCoinList),
//                        currency = sharedPreferences.Currency.toString()
//                    ).collect { result ->
//                        when (result) {
//                            is Resource.Success -> {
//                                //All out success
//                                val overviewMergedCoinData =
//                                    getHistoryForCoins(favoriteCoinList, result)
//                                _viewState.value = if (overviewMergedCoinData.isNotEmpty()) {
//                                    ViewStateOverview.Success(overviewMergedCoinData)
//                                } else {
//                                    ViewStateOverview.Problem(ProblemState.COULD_NOT_LOAD)
//                                }
//                            }
//
//                            is Resource.Error -> {
//                                _viewState.value = ViewStateOverview.Problem(result.state)
//                            }
//
//                            is Resource.Loading -> {
//                                _viewState.value = ViewStateOverview.Loading
//                            }
//                        }
//                    }
//                }
//            }
//        } else {
//            _viewState.value = ViewStateOverview.Problem(ProblemState.EMPTY)
//        }
//    }

//    private suspend fun getHistoryForCoins(
//        listCoins: ArrayList<FavoriteCoin>,
//        priceCoin: Resource<Map<String, MutableMap<String, Any>>>,
//    ) : ArrayList<OverviewMergedCoinData> {
//        val timestamp = System.currentTimeMillis()
//        val overviewCoinList = arrayListOf<OverviewMergedCoinData>()
//
//        listCoins.forEach { item ->
//            cryptoRepository.getHistoryByCoinId(
//                coinId = item.id,
//                currency = sharedPreferences.Currency.toString(),
//                days = sharedPreferences.AmountDaysTracking.toString()
//            ).collect { graphData ->
//                when (graphData) {
//                    is Resource.Success -> {
//                        graphData.data?.prices?.let { marketChart ->
//                            priceCoin.data?.get(item.id)?.put("market_chart", marketChart)
//                        }
//                        priceCoin.data?.get(item.id)?.put("time_stamp", timestamp)
//
//                        val dataChart = buildGraphData(graphData)
//
//                        val currency = sharedPreferences.Currency
//
//                        overviewCoinList.add(
//                            OverviewMergedCoinData(
//                                id = item.id,
//                                name = item.name,
//                                priceCoin.data?.get(item.id)?.get(currency).toString(),
//                                graphData = dataChart,
//                                timestamp = timestamp
//                            )
//                        )
//                    }
//                    is Resource.Error -> {
//                        Log.e("HistoryItem", "History for $item failed")
//                    }
//                    else -> {
//                        Log.e("HistoryItem", "Not a valid resource state triggered")
//                    }
//                }
//            }
//        }
//
//        return overviewCoinList
//    }

//    private fun buildGraphData(graphData: Resource<MarketChart>): ArrayList<GraphItem> {
//        val dataChart = arrayListOf<GraphItem>()
//        graphData.data?.prices?.forEach { datapoint ->
//            dataChart.add(
//                GraphItem(
//                    price = datapoint[1],
//                    timestamp = datapoint[0].toLong()
//                )
//            )
//
//        }
//        return dataChart
//    }

    //TODO: StringBuilder might be nice
//    private fun getCoinsIdString(
//        listCoins: ArrayList<FavoriteCoin>,
//    ) : String {
//        val idCoins = arrayListOf<String>()
//
//        listCoins.forEach {
//            idCoins.add(it.id)
//        }
//
//        return idCoins.joinToString(",")
//    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}