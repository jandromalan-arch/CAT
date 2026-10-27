package com.example.model

enum class GradeLevel(val displayName: String, val shortName: String) {
    GRADE_10("Grade 10", "Gr 10"),
    GRADE_11("Grade 11", "Gr 11"),
    GRADE_12("Grade 12 (Matric)", "Gr 12")
}

enum class CurriculumTerm(val displayName: String, val number: Int) {
    TERM_1("Term 1", 1),
    TERM_2("Term 2", 2),
    TERM_3("Term 3", 3),
    TERM_4("Term 4", 4)
}

enum class SubjectStrand(val displayName: String, val isPractical: Boolean) {
    SYSTEMS_TECH("Systems Technologies", false),
    NETWORK_TECH("Network Technologies", false),
    INTERNET_COMM("Internet & Communication", false),
    SOCIAL_IMPLICATIONS("Social Implications", false),
    DATA_MANAGEMENT("Information Management", false),
    WORD_PROCESSING("Word Processing (MS Word)", true),
    SPREADSHEETS("Spreadsheets (MS Excel)", true),
    DATABASES("Databases (MS Access)", true),
    WEB_DESIGN("Web Design (HTML & CSS)", true)
}

data class CurriculumModule(
    val id: String,
    val grade: GradeLevel,
    val term: CurriculumTerm,
    val strand: SubjectStrand,
    val title: String,
    val summary: String,
    val keyConcepts: List<String>,
    val practicalTips: List<String> = emptyList(),
    val commonExamTraps: List<String> = emptyList(),
    val formulaCheatsheet: List<Pair<String, String>> = emptyList(),
    val videoTutorialId: String? = null,
    val isCompleted: Boolean = false
)

enum class QuestionDifficulty {
    FOUNDATION,
    INTERMEDIATE,
    MATRIC_EXAM
}

data class QuizQuestion(
    val id: String,
    val grade: GradeLevel,
    val term: CurriculumTerm,
    val strand: SubjectStrand,
    val difficulty: QuestionDifficulty,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String,
    val formulaOrSnippet: String? = null
)

data class VideoTutorial(
    val id: String,
    val title: String,
    val strand: SubjectStrand,
    val grade: GradeLevel,
    val durationMinutes: Int,
    val description: String,
    val keyTimestamps: List<Pair<String, String>>, // e.g. "01:15" to "Creating Calculated Field"
    val tipSnippet: String
)

data class ForumReply(
    val id: String,
    val author: String,
    val authorGrade: String,
    val content: String,
    val timestamp: String,
    val isTeacherVerified: Boolean = false
)

data class ForumThread(
    val id: String,
    val title: String,
    val author: String,
    val authorGrade: String,
    val category: String, // e.g. "Excel", "Access", "Theory", "PAT"
    val content: String,
    val formulaSnippet: String? = null,
    val upvotes: Int,
    val timestamp: String,
    val isSolved: Boolean = false,
    val replies: List<ForumReply> = emptyList()
)

data class LearnerStats(
    val totalXp: Int = 340,
    val currentStreakDays: Int = 5,
    val questionsAnswered: Int = 42,
    val correctAnswers: Int = 36,
    val completedModuleIds: Set<String> = setOf("gr12_t1_vlookup", "gr12_t1_sys_tech", "gr11_t1_if"),
    val unlockedBadgeIds: Set<String> = setOf("first_step", "excel_master", "matric_ready"),
    val termProgress: Map<CurriculumTerm, Float> = mapOf(
        CurriculumTerm.TERM_1 to 0.75f,
        CurriculumTerm.TERM_2 to 0.50f,
        CurriculumTerm.TERM_3 to 0.30f,
        CurriculumTerm.TERM_4 to 0.15f
    )
)

data class AchievementBadge(
    val id: String,
    val title: String,
    val description: String,
    val iconName: String,
    val isUnlocked: Boolean
)
