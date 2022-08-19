package com.krakert.tracker.ui.viewmodel

//import com.krakert.tracker.repository.CryptoCacheRepository
import android.content.ContentValues
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.AmountDaysTracking
import com.krakert.tracker.SharedPreference.Currency
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.models.FavoriteCoins
import com.krakert.tracker.repository.CryptoApiRepository
import com.krakert.tracker.ui.state.ViewStateDataCoins
import com.krakert.tracker.ui.state.ViewStateOverview
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.reflect.Type


class OverviewViewModel(context: Context) : ViewModel() {
    private val cryptoApiRepository: CryptoApiRepository = CryptoApiRepository()
//    private val cryptoCacheRepository: CryptoCacheRepository = CryptoCacheRepository(context)
    private val sharedPreference = SharedPreference.sharedPreference(context)

    private val _viewState = MutableStateFlow<ViewStateOverview>(ViewStateOverview.Loading)
    val favoriteCoins = _viewState.asStateFlow()
    // StateFlow
    private val _viewStateDataCoin = MutableStateFlow<ViewStateDataCoins>(ViewStateDataCoins.Loading)
    val dataCoin = _viewStateDataCoin.asStateFlow()

    // Live data
//    private val _viewStateDataCoin: MutableLiveData<Resource<MutableMap<String, MutableMap<String, Any>>>> = MutableLiveData(Resource.Empty())

//    val dataCoin: LiveData<Resource<MutableMap<String, MutableMap<String, Any>>>>
//        get() = _viewStateDataCoin

    fun getFavoriteCoins() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val dataSharedPreference = sharedPreference.FavoriteCoins.toString()
            val typeOfT: Type = object : TypeToken<FavoriteCoins>() {}.type
            if (dataSharedPreference.isBlank()) {
                _viewState.value = ViewStateOverview.Empty
            } else {
                val listFavoriteCoins: FavoriteCoins =
                    Gson().fromJson(dataSharedPreference, typeOfT)
//                println("size of the list of favorites is : ${listFavoriteCoins.size}")
                _viewState.value = ViewStateOverview.Success(listFavoriteCoins)
            }
        } catch (e: Exception) {
            val errorMsg = "Something went wrong while retrieving the list of coins"

            Log.e(ContentValues.TAG, e.message ?: errorMsg)
            _viewState.value = ViewStateOverview.Error(e)
        }
    }

//    fun getPriceByListCoinIds(listCoins: FavoriteCoins) {
//        viewModelScope.launch {
//            val idCoins = arrayListOf<String>()
//            listCoins.forEach {
//                idCoins.add(it.id)
//            }
//            val result = cryptoApiRepository.getPriceCoins(
//                idCoins = idCoins.joinToString(","),
//                currency = sharedPreference.Currency.toString())
//            Log.i("API CALL: getPriceCoins", result.data.toString())
//        }
//    }

    fun getAllDataByListCoinIds(listCoins: FavoriteCoins){
        viewModelScope.launch {
            val idCoins = arrayListOf<String>()
            val time = System.currentTimeMillis()
            listCoins.forEach {
                idCoins.add(it.id)
            }
            val mapData = cryptoApiRepository.getPriceCoins(
                idCoins = idCoins.joinToString(","),
                currency = sharedPreference.Currency.toString()
            )
            Log.i("API CALL: getPriceCoins", mapData.data.toString())

            listCoins.forEach { index ->
                val result = cryptoApiRepository.getHistoryByCoinId(
                    coinId = index.id,
                    currency = sharedPreference.Currency.toString(),
                    days = sharedPreference.AmountDaysTracking.toString()
                )
                result.data?.prices?.let { mapData.data?.get(index.id)?.put("market_chart", it) }
                mapData.data?.get(index.id)?.put("time_stamp", time)
                Log.i("API CALL: getHistoryByCoinId", result.toString())
            }
            _viewStateDataCoin.value = ViewStateDataCoins.Success(mapData)
        }
    }

//`        viewModelScope.launch(Dispatchers.IO) {
//            val resultCache = cryptoCacheRepository.getListDataCoins()
//            if (resultCache.isEmpty()) {
//                println("Cache empty, OverviewViewModel")
//                getAndSetData(listResult)
//            } else {
//                println("found data in the cache, OverviewViewModel")
//                val oldDate = LocalDateTime.ofInstant(
//                    Instant.ofEpochMilli(resultCache[0].timeStamp),
//                    TimeZone.getDefault().toZoneId()
//                )
//                val dateNow = LocalDateTime.now()
//                println("Date now: $dateNow, date data: $oldDate")
//                println("time between: ${ChronoUnit.MINUTES.between(oldDate, dateNow)}, Max: ${sharedPreference.MinutesCache}")
//                if (ChronoUnit.MINUTES.between(oldDate, dateNow) >= sharedPreference.MinutesCache){
//                    println("Data is overdue, and needs updating, OverviewViewModel")
////                    getAndSetData(listResult)
//                } else {
//                    println("size listResult: ${listResult.size}")
//                    println("size resultCache: ${resultCache.size}")
//                    println("checking if cached data is usable, OverviewViewModel")
//                    if (listResult.size != resultCache.size) {
//                        println("data out of sync with favourites, refreshing")
////                        getAndSetData(listResult)
//                    } else {
//                        println("data in sync with favourites")
//                        _viewStateDataCoin.value = ViewStateDataCoins.Success(resultCache)
//                    }
//                }
//            }
//        }

//        val data = arrayListOf<DataCoinChart>()
//        val time = System.currentTimeMillis()
//        try {
//            listResult.forEach { index ->
//                data.add(
//                    DataCoinChart(
//                        id = index.id,
//                        history =
//                    )
//                    DataCoin(
//                        id = index.id,
//                        history = coinCapApiService.getHistoryByCoinId(index.id),
//                        currentPrice = coinCapApiService.getLatestPriceByCoinId(index.id),
//                        timeStamp = time
//                    )
//                )
//            }
//            println("Got data for ${data.size}, set time add $time")
//            cryptoCacheRepository.setListDataCoins(dataCoins = data)
//            _viewStateDataCoin.value = ViewStateDataCoins.Success(data)
//        } catch (e: Exception) {
//            val errorMsg = "Something went wrong while retrieving data"
//
//            Log.e(ContentValues.TAG, e.message ?: errorMsg)
//            _viewStateDataCoin.value = ViewStateDataCoins.Error(e)
//        }

//    suspend fun getAndSetData(listResult: List<Coin>) {
//        val data = arrayListOf<DataCoin>()
//        val time = System.currentTimeMillis()
//        try {
//            listResult.forEach { index ->
//                data.add(
//                    DataCoin(
//                        id = index.idCoin,
//                        history = coinGeckoRepo.getHistoryByCoinId(index.idCoin),
//                        currentPrice = coinGeckoRepo.getLatestPriceByCoinId(index.idCoin),
//                        timeStamp = time
//                    )
//                )
//            }
//            println("Got data for ${data.size}, set time add $time")
//            cryptoCacheRepository.setListDataCoins(dataCoins = data)
//            _viewStateDataCoin.value = ViewStateDataCoins.Success(data)
//        } catch (e: CoinGeckoRepository.CoinGeckoExceptionError) {
//            val errorMsg = "Something went wrong while retrieving data"
//
//            Log.e(ContentValues.TAG, e.message ?: errorMsg)
//            _viewStateDataCoin.value = ViewStateDataCoins.Error(e)
//        }
//    }
}


