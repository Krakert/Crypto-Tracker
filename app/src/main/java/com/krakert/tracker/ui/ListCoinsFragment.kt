package com.krakert.tracker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cached
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.wear.compose.material.*
import com.krakert.tracker.R
import com.krakert.tracker.model.Coin
import com.krakert.tracker.state.ViewState
import com.krakert.tracker.viewmodel.ListCoinsViewModel


class ListCoinsFragment : Fragment() {

    private val viewModel: ListCoinsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                ColumnDemo(viewModel)
            }
        }
    }

    @Composable
    private fun ColumnDemo(viewModel: ListCoinsViewModel) {

        val scrollState = rememberScalingLazyListState()
        Scaffold(
            timeText = {
                if (!scrollState.isScrollInProgress) {
                    TimeText()
                }
            },
            vignette = {
                // Only show a Vignette for scrollable screens. This code lab only has one screen,
                // which is scrollable, so we show it all the time.
                Vignette(vignettePosition = VignettePosition.TopAndBottom)
            },
            positionIndicator = {
                PositionIndicator(
                    scalingLazyListState = scrollState
                )
            }
        ) {
            when (val listResult = viewModel.listCoins.collectAsState().value) {
                ViewState.Empty -> {
                    ShowIncorrectState(R.string.txt_toast_reload)
                }
                is ViewState.Error -> {
                    ShowIncorrectState(R.string.txt_toast_error)
                }
                ViewState.Loading -> {
                    Loading()
                }
                is ViewState.Success -> {
//                    Loading()
//                    ShowIncorrectState(R.string.txt_toast_error)
                    listResult.coins.Coins?.let { ShowList(scrollState, it) }
                }
            }

        }
    }

    @Composable
    private fun ShowIncorrectState(@StringRes text: Int) {
        CenterElement {
            IconButton(Icons.Rounded.Cached) {
                viewModel.getListCoins()
                Toast.makeText(context, getString(text), Toast.LENGTH_SHORT).show()
            }
            Text(
                text = stringResource(text),
                modifier = Modifier.padding(top = 8.dp),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary,
            )
        }
    }

    @Composable
    private fun ShowList(scrollState: ScalingLazyListState, listResult: List<Coin>) {
        ScalingLazyColumn(
            modifier = Modifier
                .fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 32.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 32.dp
                ),
            verticalArrangement = Arrangement.Center,
            autoCentering = AutoCenteringParams(itemIndex = 0),
            state = scrollState
        ) {
            items(listResult.size) { index ->
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Chip(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        onClick = { /* ... */ },
                        label = {
                            Text(
                                text = listResult[index].name.toString(),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = Icons.Rounded.Phone,
                                contentDescription = "triggers meditation action",
                                modifier = Modifier
                                    .size(24.dp)
                                    .wrapContentSize(align = Alignment.Center)
                            )
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun Loading() {
        CenterElement{
                CircularProgressIndicator(modifier = Modifier.size(75.dp))
        }
    }

    @Composable
    private fun CenterElement(content: @Composable () -> Unit) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            content()
        }
    }

    @Composable
    private fun IconButton(imageVector: ImageVector, onClick: () -> Unit) {
        // Button
        Button(
            modifier = Modifier.size(ButtonDefaults.LargeButtonSize),
            onClick = onClick,
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = "triggers phone action",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center)
            )
        }
    }
}