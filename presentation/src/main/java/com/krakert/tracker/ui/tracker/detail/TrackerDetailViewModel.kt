package com.krakert.tracker.ui.tracker.detail

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//sealed class ViewStateDetailsCoins {
//    object Loading : ViewStateDetailsCoins()
//    data class Success(val details: DetailsCoin) : ViewStateDetailsCoins()
//    data class Problem(val exception: ProblemState?) : ViewStateDetailsCoins()
//}

@HiltViewModel
class DetailsViewModel @Inject constructor(
//    private val CryptoRepository: CryptoRepository,
    private val sharedPreferences: SharedPreferences,
    private val application: Application,
) : ViewModel() {
//
//    lateinit var coinId: String
//
//    private val _viewState = MutableStateFlow<ViewStateDetailsCoins>(ViewStateDetailsCoins.Loading)
//    val detailsCoin = _viewState.asStateFlow()
//
//    fun getDetailsCoinByCoinId() {
//        viewModelScope.launch {
//            if (!CryptoRepository.isOnline()) {
//                _viewState.value = ViewStateDetailsCoins.Problem(ProblemState.NO_CONNECTION)
//            } else {
//                CryptoRepository.getDetailsCoinByCoinId(
//                    coinId = coinId
//                ).collect { result ->
//                    when (result) {
//                        is Resource.Success -> {
//                            _viewState.value = ViewStateDetailsCoins.Success(
//                                DetailsCoin(
//                                    name = result.data?.name.toString(),
//                                    image = result.data?.image,
//                                    currentPrice = result.data?.marketData?.currentPrice?.get(sharedPreferences.Currency).toString(),
//                                    priceChangePercentage24h = result.data?.marketData?.priceChangePercentage24h?: 0.0,
//                                    priceChangePercentage7d = result.data?.marketData?.priceChangePercentage7d ?: 0.0,
//                                    circulatingSupply = result.data?.marketData?.circulatingSupply ?: 0.0,
//                                    high24h = result.data?.marketData?.high24h?.get(sharedPreferences.Currency) ?: 0.0,
//                                    low24h = result.data?.marketData?.low24h?.get(sharedPreferences.Currency) ?: 0.0,
//                                    marketCap = result.data?.marketData?.marketCap?.get(sharedPreferences.Currency) ?: 0.0,
//                                    marketCapChangePercentage24h = result.data?.marketData?.marketCapChangePercentage24h ?: 0.0
//                                )
//                            )
//                        }
//                        is Resource.Error -> {
//                            _viewState.value = ViewStateDetailsCoins.Problem(result.state)
//                        }
//                        is Resource.Loading -> {
//                            _viewState.value = ViewStateDetailsCoins.Loading
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun removeCoinFromFavoriteCoins(coinId: String) {
//        try {
//            val dataSharedPreferences = sharedPreferences.FavoriteCoins.toString()
//            val favoriteCoin = sharedPreferences.FavoriteCoin
//            val typeOfT: Type = object : TypeToken<ArrayList<FavoriteCoin>>() {}.type
//            val listFavoriteCoins: ArrayList<FavoriteCoin> = Gson().fromJson(dataSharedPreferences, typeOfT)
//
//            var indexToRemove: Int? = null
//                listFavoriteCoins.forEach {
//                    if (it.id == coinId.lowercase())
//                        indexToRemove = listFavoriteCoins.indexOf(it)
//                }
//
//            indexToRemove?.let { listFavoriteCoins.removeAt(it) }
//
//            sharedPreferences.FavoriteCoins = Gson().toJson(listFavoriteCoins)
//
//            if (favoriteCoin == coinId) {
//                sharedPreferences.FavoriteCoin = ""
//            }
//
//        } catch (e: Exception) {
//            Log.e(ContentValues.TAG, e.message ?: "Something went wrong while deleting a coins")
//        }
//    }
//
//    fun openSettings() {
//        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//        application.applicationContext.startActivity(intent)
//    }
}