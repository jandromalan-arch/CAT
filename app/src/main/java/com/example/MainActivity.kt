package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.model.GradeLevel
import com.example.ui.components.BottomNavBar
import com.example.ui.components.ProfileDialog
import com.example.ui.components.TopNavBar
import com.example.ui.screens.*
import com.example.ui.theme.CatMasterTheme
import com.example.viewmodel.CatViewModel
import com.example.viewmodel.NavigationTab
import com.example.viewmodel.ThemeMode

class MainActivity : ComponentActivity() {

    private val viewModel: CatViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val snackbarHostState = remember { SnackbarHostState() }

            // Theme calculation
            val isDark = when (uiState.themeMode) {
                ThemeMode.SYSTEM -> isSystemInDarkTheme()
                ThemeMode.DARK -> true
                ThemeMode.LIGHT -> false
            }

            var showProfileDialog by remember { mutableStateOf(false) }

            // Toast / Snackbar observer
            LaunchedEffect(uiState.toastMessage) {
                uiState.toastMessage?.let { msg ->
                    snackbarHostState.showSnackbar(
                        message = msg,
                        duration = SnackbarDuration.Short
                    )
                    viewModel.clearToast()
                }
            }

            // Back handler: return to Dashboard if on sub-tab, or dismiss open dialogs
            BackHandler(enabled = uiState.currentTab != NavigationTab.DASHBOARD || uiState.quizSession.isActive || uiState.activeModuleDetail != null || uiState.activeVideoTutorial != null) {
                when {
                    uiState.quizSession.isActive -> viewModel.exitQuiz()
                    uiState.activeModuleDetail != null -> viewModel.openModuleDetail(null)
                    uiState.activeVideoTutorial != null -> viewModel.openVideoTutorial(null)
                    uiState.currentTab != NavigationTab.DASHBOARD -> viewModel.selectTab(NavigationTab.DASHBOARD)
                }
            }

            CatMasterTheme(darkTheme = isDark) {
                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    topBar = {
                        TopNavBar(
                            uiState = uiState,
                            onGradeSelected = { viewModel.selectGrade(it) },
                            onTermSelected = { viewModel.selectTerm(it) },
                            onToggleTheme = {
                                val nextMode = if (isDark) ThemeMode.LIGHT else ThemeMode.DARK
                                viewModel.setThemeMode(nextMode)
                            },
                            onProfileClick = { showProfileDialog = true }
                        )
                    },
                    bottomBar = {
                        BottomNavBar(
                            currentTab = uiState.currentTab,
                            onTabSelected = { viewModel.selectTab(it) }
                        )
                    },
                    contentWindowInsets = WindowInsets.safeDrawing,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        when (uiState.currentTab) {
                            NavigationTab.DASHBOARD -> {
                                DashboardScreen(
                                    uiState = uiState,
                                    onNavigateTab = { viewModel.selectTab(it) },
                                    onStartExamDrill = { viewModel.startQuiz(GradeLevel.GRADE_12, null) },
                                    onPracticeWeakArea = { viewModel.startQuiz(uiState.selectedGrade, uiState.selectedTerm) }
                                )
                            }
                            NavigationTab.CURRICULUM -> {
                                CurriculumScreen(
                                    uiState = uiState,
                                    viewModel = viewModel,
                                    onOpenVideo = { videoId ->
                                        val video = com.example.data.CatCurriculumData.videoTutorials.find { it.id == videoId }
                                        viewModel.openVideoTutorial(video)
                                        viewModel.selectTab(NavigationTab.VIDEOS)
                                    }
                                )
                            }
                            NavigationTab.ASSESSMENTS -> {
                                AssessmentScreen(
                                    uiState = uiState,
                                    viewModel = viewModel
                                )
                            }
                            NavigationTab.VIDEOS -> {
                                VideoVaultScreen(
                                    uiState = uiState,
                                    viewModel = viewModel
                                )
                            }
                            NavigationTab.FORUM -> {
                                PeerForumScreen(
                                    uiState = uiState,
                                    viewModel = viewModel
                                )
                            }
                            NavigationTab.GUIDES -> {
                                StudyGuidesScreen(
                                    uiState = uiState
                                )
                            }
                        }
                    }
                }

                if (showProfileDialog) {
                    ProfileDialog(
                        uiState = uiState,
                        onDismiss = { showProfileDialog = false },
                        onSelectTheme = { viewModel.setThemeMode(it) }
                    )
                }
            }
        }
    }
}
