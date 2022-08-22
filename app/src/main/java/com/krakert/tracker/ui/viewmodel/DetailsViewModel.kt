package com.krakert.tracker.ui.viewmodel

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference.FavoriteCoin
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.repository.CachedCryptoRepository
import com.krakert.tracker.ui.state.ViewStateDetailsCoins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val cachedCryptoRepository: CachedCryptoRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    //TODO: set this one via setter
    lateinit var coinId: String


    private val _viewStateDetailsCoin = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Loading)
    val detailsCoin = _viewStateDetailsCoin.asStateFlow()

    init {
        getDetailsCoinByCoinId(coinId)
    }


    fun getDetailsCoinByCoinId(coinId: String){
        viewModelScope.launch {
            val response = cachedCryptoRepository.getDetailsCoinByCoinId(coinId )

            _viewStateDetailsCoin.value = ViewStateDetailsCoins.Success(response)


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