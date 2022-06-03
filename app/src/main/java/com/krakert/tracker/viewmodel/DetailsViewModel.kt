package com.krakert.tracker.viewmodel

import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.model.Coin
import com.krakert.tracker.repository.CoinGeckoRepository
import com.krakert.tracker.repository.FirebaseRepository
import com.krakert.tracker.state.ViewStateDetailsCoins
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel(context: Context, coin: Coin?) : ViewModel() {
    private val coinGeckoRepo: CoinGeckoRepository = CoinGeckoRepository(context)
    private val fireBaseRepo: FirebaseRepository = FirebaseRepository()

    init {
        getDetailsCoinByCoinId(coin?.idCoin.toString())
    }


    private val _viewStateDetailsCoin = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Loading)
    val detailsCoin = _viewStateDetailsCoin.asStateFlow()

    fun getDetailsCoinByCoinId(coinId: String) {
        viewModelScope.launch {
            coinGeckoRepo.getDetailsCoinByCoinId(coinId).collect() { dataDetailsCoin ->
                try {
                    if (dataDetailsCoin.name.isEmpty()){
                        _viewStateDetailsCoin.value = ViewStateDetailsCoins.Empty
                    } else {
                        _viewStateDetailsCoin.value = ViewStateDetailsCoins.Success(dataDetailsCoin)
                    }
                } catch (e: CoinGeckoRepository.CoinGeckoExceptionError) {
                    val errorMsg = "Something went wrong while retrieving data"

                    Log.e(ContentValues.TAG, e.message ?: errorMsg)
                    _viewStateDetailsCoin.value = ViewStateDetailsCoins.Error(e)

                }
            }
        }
    }

    fun removeCoinFromFavoriteCoins(coin: Coin, context: Context){
        viewModelScope.launch {
            fireBaseRepo.removeCoinFromFavoriteCoins(coin, context)
        }
    }
}