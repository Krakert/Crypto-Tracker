package com.krakert.tracker.ui.tracker.detail

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.response.BackendException
import com.krakert.tracker.domain.tracker.GetDetailsCoin
import com.krakert.tracker.domain.tracker.RemoveFavouriteCoin
import com.krakert.tracker.ui.tracker.detail.ViewStateDetails.Loading
import com.krakert.tracker.ui.tracker.detail.ViewStateDetails.Problem
import com.krakert.tracker.ui.tracker.detail.ViewStateDetails.Success
import com.krakert.tracker.ui.tracker.detail.mapper.DetailCoinDisplayMapper
import com.krakert.tracker.ui.tracker.detail.model.DetailCoinDisplay
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

sealed class ViewStateDetails {
    object Loading : ViewStateDetails()
    data class Success(val details: DetailCoinDisplay) : ViewStateDetails()
    data class Problem(val exception: ProblemState) : ViewStateDetails()
}

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val application: Application,
    private val getDetailsCoin: GetDetailsCoin,
    private val removeFavouriteCoin: RemoveFavouriteCoin,
    private val detailCoinDisplayMapper: DetailCoinDisplayMapper
) : ViewModel() {

    private val mutableStateDetail = MutableStateFlow<ViewStateDetails>(Loading)
    val detailsCoin = mutableStateDetail.asStateFlow()
    fun getDetailsByCoinId(coinId: String) {
        viewModelScope.launch {
            getDetailsCoin(coinId).collect { flow ->
                flow.onSuccess { details ->
                    mutableStateDetail.emit(
                        Success(
                            detailCoinDisplayMapper.map(details)
                        )
                    )
                }
                    .onFailure { exception ->
                        when (exception) {
                            is SSLHandshakeException -> mutableStateDetail.emit(Problem(SSL))

                            is ConnectException -> mutableStateDetail.emit(Problem(NO_CONNECTION))

                            is BackendException -> {
                                when (exception.errorCode) {
                                    in 400..499 -> mutableStateDetail.emit(Problem(API_LIMIT))
                                    in 500..599 -> mutableStateDetail.emit(Problem(SERVER))
                                    else -> mutableStateDetail.emit(Problem(UNKNOWN))
                                }
                            }

                            else -> mutableStateDetail.emit(Problem(UNKNOWN))
                        }
                    }
            }
        }
    }

    fun removeCoinFromFavoriteCoins(id: String, name: String) {
        viewModelScope.launch {
            removeFavouriteCoin(id = id, name = name)
        }
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}