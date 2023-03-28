package com.krakert.tracker.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.Text
import com.krakert.tracker.ui.tracker.model.ProblemState
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_API_LEVEL
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_BACKGROUND_COLOR_BLACK
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_HEIGHT_DP
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_DEVICE_WIDTH_DP
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_SHOW_BACKGROUND
import com.krakert.tracker.ui.tracker.model.WEAR_PREVIEW_UI_MODE


@Composable
fun ShowProblem(problemState: ProblemState?, onClick: () -> Unit) {
    CenterElement {
        problemState?.icon?.let {
            IconButton(Modifier.size(ButtonDefaults.LargeButtonSize), it) {
                onClick()
            }
        }
        Text(
            modifier = Modifier.padding(all = 8.dp),
            text = problemState?.txt?.let { stringResource(it) }.toString(),
            fontSize = 16.sp,
            textAlign = TextAlign.Center

        )
    }
}

class ProblemsPreviewParameterProvider : PreviewParameterProvider<ProblemState> {
    override val values = sequenceOf(
        ProblemState.UNKNOWN,
        ProblemState.SSL,
        ProblemState.EMPTY,
        ProblemState.API_LIMIT,
        ProblemState.COULD_NOT_LOAD,
        ProblemState.NO_CONNECTION
    )
}

@Preview(
    group = "State",
    widthDp = WEAR_PREVIEW_DEVICE_WIDTH_DP,
    heightDp = WEAR_PREVIEW_DEVICE_HEIGHT_DP,
    apiLevel = WEAR_PREVIEW_API_LEVEL,
    uiMode = WEAR_PREVIEW_UI_MODE,
    backgroundColor = WEAR_PREVIEW_BACKGROUND_COLOR_BLACK,
    showBackground = WEAR_PREVIEW_SHOW_BACKGROUND
)
@Composable
fun PreviewShowProblem( @PreviewParameter(ProblemsPreviewParameterProvider::class) problem: ProblemState
){
    ShowProblem(problem) { }
}