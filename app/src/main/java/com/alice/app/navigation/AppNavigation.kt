package com.alice.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.alice.app.ui.components.AliceTopBar
import com.alice.app.ui.screens.AliceChatScreen
import com.alice.app.ui.screens.CameraAIScreen
import com.alice.app.ui.screens.DocumentsScreen
import com.alice.app.ui.screens.FlashcardsScreen
import com.alice.app.ui.screens.HomeScreen
import com.alice.app.ui.screens.LoginScreen
import com.alice.app.ui.screens.ProfileScreen
import com.alice.app.ui.screens.StudyPlanScreen
import com.alice.app.ui.screens.TasksScreen
import com.alice.app.viewmodel.AliceViewModel
import com.alice.app.viewmodel.DocumentViewModel
import com.alice.app.viewmodel.FlashcardViewModel
import com.alice.app.viewmodel.StudyPlanViewModel
import com.alice.app.viewmodel.TaskViewModel

private data class BottomDestination(
    val route: String,
    val title: String,
    val icon: ImageVector
)

private val bottomDestinations = listOf(
    BottomDestination(Routes.HOME, "Home", Icons.Default.Home),
    BottomDestination(Routes.CHAT, "Chat", Icons.Default.AutoAwesome),
    BottomDestination(Routes.DOCUMENTS, "Documentos", Icons.AutoMirrored.Filled.Article),
    BottomDestination(Routes.FLASHCARDS, "Flashcards", Icons.Default.CreditCard),
    BottomDestination(Routes.TASKS, "Tareas", Icons.Default.CheckCircle),
    BottomDestination(Routes.PROFILE, "Perfil", Icons.Default.Person)
)

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val showAppChrome = currentRoute != null && currentRoute != Routes.LOGIN
    val isBottomRoute = bottomDestinations.any { it.route == currentRoute }

    // Navegación principal de la app.
    Scaffold(
        topBar = {
            if (showAppChrome) {
                AliceTopBar(
                    title = titleForRoute(currentRoute),
                    canGoBack = !isBottomRoute,
                    onBackClick = { navController.popBackStack() }
                )
            }
        },
        bottomBar = {
            if (showAppChrome) {
                NavigationBar {
                    bottomDestinations.forEach { destination ->
                        val selected = backStackEntry?.destination?.hierarchy
                            ?.any { it.route == destination.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(destination.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = destination.icon,
                                    contentDescription = destination.title
                                )
                            },
                            label = { Text(destination.title) }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.LOGIN) {
                LoginScreen(
                    onEnterApp = {
                        navController.navigate(Routes.HOME) {
                            popUpTo(Routes.LOGIN) { inclusive = true }
                        }
                    }
                )
            }
            composable(Routes.HOME) {
                HomeScreen(onNavigate = navController::navigate)
            }
            composable(Routes.CHAT) {
                val viewModel: AliceViewModel = viewModel()
                AliceChatScreen(viewModel = viewModel)
            }
            composable(Routes.DOCUMENTS) {
                val viewModel: DocumentViewModel = viewModel()
                DocumentsScreen(viewModel = viewModel)
            }
            composable(Routes.FLASHCARDS) {
                val viewModel: FlashcardViewModel = viewModel()
                FlashcardsScreen(viewModel = viewModel)
            }
            composable(Routes.TASKS) {
                val viewModel: TaskViewModel = viewModel()
                TasksScreen(viewModel = viewModel)
            }
            composable(Routes.PROFILE) {
                ProfileScreen()
            }
            composable(Routes.STUDY_PLAN) {
                val viewModel: StudyPlanViewModel = viewModel()
                StudyPlanScreen(viewModel = viewModel)
            }
            composable(Routes.CAMERA_AI) {
                CameraAIScreen()
            }
        }
    }
}

private fun titleForRoute(route: String?): String {
    return when (route) {
        Routes.HOME -> "ALICE"
        Routes.CHAT -> "Chat con ALICE"
        Routes.DOCUMENTS -> "Documentos"
        Routes.FLASHCARDS -> "Flashcards"
        Routes.TASKS -> "Tareas"
        Routes.PROFILE -> "Perfil"
        Routes.STUDY_PLAN -> "Plan de estudio"
        Routes.CAMERA_AI -> "Cámara IA"
        else -> "ALICE"
    }
}
