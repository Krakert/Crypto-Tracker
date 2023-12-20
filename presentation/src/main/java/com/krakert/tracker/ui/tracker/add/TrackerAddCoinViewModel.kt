package com.krakert.tracker.ui.tracker.add

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.response.BackendException
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
import com.krakert.tracker.ui.tracker.model.ProblemState.API_LIMIT
import com.krakert.tracker.ui.tracker.model.ProblemState.NO_CONNECTION
import com.krakert.tracker.ui.tracker.model.ProblemState.SERVER
import com.krakert.tracker.ui.tracker.model.ProblemState.SSL
import com.krakert.tracker.ui.tracker.model.ProblemState.UNKNOWN
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
    data class Problem(val exception: ProblemState) : ViewStateAddCoin()
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
            getListCoinsToAdd().collect { flow ->
                flow.onSuccess { success ->
                    mutableStateAdd.emit(
                        Success(listCoinsDisplayMapper.map(success))
                    )
                }.onFailure { exception ->
                    when (exception) {
                        is SSLHandshakeException -> mutableStateAdd.emit(Problem(SSL))

                        is ConnectException -> mutableStateAdd.emit(Problem(NO_CONNECTION))

                        is BackendException -> {
                            when (exception.errorCode) {
                                in 400..499 -> mutableStateAdd.emit(Problem(API_LIMIT))
                                in 500..599 -> mutableStateAdd.emit(Problem(SERVER))
                                else -> mutableStateAdd.emit(Problem(UNKNOWN))
                            }
                        }

                        else -> mutableStateAdd.emit(Problem(UNKNOWN))
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