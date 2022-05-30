package com.krakert.tracker.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class DetailsViewModel  : ViewModel() {
    init {
        getDetailsCoin()
    }

    fun getDetailsCoin() = viewModelScope.launch {

    }
}