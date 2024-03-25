package com.krakert.tracker.ui.components

import com.krakert.tracker.ui.tracker.model.MessageWithIcon

sealed interface ContentState<out T>
class OnDisplay<out T>(val display: T) : ContentState<T>
data object OnLoading : ContentState<Nothing>
class OnError(val errorDisplay: MessageWithIcon) : ContentState<Nothing>