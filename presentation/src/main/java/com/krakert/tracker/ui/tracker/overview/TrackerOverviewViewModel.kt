package com.krakert.tracker.ui.tracker.overview

import android.app.Application
import android.content.Intent
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.krakert.tracker.domain.tracker.GetFavouriteCoins
import com.krakert.tracker.domain.tracker.GetOverview
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.krakert.tracker.ui.tracker.model.ProblemState.EMPTY
import com.krakert.tracker.ui.tracker.model.ProblemState.NO_CONNECTION
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
    private val application: Application,
    private val getFavouriteCoins: GetFavouriteCoins,
    private val getOverview: GetOverview,
    private val overviewCoinDisplayMapper: OverviewCoinDisplayMapper,
) : ViewModel() {
    private val mutableStateOverview = MutableStateFlow<ViewStateOverview>(Loading)
    val overviewViewState = mutableStateOverview.asStateFlow()

    fun getAllOverviewData() {
        viewModelScope.launch {
            mutableStateOverview.emit(Loading)
            getFavouriteCoins()
                .onSuccess { listFavouriteCoins ->
                    if (listFavouriteCoins.result.isNotEmpty()) {
                        getOverview()
                            .onSuccess { coinOverview ->
                                mutableStateOverview.emit(
                                    Success(
                                        overviewCoinDisplayMapper.map(coinOverview)
                                    )
                                )
                            }
                            .onFailure {
                                when (it) {
                                    is SSLHandshakeException -> {
                                        mutableStateOverview.emit(Problem(SSL))
                                    }

                                    is ConnectException -> {
                                        mutableStateOverview.emit(Problem(NO_CONNECTION))
                                    }

                                    else -> {
                                        mutableStateOverview.emit(Problem(UNKNOWN))
                                    }
                                }
                            }
                    } else {
                        mutableStateOverview.emit(
                            Problem(EMPTY)
                        )
                    }
                }
        }
    }

    fun openSettings() {
        val intent = Intent(Settings.ACTION_DATE_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        application.applicationContext.startActivity(intent)
    }
}