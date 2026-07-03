package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alice.app.data.models.AiUiState
import com.alice.app.data.models.ChatMessage
import com.alice.app.ui.components.AliceInputBar
import com.alice.app.ui.components.ErrorCard
import com.alice.app.ui.components.LoadingBubble
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurface
import com.alice.app.ui.theme.AliceSurfaceVariant
import com.alice.app.ui.theme.AliceTextSecondary
import com.alice.app.viewmodel.AliceViewModel

@Composable
fun AliceChatScreen(
    viewModel: AliceViewModel
) {
    var input by rememberSaveable { mutableStateOf("") }
    val listState = rememberLazyListState()
    val loading = viewModel.uiState == AiUiState.Loading

    LaunchedEffect(viewModel.messages.size, loading) {
        val extraLoadingItem = if (loading) 1 else 0
        val lastIndex = viewModel.messages.lastIndex + extraLoadingItem
        if (lastIndex >= 0) listState.animateScrollToItem(lastIndex)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    text = "Podemos avanzar paso a paso. Escriba una pregunta o pida ayuda con una tarea.",
                    modifier = Modifier.padding(top = 12.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = AliceTextSecondary
                )
            }
            items(viewModel.messages, key = { it.id }) { message ->
                ChatBubble(message = message)
            }
            if (loading) {
                item {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        LoadingBubble(text = "ALICE está pensando…")
                    }
                }
            }
            val error = viewModel.uiState as? AiUiState.Error
            if (error != null) {
                item {
                    ErrorCard(message = error.message)
                }
            }
        }
        AliceInputBar(
            value = input,
            onValueChange = { input = it },
            enabled = !loading,
            onSend = {
                viewModel.sendMessage(input)
                input = ""
            }
        )
    }
}

@Composable
private fun ChatBubble(
    message: ChatMessage
) {
    val alignment = if (message.isFromUser) Alignment.CenterEnd else Alignment.CenterStart
    val container = if (message.isFromUser) AlicePrimary else AliceSurface
    val textColor = if (message.isFromUser) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        Surface(
            color = container,
            shape = RoundedCornerShape(8.dp),
            tonalElevation = if (message.isFromUser) 0.dp else 2.dp,
            modifier = Modifier.fillMaxWidth(0.86f)
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = if (message.isFromUser) "Usted" else "ALICE",
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (message.isFromUser) textColor else AlicePrimary
                )
                Text(
                    text = message.content,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor
                )
            }
        }
    }
}
