package com.krakert.tracker.ui.tracker.overview

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.response.BackendException
import com.krakert.tracker.domain.tracker.CheckFavouriteCoins
import com.krakert.tracker.domain.tracker.GetOverview
import com.krakert.tracker.ui.components.ContentState
import com.krakert.tracker.ui.components.OnDisplay
import com.krakert.tracker.ui.components.OnError
import com.krakert.tracker.ui.components.OnLoading
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.API_LIMIT
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.EMPTY
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.NO_CONNECTION
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.SERVER
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.SSL
import com.krakert.tracker.ui.tracker.model.MessageWithIcon.UNKNOWN
import com.krakert.tracker.ui.tracker.overview.mapper.OverviewCoinDisplayMapper
import com.krakert.tracker.ui.tracker.overview.model.OverviewCoinDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

@HiltViewModel
class OverviewViewModel
@Inject constructor(
    private val application: Application,
    private val checkFavouriteCoins: CheckFavouriteCoins,
    private val getOverview: GetOverview,
    private val overviewCoinDisplayMapper: OverviewCoinDisplayMapper,
) : ViewModel() {
    private val mutableStateOverview = MutableStateFlow<ContentState<OverviewCoinDisplay>>(OnLoading)
    val overviewViewState = mutableStateOverview.asStateFlow()

    fun getAllOverviewData() {
        viewModelScope.launch {
            if (!checkFavouriteCoins()) {
                getOverview().collect { flow ->
                    flow.onSuccess { coinOverview ->
                        mutableStateOverview.emit(OnDisplay(overviewCoinDisplayMapper.map(coinOverview)))
                    }
                        .onFailure { exception ->
                            when (exception) {
                                is SSLHandshakeException -> mutableStateOverview.emit(OnError(SSL))

                                is ConnectException -> mutableStateOverview.emit(OnError(NO_CONNECTION))

                                is BackendException -> {
                                    when (exception.errorCode) {
                                        in 400..499 -> mutableStateOverview.emit(OnError(API_LIMIT))
                                        in 500..599 -> mutableStateOverview.emit(OnError(SERVER))
                                        else -> mutableStateOverview.emit(OnError(UNKNOWN))
                                    }
                                }
                                else -> mutableStateOverview.emit(OnError(UNKNOWN))
                            }
                        }
                }
            } else {
                mutableStateOverview.emit(OnError(EMPTY))
            }
        }
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}