package com.krakert.tracker.ui.tracker.add

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class AddCoinViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val application: Application
) : ViewModel() {

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
//    fun toggleFavoriteCoin(coin: Coin, context: Context) {
//        try {
//            var listFavoriteCoins = ArrayList<FavoriteCoin>()
//            var alreadyAdded = false
//            val dataSharedPreferences = sharedPreferences.FavoriteCoins.toString()
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
//        } catch (e: Exception) {
//            Log.e(
//                TAG,
//                e.message ?: "Something went wrong while saving the list of favorite coins"
//            )
//        }
//    }
//
//    fun openSettings() {
//        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        application.applicationContext.startActivity(intent)
//    }
}