package com.example

import com.example.data.CatCurriculumData
import com.example.model.GradeLevel
import com.example.model.CurriculumTerm
import org.junit.Assert.*
import org.junit.Test

class CatCurriculumTest {

    @Test
    fun testCurriculumModulesExistForAllGrades() {
        val modules = CatCurriculumData.modules
        assertTrue("Modules should not be empty", modules.isNotEmpty())

        val grades = modules.map { it.grade }.toSet()
        assertTrue("Should contain Grade 10", grades.contains(GradeLevel.GRADE_10))
        assertTrue("Should contain Grade 11", grades.contains(GradeLevel.GRADE_11))
        assertTrue("Should contain Grade 12", grades.contains(GradeLevel.GRADE_12))
    }

    @Test
    fun testQuestionsHaveValidAnswers() {
        val questions = CatCurriculumData.questions
        assertTrue("Question bank should not be empty", questions.isNotEmpty())

        questions.forEach { q ->
            assertTrue("Question must have options", q.options.size >= 2)
            assertTrue("Correct index must be within options range", q.correctIndex in q.options.indices)
            assertTrue("Explanation must be provided", q.explanation.isNotBlank())
        }
    }

    @Test
    fun testVideoTutorialsAndForum() {
        val videos = CatCurriculumData.videoTutorials
        assertTrue("Video tutorials must be present", videos.isNotEmpty())
        videos.forEach { v ->
            assertTrue("Video must have timestamps", v.keyTimestamps.isNotEmpty())
        }

        val threads = CatCurriculumData.forumThreads
        assertTrue("Peer forum must have initial threads", threads.isNotEmpty())
    }
}
