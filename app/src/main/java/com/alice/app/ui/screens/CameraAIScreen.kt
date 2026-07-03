package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.alice.app.ui.components.AlicePrimaryButton
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurface
import com.alice.app.ui.theme.AliceTextSecondary

@Composable
fun CameraAIScreen() {
    var message by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            color = AliceSurface,
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 6.dp
        ) {
            Icon(
                imageVector = Icons.Default.CameraAlt,
                contentDescription = null,
                tint = AlicePrimary,
                modifier = Modifier.padding(30.dp)
            )
        }
        Spacer(modifier = Modifier.height(22.dp))
        Text(
            text = "Cámara IA",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "Active la cámara para que ALICE analice su entorno o documentos físicos.",
            modifier = Modifier.padding(top = 10.dp, bottom = 22.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = AliceTextSecondary,
            textAlign = TextAlign.Center
        )
        AlicePrimaryButton(
            text = "Abrir cámara",
            onClick = { message = "Función preparada para futura integración con Gemini Vision." },
            modifier = Modifier.fillMaxWidth()
        )
        if (message.isNotBlank()) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = AliceTextSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}
