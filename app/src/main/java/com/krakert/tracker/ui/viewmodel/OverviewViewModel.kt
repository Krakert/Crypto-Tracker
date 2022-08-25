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
<<<<<<< HEAD
import com.krakert.tracker.models.OverviewMergedCoinData
import com.krakert.tracker.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
=======
import com.krakert.tracker.repository.CryptoApiRepository
import com.krakert.tracker.ui.state.ViewStateOverview
>>>>>>> State-flow-rework
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
        private val cachedCryptoRepository: CryptoRepository,
        private val sharedPreferences : SharedPreferences
    ): ViewModel() {

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Empty)
    val overviewViewState = _viewState.asStateFlow()

<<<<<<< HEAD
    init {
        fetchAllOverviewData()
    }
=======
    private val _viewStateOverview = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Empty)
    val stateOverview = _viewStateOverview.asStateFlow()
>>>>>>> State-flow-rework

    //TODO: is viewmodelscope really necessary here?
    fun fetchAllOverviewData() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val dataSharedPreference = sharedPreferences.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type

            val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(dataSharedPreference, typeOfT)

<<<<<<< HEAD
            if(listFavoriteCoins.isEmpty()) {
                //TODO: Extract to strings.xml
                _viewState.value = ViewStateOverview.Error("Please add one or more favorite coins")
            } else {
                getAllDataByListCoinIds()
=======
            if (listFavoriteCoins.isNotEmpty()) {
                _viewStateOverview.value = ViewStateOverview.Loading(listFavoriteCoins)
>>>>>>> State-flow-rework
            }
        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message ?: "Something went wrong while retrieving the list of coins")
            _viewStateOverview.value = ViewStateOverview.Error(e.message.toString())
        }
    }

    fun getAllDataByListCoinIds(listCoins: ArrayList<FavoriteCoin>){
        viewModelScope.launch {
            val time = System.currentTimeMillis()

            cachedCryptoRepository.getPriceCoins(
                idCoins = getCoinsIdString(listCoins),
                currency = sharedPreferences.Currency.toString()
            ).collect { priceCoin ->
                when(priceCoin) {
                    is Resource.Success -> {
                        val overviewCoinList = arrayListOf<OverviewMergedCoinData>()

                        listCoins.forEach { item ->
                            val currency = sharedPreferences.Currency?.lowercase()
                            OverviewMergedCoinData(id = item.id, name = item.name, priceCoin.data?.get(item.id)?.get(currency).toString(),
                                listOf())
                            val historyCoinData = cachedCryptoRepository.getHistoryByCoinId(
                                coinId = item.id,
                                currency = sharedPreferences.Currency.toString(),
                                days = sharedPreferences.AmountDaysTracking.toString()
                            )

                            when (historyCoinData) {
                                is Resource.Success -> {
                                    historyCoinData.data?.prices?.let { marketChart ->
                                        priceCoin.data?.get(item.id)?.put("market_chart", marketChart)
                                    }
                                    priceCoin.data?.get(item.id)?.put("time_stamp", time)
                                }
                                is Resource.Error -> {
                                    _viewState.value = ViewStateOverview.Error("History for ${item} failed")

                                }
                            }
                        }
<<<<<<< HEAD

                        //All out success
                        _viewState.value = ViewStateOverview.Success(priceCoin)
=======
                        mapData.data?.get(index.id)?.put("time_stamp", time)
                        mapData.data?.get(index.id)?.put("name", index.name)
>>>>>>> State-flow-rework
                    }
                    is Resource.Error -> {
                        _viewState.value = ViewStateOverview.Error("Cant get price coin data")

                    }
                    else -> {}
                }
<<<<<<< HEAD

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


=======
            }
            if (hadError){
                _viewStateOverview.value = ViewStateOverview.Error(errorString)
            } else {
                _viewStateOverview.value = ViewStateOverview.Loaded(mapData)
            }
        }
    }
}
>>>>>>> State-flow-rework
