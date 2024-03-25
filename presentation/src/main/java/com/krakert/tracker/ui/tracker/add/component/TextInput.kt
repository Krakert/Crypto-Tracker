package com.krakert.tracker.ui.tracker.add.component

import android.app.RemoteInput
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.wear.compose.material.CompactButton
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import androidx.wear.input.RemoteInputIntentHelper
import androidx.wear.input.wearableExtender

@Composable
fun TextInput(
    onClick: (String) -> Unit,
    onResult: (String) -> Unit,
    text: String,
    defaultText: String,
    triggerOnCLick: Boolean = false
) {
    val inputTextKey = "input_text"
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            it.data?.let { data ->
                val results: Bundle = RemoteInput.getResultsFromIntent(data)
                val newInputText: CharSequence? = results.getCharSequence(inputTextKey)
                onResult(newInputText?.toString() ?: "")
                if (triggerOnCLick) {
                    onClick(newInputText?.toString() ?: "")
                }
            }
        }

    val remoteInputs: List<RemoteInput> = listOf(
        RemoteInput.Builder(inputTextKey)
            .setLabel("type in the coin your are looking for")
            .wearableExtender {
                setEmojisAllowed(false)
                setInputActionType(EditorInfo.IME_ACTION_DONE)
            }.build(),
    )

    val intent: Intent = RemoteInputIntentHelper.createActionRemoteInputIntent()
    RemoteInputIntentHelper.putRemoteInputsExtra(intent, remoteInputs)

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(0.85f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text,
                Modifier
                    .weight(1f)
                    .clickable { launcher.launch(intent) })
            CompactButton(
                onClick = {
                    if (text != defaultText) {
                        onClick(text)
                    } else {
                        launcher.launch(intent)
                    }
                },
            ) {
                Icon(
                    imageVector = if (text != defaultText) Icons.Filled.Search else Icons.Filled.Edit,
                    contentDescription = null
                )
            }

        }
    }
}