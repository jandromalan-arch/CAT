package com.example.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.data.CatCurriculumData
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class NavigationTab(val label: String) {
    DASHBOARD("Home"),
    CURRICULUM("Curriculum"),
    ASSESSMENTS("Quizzes"),
    VIDEOS("Video Vault"),
    FORUM("Peer Forum"),
    GUIDES("Study Guides")
}

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

data class QuizSessionState(
    val isActive: Boolean = false,
    val questions: List<QuizQuestion> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val isAnswerRevealed: Boolean = false,
    val score: Int = 0,
    val isFinished: Boolean = false
)

data class UserSession(
    val isSignedIn: Boolean = true,
    val name: String = "Jandro Malan",
    val email: String = "jandromalan@gmail.com",
    val school: String = "Pretoria High School",
    val selectedGrade: GradeLevel = GradeLevel.GRADE_12
)

data class CatUiState(
    val currentTab: NavigationTab = NavigationTab.DASHBOARD,
    val selectedGrade: GradeLevel = GradeLevel.GRADE_12,
    val selectedTerm: CurriculumTerm = CurriculumTerm.TERM_1,
    val selectedStrandFilter: SubjectStrand? = null,
    val searchQuery: String = "",
    val activeModuleDetail: CurriculumModule? = null,
    val activeVideoTutorial: VideoTutorial? = null,
    val quizSession: QuizSessionState = QuizSessionState(),
    val stats: LearnerStats = LearnerStats(),
    val forumThreads: List<ForumThread> = CatCurriculumData.forumThreads,
    val selectedForumThread: ForumThread? = null,
    val isAskingQuestion: Boolean = false,
    val userSession: UserSession = UserSession(),
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val toastMessage: String? = null
)

class CatViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("cat_master_prefs", Context.MODE_PRIVATE)

    private val _uiState = MutableStateFlow(
        CatUiState(
            stats = LearnerStats(
                totalXp = prefs.getInt("total_xp", 480),
                currentStreakDays = prefs.getInt("streak_days", 6),
                questionsAnswered = prefs.getInt("questions_answered", 38),
                correctAnswers = prefs.getInt("correct_answers", 33),
                completedModuleIds = prefs.getStringSet("completed_modules", setOf("gr12_t1_vlookup", "gr12_t1_sys_tech")) ?: emptySet()
            )
        )
    )
    val uiState: StateFlow<CatUiState> = _uiState.asStateFlow()

    fun selectTab(tab: NavigationTab) {
        _uiState.update { it.copy(currentTab = tab) }
    }

    fun selectGrade(grade: GradeLevel) {
        _uiState.update { it.copy(selectedGrade = grade) }
    }

    fun selectTerm(term: CurriculumTerm) {
        _uiState.update { it.copy(selectedTerm = term) }
    }

    fun selectStrandFilter(strand: SubjectStrand?) {
        _uiState.update { it.copy(selectedStrandFilter = strand) }
    }

    fun setSearchQuery(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
    }

    fun openModuleDetail(module: CurriculumModule?) {
        _uiState.update { it.copy(activeModuleDetail = module) }
    }

    fun openVideoTutorial(video: VideoTutorial?) {
        _uiState.update { it.copy(activeVideoTutorial = video) }
    }

    fun toggleModuleCompletion(moduleId: String) {
        _uiState.update { state ->
            val updated = state.stats.completedModuleIds.toMutableSet()
            val gainedXp = if (updated.contains(moduleId)) {
                updated.remove(moduleId)
                0
            } else {
                updated.add(moduleId)
                50
            }
            val newXp = state.stats.totalXp + gainedXp
            saveStats(newXp, state.stats.currentStreakDays, state.stats.questionsAnswered, state.stats.correctAnswers, updated)
            state.copy(
                stats = state.stats.copy(
                    completedModuleIds = updated,
                    totalXp = newXp
                ),
                toastMessage = if (gainedXp > 0) "+50 XP: Module Completed!" else null
            )
        }
    }

    // Quiz Session Engine
    fun startQuiz(grade: GradeLevel = _uiState.value.selectedGrade, term: CurriculumTerm? = null) {
        val pool = CatCurriculumData.questions.filter { q ->
            q.grade == grade && (term == null || q.term == term)
        }.ifEmpty {
            CatCurriculumData.questions.filter { it.grade == grade }.ifEmpty { CatCurriculumData.questions }
        }.shuffled()

        _uiState.update {
            it.copy(
                quizSession = QuizSessionState(
                    isActive = true,
                    questions = pool,
                    currentIndex = 0,
                    selectedOptionIndex = null,
                    isAnswerRevealed = false,
                    score = 0,
                    isFinished = false
                ),
                currentTab = NavigationTab.ASSESSMENTS
            )
        }
    }

    fun selectQuizOption(index: Int) {
        val session = _uiState.value.quizSession
        if (session.isAnswerRevealed || session.isFinished) return

        val currentQ = session.questions[session.currentIndex]
        val isCorrect = index == currentQ.correctIndex

        _uiState.update { state ->
            val newScore = if (isCorrect) session.score + 1 else session.score
            val newTotalAnswered = state.stats.questionsAnswered + 1
            val newCorrect = if (isCorrect) state.stats.correctAnswers + 1 else state.stats.correctAnswers
            val xpGain = if (isCorrect) 25 else 5
            val newXp = state.stats.totalXp + xpGain

            saveStats(newXp, state.stats.currentStreakDays, newTotalAnswered, newCorrect, state.stats.completedModuleIds)

            state.copy(
                quizSession = session.copy(
                    selectedOptionIndex = index,
                    isAnswerRevealed = true,
                    score = newScore
                ),
                stats = state.stats.copy(
                    totalXp = newXp,
                    questionsAnswered = newTotalAnswered,
                    correctAnswers = newCorrect
                )
            )
        }
    }

    fun nextQuizQuestion() {
        val session = _uiState.value.quizSession
        if (session.currentIndex + 1 < session.questions.size) {
            _uiState.update {
                it.copy(
                    quizSession = session.copy(
                        currentIndex = session.currentIndex + 1,
                        selectedOptionIndex = null,
                        isAnswerRevealed = false
                    )
                )
            }
        } else {
            // Quiz completed
            _uiState.update {
                it.copy(
                    quizSession = session.copy(isFinished = true),
                    toastMessage = "Quiz Complete! Score: ${session.score}/${session.questions.size}"
                )
            }
        }
    }

    fun exitQuiz() {
        _uiState.update {
            it.copy(quizSession = QuizSessionState(isActive = false))
        }
    }

    // Forum Operations
    fun openForumThread(thread: ForumThread?) {
        _uiState.update { it.copy(selectedForumThread = thread) }
    }

    fun setAskingQuestion(isAsking: Boolean) {
        _uiState.update { it.copy(isAskingQuestion = isAsking) }
    }

    fun upvoteThread(threadId: String) {
        _uiState.update { state ->
            val updatedThreads = state.forumThreads.map { thread ->
                if (thread.id == threadId) {
                    thread.copy(upvotes = thread.upvotes + 1)
                } else thread
            }
            val updatedSelected = if (state.selectedForumThread?.id == threadId) {
                state.selectedForumThread.copy(upvotes = state.selectedForumThread.upvotes + 1)
            } else state.selectedForumThread

            state.copy(
                forumThreads = updatedThreads,
                selectedForumThread = updatedSelected,
                toastMessage = "Upvoted question!"
            )
        }
    }

    fun postQuestion(title: String, category: String, content: String, formula: String?) {
        if (title.isBlank() || content.isBlank()) return
        val newThread = ForumThread(
            id = "thread_${System.currentTimeMillis()}",
            title = title,
            author = _uiState.value.userSession.name,
            authorGrade = _uiState.value.selectedGrade.displayName,
            category = category,
            content = content,
            formulaSnippet = formula?.takeIf { it.isNotBlank() },
            upvotes = 1,
            timestamp = "Just now",
            isSolved = false,
            replies = emptyList()
        )
        _uiState.update { state ->
            state.copy(
                forumThreads = listOf(newThread) + state.forumThreads,
                isAskingQuestion = false,
                stats = state.stats.copy(totalXp = state.stats.totalXp + 20),
                toastMessage = "Question posted! +20 XP"
            )
        }
    }

    fun postReply(threadId: String, content: String) {
        if (content.isBlank()) return
        val newReply = ForumReply(
            id = "reply_${System.currentTimeMillis()}",
            author = _uiState.value.userSession.name,
            authorGrade = _uiState.value.selectedGrade.displayName,
            content = content,
            timestamp = "Just now",
            isTeacherVerified = false
        )
        _uiState.update { state ->
            val updatedThreads = state.forumThreads.map { thread ->
                if (thread.id == threadId) {
                    thread.copy(replies = thread.replies + newReply)
                } else thread
            }
            val updatedSelected = if (state.selectedForumThread?.id == threadId) {
                state.selectedForumThread.copy(replies = state.selectedForumThread.replies + newReply)
            } else state.selectedForumThread

            state.copy(
                forumThreads = updatedThreads,
                selectedForumThread = updatedSelected,
                stats = state.stats.copy(totalXp = state.stats.totalXp + 15),
                toastMessage = "Reply posted! +15 XP"
            )
        }
    }

    fun setThemeMode(mode: ThemeMode) {
        _uiState.update { it.copy(themeMode = mode) }
    }

    fun clearToast() {
        _uiState.update { it.copy(toastMessage = null) }
    }

    private fun saveStats(xp: Int, streak: Int, answered: Int, correct: Int, modules: Set<String>) {
        prefs.edit()
            .putInt("total_xp", xp)
            .putInt("streak_days", streak)
            .putInt("questions_answered", answered)
            .putInt("correct_answers", correct)
            .putStringSet("completed_modules", modules)
            .apply()
    }
}
