package com.krakert.tracker.ui.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.models.Coin
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.repository.CachedCryptoRepository
import com.krakert.tracker.ui.state.ViewStateAddCoin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type
import javax.inject.Inject

@HiltViewModel
class AddCoinViewModel @Inject constructor(
    private val cachedCryptoRepository: CachedCryptoRepository,
    private val sharedPreferences: SharedPreferences,
) : ViewModel() {

    // backing property to avoid state updates from other classes
    private val _viewState = MutableStateFlow<ViewStateAddCoin>(ViewStateAddCoin.Loading)

    // the UI collects from this StateFlow to get it's state update
    val listCoins = _viewState.asStateFlow()

    fun getListCoins() {
        viewModelScope.launch {
            _viewState.value = ViewStateAddCoin.Success(cachedCryptoRepository.getListCoins())
        }
    }

    fun addCoinToFavoriteCoins(coin: Coin, context: Context) {
        try {
            var listFavoriteCoins = ArrayList<FavoriteCoin>()
            var alreadyAdded = false
            val dataSharedPreferences = sharedPreferences.FavoriteCoins.toString()

            //TODO: Fix this warning, serious warning
            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type

            if (dataSharedPreferences.isNotEmpty()) {
                listFavoriteCoins = Gson().fromJson(dataSharedPreferences, typeOfT)
            }
            listFavoriteCoins.forEach {
                if (it.name == coin.name) {
                    alreadyAdded = true
                    Toast.makeText(context, context.getString(R.string.txt_toast_already_added), Toast.LENGTH_SHORT)
                        .show()
                }
            }
            if (!alreadyAdded) {
                listFavoriteCoins.add(
                    FavoriteCoin(
                    id = coin.id,
                    name = coin.name)
                )
                sharedPreferences.FavoriteCoins = Gson().toJson(listFavoriteCoins)
                Toast.makeText(context, context.getString(R.string.txt_toast_added, coin.name), Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            Log.e(TAG, e.message ?: "Something went wrong while saving the list of favorite coins")
            _viewState.value = ViewStateAddCoin.Error(e.message.toString())
        }
    }
}