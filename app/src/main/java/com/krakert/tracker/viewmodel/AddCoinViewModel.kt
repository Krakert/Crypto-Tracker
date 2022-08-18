package com.krakert.tracker.viewmodel

//import com.krakert.tracker.repository.CryptoCacheRepository
import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.krakert.tracker.R
import com.krakert.tracker.SharedPreference
import com.krakert.tracker.SharedPreference.FavoriteCoins
import com.krakert.tracker.api.Resource
import com.krakert.tracker.models.Coin
import com.krakert.tracker.models.ListCoins
import com.krakert.tracker.models.FavoriteCoin
import com.krakert.tracker.repository.CryptoApiRepository
import kotlinx.coroutines.launch
import java.lang.reflect.Type

class AddCoinViewModel(application: Application) : AndroidViewModel(application) {

//    private val cryptoCacheRepository: CryptoCacheRepository = CryptoCacheRepository(context)
    private val cryptoApiRepository: CryptoApiRepository = CryptoApiRepository()
    private val context = getApplication<Application>()
    private val sharedPreference = SharedPreference.sharedPreference(context)

    //initialize it with an Empty type of Resource
    private val _httpResource: MutableLiveData<Resource<ListCoins>> = MutableLiveData(Resource.Loading())

    val httpResource: LiveData<Resource<ListCoins>>
        get() = _httpResource

    fun getListCoins() {
        viewModelScope.launch {
            _httpResource.value  = cryptoApiRepository.getListCoins()
        }
    }

//        _viewState.value = ViewStateAddCoin.Success(result.coins)

//        val resultCache = cryptoCacheRepository.getListCoins()
//        println(resultCache)
//        if (resultCache.isEmpty()) {
//            println("Cache empty, AddCoinViewModel")
//            getAndSetData()
//        } else {
//            println("found data in the cache, AddCoinViewModel")
//            val oldDate = LocalDateTime.ofInstant(
//                Instant.ofEpochMilli(resultCache[0].timeStamp),
//                TimeZone.getDefault().toZoneId())
//            val dateNow = LocalDateTime.now()
//            if (ChronoUnit.HOURS.between(oldDate, dateNow) >= 24){
//                println("Data is overdue, and needs updating, AddCoinViewModel")
////                getAndSetData()
//            } else {
//                println("Using cached data")
//                _viewState.value = ViewStateAddCoin.Success(resultCache)
//            }
//        }

//    private suspend fun getAndSetData() {
//        fireBaseRepo.getListCoins().collect {
//            try {
//                CoroutineScope(Dispatchers.IO).launch{
//                    cryptoCacheRepository.setListCoins(it)
//                }
//                _viewState.value = ViewStateAddCoin.Success(it)
//            } catch (e: FirebaseRepository.FireBaseExceptionError) {
//                val errorMsg = "Something went wrong while retrieving the list of coins"
//                Log.e(TAG, e.message ?: errorMsg)
//                _viewState.value = ViewStateAddCoin.Error(e)
//            }
//        }
//    }

    fun addCoinToFavoriteCoins(coin: Coin, context: Context) {
        try {
            var listFavoriteCoins = ArrayList<FavoriteCoin>()
            var alreadyAdded = false
            val dataSharedPreferences = sharedPreference.FavoriteCoins.toString()
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
                sharedPreference.FavoriteCoins = Gson().toJson(listFavoriteCoins)
                Toast.makeText(context, context.getString(R.string.txt_toast_added, coin.name), Toast.LENGTH_SHORT)
                    .show()
            }
        } catch (e: Exception) {
            val errorMsg = "Something went wrong while saving the list of favorite coins"

            Log.e(TAG, e.message ?: errorMsg)
//            _viewState.value = ViewStateAddCoin.Error(e)
        }
    }
}