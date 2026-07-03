package com.alice.app.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurface
import com.alice.app.ui.theme.AliceSurfaceVariant
import com.alice.app.ui.theme.AliceTextSecondary

@Composable
fun AliceInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = AliceSurface,
        tonalElevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            IconButton(onClick = {}, enabled = enabled) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = "Micrófono simulado",
                    tint = AliceTextSecondary
                )
            }
            IconButton(onClick = {}, enabled = enabled) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Cámara simulada",
                    tint = AliceTextSecondary
                )
            }
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.weight(1f),
                enabled = enabled,
                placeholder = { Text("Escriba a ALICE") },
                singleLine = false,
                maxLines = 4,
                shape = RoundedCornerShape(8.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AlicePrimary,
                    unfocusedBorderColor = AliceSurfaceVariant,
                    focusedContainerColor = AliceSurfaceVariant,
                    unfocusedContainerColor = AliceSurfaceVariant,
                    cursorColor = AlicePrimary
                )
            )
            IconButton(
                onClick = onSend,
                enabled = enabled && value.isNotBlank()
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Enviar",
                    tint = if (value.isNotBlank()) AlicePrimary else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
