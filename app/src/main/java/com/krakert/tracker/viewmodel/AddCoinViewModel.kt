package com.krakert.tracker.viewmodel

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.model.Coin
import com.krakert.tracker.repository.FirebaseRepository
import com.krakert.tracker.state.ViewStateAddCoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AddCoinViewModel: ViewModel(){
    private var fireBaseRepo: FirebaseRepository = FirebaseRepository()

    init {
        getListCoins()
    }

    // backing property to avoid state updates from other classes
    private val _viewState = MutableStateFlow<ViewStateAddCoin>(ViewStateAddCoin.Loading)

    // the UI collects from this StateFlow to get it's state update
    val listCoins = _viewState.asStateFlow()

    fun getListCoins() = viewModelScope.launch(Dispatchers.IO) {
        fireBaseRepo.getListCoins().collect { result ->
            try {
                if (result.Coins?.size == null) {
                    _viewState.value = ViewStateAddCoin.Empty
                } else {
                    _viewState.value = ViewStateAddCoin.Success(result)
                }
            } catch (e: FirebaseRepository.FireBaseExceptionError) {
                val errorMsg = "Something went wrong while retrieving the list of coins"

                Log.e(TAG, e.message ?: errorMsg)
                _viewState.value = ViewStateAddCoin.Error(e)
            }
        }
    }

    fun addCoinToFavoriteCoins(coin: Coin, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fireBaseRepo.addCoinToFavoriteCoins(coin, context)
            } catch (e: FirebaseRepository.FireBaseExceptionError) {
                val errorMsg = "Something went wrong while updating the favorites coins"
                Log.e(TAG, e.message ?: errorMsg)
            }
        }
    }
}