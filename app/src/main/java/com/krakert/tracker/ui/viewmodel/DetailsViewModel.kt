package com.krakert.tracker.ui.viewmodel

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.FavoriteCoin
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.ui.FavoriteCoin
import com.krakert.tracker.models.ui.DetailsCoin
import com.krakert.tracker.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

sealed class ViewStateDetailsCoins {
    object Empty : ViewStateDetailsCoins()
    object Loading : ViewStateDetailsCoins()
    data class Success(val details: DetailsCoin) : ViewStateDetailsCoins()
    data class Error(val exception: String) : ViewStateDetailsCoins()
}

@Suppress("UnstableApiUsage")
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val CryptoRepository: CryptoRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    lateinit var coinId: String

    private val _viewState = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Empty)
    val detailsCoin = _viewState.asStateFlow()

    fun getDetailsCoinByCoinId(){
        viewModelScope.launch {
            val response = CryptoRepository.getDetailsCoinByCoinId(
                coinId = coinId
            ).collect{ result ->
                when (result) {
                    is Resource.Success -> {
                        _viewState.value = ViewStateDetailsCoins.Success(
                            DetailsCoin(
                                name = result.data?.name.toString(),
                                image = result.data?.image,
                                currentPrice = result.data?.marketData?.currentPrice?.get(sharedPreferences.Currency).toString(),
                                priceChangePercentage24h = result.data?.marketData?.priceChangePercentage24h ?: 0.0,
                                priceChangePercentage7d = result.data?.marketData?.priceChangePercentage7d ?: 0.0,
                                circulatingSupply = result.data?.marketData?.circulatingSupply ?: 0.0,
                                high24h = result.data?.marketData?.high24h?.get(sharedPreferences.Currency) ?: 0.0,
                                low24h = result.data?.marketData?.low24h?.get(sharedPreferences.Currency) ?: 0.0,
                                marketCap = result.data?.marketData?.marketCap?.get(sharedPreferences.Currency) ?: 0.0,
                                marketCapChangePercentage24h = result.data?.marketData?.marketCapChangePercentage24h ?: 0.0
                            )
                        )
                    }
                    is Resource.Error -> {
                        _viewState.value = ViewStateDetailsCoins.Error("Cant get details coin")
                    }
                }
            }
        }
    }

    fun removeCoinFromFavoriteCoins(coinId: String) {
        try {
            val dataSharedPreferences = sharedPreferences.FavoriteCoins.toString()
            val favoriteCoin = sharedPreferences.FavoriteCoin
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
            val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(dataSharedPreferences, typeOfT)

            var indexToRemove: Int? = null
                listFavoriteCoins.forEach {
                    if (it.id == coinId.lowercase())
                        indexToRemove = listFavoriteCoins.indexOf(it)
                }

            indexToRemove?.let { listFavoriteCoins.removeAt(it) }

            sharedPreferences.FavoriteCoins = Gson().toJson(listFavoriteCoins)

            if (favoriteCoin == coinId){
                sharedPreferences.FavoriteCoin = ""
            }

        } catch (e: Exception) {
            Log.e(ContentValues.TAG, e.message ?: "Something went wrong while deleting a coins")
        }
    }
}