package com.alice.app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Route
import androidx.compose.material.icons.filled.TaskAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.alice.app.navigation.Routes
import com.alice.app.ui.components.FeatureCard
import com.alice.app.ui.components.SectionTitle
import com.alice.app.ui.theme.AlicePrimary
import com.alice.app.ui.theme.AliceSurface
import com.alice.app.ui.theme.AliceTextSecondary

private data class HomeFeature(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val route: String
)

@Composable
fun HomeScreen(
    onNavigate: (String) -> Unit
) {
    val features = listOf(
        HomeFeature(Icons.Default.AutoAwesome, "Chat con ALICE", "Pregunte, estudie y resuelva dudas.", Routes.CHAT),
        HomeFeature(Icons.Default.Lightbulb, "Explicar tema", "Reciba una explicación clara y ordenada.", Routes.CHAT),
        HomeFeature(Icons.Default.CreditCard, "Crear flashcards", "Genere tarjetas de repaso con IA.", Routes.FLASHCARDS),
        HomeFeature(Icons.Default.Route, "Plan de estudio", "Organice sesiones por duración.", Routes.STUDY_PLAN),
        HomeFeature(Icons.AutoMirrored.Filled.Article, "Resumir documento", "Pegue texto y obtenga un resumen.", Routes.DOCUMENTS),
        HomeFeature(Icons.Default.CameraAlt, "Cámara IA", "Pantalla preparada para visión.", Routes.CAMERA_AI),
        HomeFeature(Icons.Default.TaskAlt, "Organizar tareas", "Priorice hábitos y pendientes.", Routes.TASKS)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = "Buenas tardes, Rodrigo. ¿Qué desea hacer hoy?",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors(containerColor = AliceSurface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "ALICE",
                    style = MaterialTheme.typography.titleLarge,
                    color = AlicePrimary,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Estoy lista para ayudarle con sus estudios, documentos y proyectos.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = AliceTextSecondary
                )
            }
        }
        SectionTitle(text = "Centro de comandos")
        FeatureGrid(features = features, onNavigate = onNavigate)
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun FeatureGrid(
    features: List<HomeFeature>,
    onNavigate: (String) -> Unit
) {
    BoxWithConstraints {
        val columns = if (maxWidth < 430.dp) 1 else 2
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            features.chunked(columns).forEach { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    rowItems.forEach { feature ->
                        FeatureCard(
                            icon = feature.icon,
                            title = feature.title,
                            description = feature.description,
                            onClick = { onNavigate(feature.route) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                    repeat(columns - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
