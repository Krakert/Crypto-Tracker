package com.krakert.tracker.ui.tracker.overview

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.response.BackendException
import com.krakert.tracker.domain.tracker.CheckFavouriteCoins
import com.krakert.tracker.domain.tracker.GetOverview
import com.krakert.tracker.domain.tracker.PreferencesRepository
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.krakert.tracker.ui.tracker.model.ProblemState.API_LIMIT
import com.krakert.tracker.ui.tracker.model.ProblemState.EMPTY
import com.krakert.tracker.ui.tracker.model.ProblemState.NO_CONNECTION
import com.krakert.tracker.ui.tracker.model.ProblemState.SERVER
import com.krakert.tracker.ui.tracker.model.ProblemState.SSL
import com.krakert.tracker.ui.tracker.model.ProblemState.UNKNOWN
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.Loading
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.Problem
import com.krakert.tracker.ui.tracker.overview.ViewStateOverview.Success
import com.krakert.tracker.ui.tracker.overview.mapper.OverviewCoinDisplayMapper
import com.krakert.tracker.ui.tracker.overview.model.OverviewCoinDisplay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

sealed class ViewStateOverview {
    // Represents different states for the overview screen
    object Loading : ViewStateOverview()
    data class Success(val data: OverviewCoinDisplay) : ViewStateOverview()
    data class Problem(val exception: ProblemState?) : ViewStateOverview()
}

@HiltViewModel
class OverviewViewModel
@Inject constructor(
    private val repository: PreferencesRepository,
    private val application: Application,
    private val checkFavouriteCoins: CheckFavouriteCoins,
    private val getOverview: GetOverview,
    private val overviewCoinDisplayMapper: OverviewCoinDisplayMapper,
) : ViewModel() {
    private val mutableStateOverview = MutableStateFlow<ViewStateOverview>(Loading)
    val overviewViewState = mutableStateOverview.asStateFlow()

    fun getAllOverviewData() {
        viewModelScope.launch {
            if (!checkFavouriteCoins()) {
                getOverview().collect { flow ->
                    flow.onSuccess { coinOverview ->
                        mutableStateOverview.emit(Success(overviewCoinDisplayMapper.map(coinOverview)))
                    }
                        .onFailure { exception ->
                            when (exception) {
                                is SSLHandshakeException -> mutableStateOverview.emit(Problem(SSL))

                                is ConnectException -> mutableStateOverview.emit(Problem(NO_CONNECTION))

                                is BackendException -> {
                                    when (exception.errorCode) {
                                        in 400..499 -> mutableStateOverview.emit(Problem(API_LIMIT))
                                        in 500..599 -> mutableStateOverview.emit(Problem(SERVER))
                                        else -> mutableStateOverview.emit(Problem(UNKNOWN))
                                    }
                                }
                                else -> mutableStateOverview.emit(Problem(UNKNOWN))
                            }
                        }
                }
            } else {
                mutableStateOverview.emit(Problem(EMPTY))
            }
        }
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}