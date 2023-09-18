package com.krakert.tracker.ui.tracker.add

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.tracker.AddFavouriteCoin
import com.krakert.tracker.domain.tracker.GetListCoinsToAdd
import com.krakert.tracker.domain.tracker.RemoveFavouriteCoin
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
    private val application: Application,
    private val getListCoinsToAdd: GetListCoinsToAdd,
    private val addFavouriteCoin: AddFavouriteCoin,
    private val removeFavouriteCoin: RemoveFavouriteCoin,
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
                when (it) {
                    is SSLHandshakeException -> {
                        mutableStateAdd.emit(Problem(ProblemState.SSL))
                    }

                    is ConnectException -> {
                        mutableStateAdd.emit(Problem(ProblemState.NO_CONNECTION))
                    }

                    else -> {
                        mutableStateAdd.emit(Problem(ProblemState.UNKNOWN))
                    }
                }
            }
        }
    }

    fun toggleFavoriteCoin(coin: ListCoinsItemDisplay) {
        viewModelScope.launch {
            if (!coin.isFavourite) {
                removeFavouriteCoin(id = coin.id, name = coin.name)
            } else {
                addFavouriteCoin(id = coin.id, name = coin.name)
            }
        }
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}