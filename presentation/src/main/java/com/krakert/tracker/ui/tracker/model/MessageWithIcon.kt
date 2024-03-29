package com.krakert.tracker.ui.tracker.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Dns
import androidx.compose.material.icons.rounded.PlusOne
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Sync
import androidx.compose.material.icons.rounded.Update
import androidx.compose.material.icons.rounded.WifiOff
import androidx.compose.ui.graphics.vector.ImageVector
import com.krakert.tracker.presentation.R

enum class MessageWithIcon(val txt: Int, val icon: ImageVector) {

    EMPTY(R.string.txt_empty_overview, Icons.Rounded.PlusOne),
    NO_CONNECTION(R.string.txt_no_connection, Icons.Rounded.WifiOff),
    COULD_NOT_LOAD(R.string.txt_could_not_load, Icons.Rounded.Sync),
    API_LIMIT(R.string.txt_limit, Icons.Rounded.Update),
    UNKNOWN(R.string.txt_unknown, Icons.Rounded.QuestionMark),
    SSL(R.string.txt_set_time, Icons.Rounded.Schedule),
    SERVER(R.string.txt_server, Icons.Rounded.Dns),
    NO_RESULT(R.string.txt_no_result, Icons.Rounded.Clear),
    END_RESULT(R.string.txt_end_result, Icons.Rounded.Clear)
}