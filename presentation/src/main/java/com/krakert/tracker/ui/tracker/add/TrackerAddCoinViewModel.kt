package com.krakert.tracker.ui.tracker.add

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.tracker.GetListCoinsToAdd
import com.krakert.tracker.ui.tracker.add.ViewStateAddCoin.Loading
import com.krakert.tracker.ui.tracker.add.ViewStateAddCoin.Problem
import com.krakert.tracker.ui.tracker.add.ViewStateAddCoin.Success
import com.krakert.tracker.ui.tracker.add.mapper.ListCoinsDisplayMapper
import com.krakert.tracker.ui.tracker.add.model.ListCoinsDisplay
import com.krakert.tracker.ui.tracker.add.model.ListCoinsItemDisplay
import com.krakert.tracker.ui.tracker.model.ProblemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.ConnectException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException


sealed class ViewStateAddCoin {
    // Represents different states for the add coin screen
    object Loading : ViewStateAddCoin()
    data class Success(val coins: ListCoinsDisplay) : ViewStateAddCoin()
    data class Problem(val exception: ProblemState?) : ViewStateAddCoin()
}

@HiltViewModel
class AddCoinViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val application: Application,
    private val getListCoinsToAdd: GetListCoinsToAdd,
    private val listCoinsDisplayMapper: ListCoinsDisplayMapper,
) : ViewModel() {
    private val mutableStateAdd = MutableStateFlow<ViewStateAddCoin>(Loading)
    val addViewState = mutableStateAdd.asStateFlow()
    fun getListCoins() {
        viewModelScope.launch {
            mutableStateAdd.emit(Loading)
            getListCoinsToAdd().onSuccess {
                mutableStateAdd.emit(
                    Success(listCoinsDisplayMapper.map(it))
                )
            }.onFailure {
                when (it){
                    is SSLHandshakeException -> {
                        mutableStateAdd.emit(Problem(ProblemState.SSL))
                    }
                    is ConnectException ->{
                        mutableStateAdd.emit(Problem(ProblemState.NO_CONNECTION))
                    }
                    else -> {
                        mutableStateAdd.emit(Problem(ProblemState.UNKNOWN))
                    }
                }
            }
        }
    }

    //    private val cacheRateLimit = CacheRateLimiter<String>(sharedPreferences.MinutesCache, TimeUnit.MINUTES)
//
//    // backing property to avoid state updates from other classes
//    private val _viewState = MutableStateFlow<ViewStateAddCoin>(ViewStateAddCoin.Loading)
//
//    // the UI collects from this StateFlow to get it's state update
//    val listCoins = _viewState.asStateFlow()
//
//    fun getListCoins() {
//        viewModelScope.launch {
//            if (!CryptoRepository.isOnline()) {
//                _viewState.value = ViewStateAddCoin.Problem(ProblemState.NO_CONNECTION)
//            } else {
//                CryptoRepository.getListCoins().collect { result ->
//                    when (result) {
//                        is Resource.Success -> {
//                            if (result.data?.isEmpty() == false) {
//                                _viewState.value = ViewStateAddCoin.Success(result.data)
//                            } else {
//                                _viewState.value = ViewStateAddCoin.Problem(ProblemState.COULD_NOT_LOAD)
//                            }
//                        }
//                        is Resource.Error -> {
//                            _viewState.value = ViewStateAddCoin.Problem(result.state)
//                        }
//                        is Resource.Loading -> {
//                            _viewState.value = ViewStateAddCoin.Loading
//                        }
//                    }
//                }
//            }
//        }
//    }
//
    fun toggleFavoriteCoin(coin: ListCoinsItemDisplay, context: Context) {
        try {
//            var listFavoriteCoins = ArrayList<FavoriteCoin>()
//            var alreadyAdded = false
            val dataSharedPreferences = sharedPreferences
//
//            //TODO: Fix this warning, serious warning
//            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
//
//            if (dataSharedPreferences.isNotEmpty()) {
//                listFavoriteCoins = Gson().fromJson(dataSharedPreferences, typeOfT)
//            }
//            if (coin.isFavorite){
//                listFavoriteCoins.forEach {
//                    if (it.name == coin.name) {
//                        alreadyAdded = true
//                    }
//                }
//                if (!alreadyAdded) {
//                    listFavoriteCoins.add(
//                        FavoriteCoin(
//                            id = coin.id,
//                            name = coin.name
//                        )
//                    )
//                    Toast.makeText(
//                        context,
//                        context.getString(R.string.txt_toast_added, coin.name),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            } else {
//                listFavoriteCoins.forEach {
//                    if (it.name == coin.name) {
//                        listFavoriteCoins.remove(it)
//                    }
//                }
//                Toast.makeText(
//                    context,
//                    context.getString(R.string.txt_toast_removed, coin.name),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//            sharedPreferences.FavoriteCoins = Gson().toJson(listFavoriteCoins)
//            cacheRateLimit.removeForKey(sharedPreferences, CACHE_KEY_PRICE_COINS)
        } catch (e: Exception) {
            Timber.e(
                TAG,
                e.message ?: "Something went wrong while saving the list of favorite coins"
            )
        }
    }

    //
    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}