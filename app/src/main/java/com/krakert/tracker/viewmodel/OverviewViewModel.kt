package com.krakert.tracker.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.model.Coin
import com.krakert.tracker.model.DataCoin
import com.krakert.tracker.repository.CoinGeckoRepository
import com.krakert.tracker.repository.FirebaseRepository
import com.krakert.tracker.state.ViewStateDataCoins
import com.krakert.tracker.state.ViewStateOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type


class OverviewViewModel(context: Context) : ViewModel() {
    private val coinGeckoRepo: CoinGeckoRepository = CoinGeckoRepository(context)
    private val sharedPreference = SharedPreference.sharedPreference(context)

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Loading)
    val favoriteCoins = _viewState.asStateFlow()

    private val _viewStateDataCoin = MutableStateFlow<ViewStateDataCoins>(ViewStateDataCoins.Loading)
    val dataCoin = _viewStateDataCoin.asStateFlow()

    fun getFavoriteCoins() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val dataSharedPreference = sharedPreference.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<ArrayList<Coin>>() {}.type
            if (dataSharedPreference.isBlank()){
                _viewState.value = ViewStateOverview.Empty
            } else {
                val listFavoriteCoins: ArrayList<Coin> = Gson().fromJson(dataSharedPreference, typeOfT)
                _viewState.value = ViewStateOverview.Success(listFavoriteCoins)
            }
        } catch (e: FirebaseRepository.FireBaseExceptionError) {
            val errorMsg = "Something went wrong while retrieving the list of coins"

            Log.e(ContentValues.TAG, e.message ?: errorMsg)
            _viewState.value = ViewStateOverview.Error(e)
        }
    }

    fun getAllDataByListCoinIds(listResult: List<Coin>) {
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


