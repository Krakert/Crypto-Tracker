package com.krakert.tracker.models.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.krakert.tracker.R

enum class ProblemState(val txt: Int, val icon: ImageVector) {

    EMPTY(R.string.txt_empty_overview, Icons.Rounded.PlusOne),
    NO_CONNECTION(R.string.txt_no_connection, Icons.Rounded.WifiOff),
    COULD_NOT_LOAD(R.string.txt_could_not_load, Icons.Rounded.Sync),
    API_LIMIT(R.string.txt_limit, Icons.Rounded.Update),
    UNKNOWN(R.string.txt_unknown, Icons.Rounded.QuestionMark),
    SSL(R.string.txt_set_time, Icons.Rounded.Schedule)
}