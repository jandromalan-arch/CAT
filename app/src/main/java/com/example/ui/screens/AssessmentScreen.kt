package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.CurriculumTerm
import com.example.model.GradeLevel
import com.example.model.QuestionDifficulty
import com.example.model.QuizQuestion
import com.example.viewmodel.CatUiState
import com.example.viewmodel.CatViewModel

@Composable
fun AssessmentScreen(
    uiState: CatUiState,
    viewModel: CatViewModel
) {
    val session = uiState.quizSession

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("assessment_screen")
    ) {
        if (!session.isActive) {
            // Quiz Launcher / Selection View
            QuizLauncher(
                uiState = uiState,
                onStartComprehensive = { viewModel.startQuiz(uiState.selectedGrade, uiState.selectedTerm) },
                onStartMatricExamDrill = { viewModel.startQuiz(GradeLevel.GRADE_12, null) },
                onStartSpeedChallenge = { viewModel.startQuiz(uiState.selectedGrade, null) }
            )
        } else if (session.isFinished) {
            // Quiz Finished Summary
            QuizCompletionView(
                session = session,
                onRetry = { viewModel.startQuiz(uiState.selectedGrade, uiState.selectedTerm) },
                onExit = { viewModel.exitQuiz() }
            )
        } else {
            // Active Quiz Question
            ActiveQuizView(
                session = session,
                onSelectOption = { viewModel.selectQuizOption(it) },
                onNextQuestion = { viewModel.nextQuizQuestion() },
                onExit = { viewModel.exitQuiz() }
            )
        }
    }
}

@Composable
fun QuizLauncher(
    uiState: CatUiState,
    onStartComprehensive: () -> Unit,
    onStartMatricExamDrill: () -> Unit,
    onStartSpeedChallenge: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(44.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.EmojiEvents,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "Gamified Practice Arena",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Text(
                                text = "Timed interactive assessments with instant CAPS memos",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            }
        }

        item {
            Text(
                text = "Select Assessment Mode",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        // Mode 1: Term Specific Assessment
        item {
            AssessmentModeCard(
                title = "${uiState.selectedGrade.displayName} • ${uiState.selectedTerm.displayName} Assessment",
                description = "Focus strictly on the CAPS curriculum content prescribed for this term. Tests theory and practical interpretations.",
                tag = "Syllabus Aligned",
                icon = Icons.Default.Assignment,
                buttonText = "Start Term Assessment",
                onStart = onStartComprehensive,
                testTag = "start_term_quiz_button"
            )
        }

        // Mode 2: Matric Past Paper Drill
        item {
            AssessmentModeCard(
                title = "Matric Past Paper Drill (Paper 1 & 2)",
                description = "Comprehensive high-yield NSC final exam questions covering VLOOKUP, nested IF, Access calculated fields, and System buses.",
                tag = "Matric High-Yield",
                icon = Icons.Default.School,
                buttonText = "Launch Matric Drill",
                onStart = onStartMatricExamDrill,
                testTag = "start_matric_drill_button"
            )
        }

        // Mode 3: Rapid Speed Challenge
        item {
            AssessmentModeCard(
                title = "Speed Challenge: Formula & Error Spotter",
                description = "Rapid fire questions testing Excel error codes (#N/A, #VALUE!), HTML tags, and hardware specs.",
                tag = "Speed Blitz",
                icon = Icons.Default.Timer,
                buttonText = "Start Speed Challenge",
                onStart = onStartSpeedChallenge,
                testTag = "start_speed_quiz_button"
            )
        }
    }
}

@Composable
fun AssessmentModeCard(
    title: String,
    description: String,
    tag: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    buttonText: String,
    onStart: () -> Unit,
    testTag: String
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(6.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(14.dp))

            Button(
                onClick = onStart,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag(testTag)
            ) {
                Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(buttonText)
            }
        }
    }
}

@Composable
fun ActiveQuizView(
    session: com.example.viewmodel.QuizSessionState,
    onSelectOption: (Int) -> Unit,
    onNextQuestion: () -> Unit,
    onExit: () -> Unit
) {
    val question = session.questions[session.currentIndex]
    val progress = (session.currentIndex + 1).toFloat() / session.questions.size.toFloat()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("active_quiz_view"),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Quiz Header & Progress
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onExit) {
                    Icon(Icons.Default.Close, contentDescription = "Exit Quiz")
                }
                Text(
                    text = "Question ${session.currentIndex + 1} of ${session.questions.size}",
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                )
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "Score: ${session.score}",
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(3.dp)),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )
        }

        // Question Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = question.strand.displayName,
                                style = MaterialTheme.typography.labelSmall,
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = when (question.difficulty) {
                                QuestionDifficulty.FOUNDATION -> Color(0xFF10B981).copy(alpha = 0.2f)
                                QuestionDifficulty.INTERMEDIATE -> Color(0xFFF59E0B).copy(alpha = 0.2f)
                                QuestionDifficulty.MATRIC_EXAM -> Color(0xFFEF4444).copy(alpha = 0.2f)
                            }
                        ) {
                            Text(
                                text = when (question.difficulty) {
                                    QuestionDifficulty.FOUNDATION -> "Foundation"
                                    QuestionDifficulty.INTERMEDIATE -> "Intermediate"
                                    QuestionDifficulty.MATRIC_EXAM -> "Matric Exam"
                                },
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                                color = when (question.difficulty) {
                                    QuestionDifficulty.FOUNDATION -> Color(0xFF047857)
                                    QuestionDifficulty.INTERMEDIATE -> Color(0xFFB45309)
                                    QuestionDifficulty.MATRIC_EXAM -> Color(0xFFB91C1C)
                                },
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = question.question,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                            lineHeight = 22.sp
                        )
                    )

                    // Optional Code / Formula Box
                    question.formulaOrSnippet?.let { snippet ->
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = snippet,
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    fontFamily = FontFamily.Monospace,
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }
        }

        // Options List
        items(question.options.indices.toList()) { index ->
            val optionText = question.options[index]
            val isSelected = session.selectedOptionIndex == index
            val isCorrect = question.correctIndex == index
            val isRevealed = session.isAnswerRevealed

            val backgroundColor = when {
                !isRevealed -> if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
                isCorrect -> Color(0xFFD1FAE5) // Lush green
                isSelected && !isCorrect -> Color(0xFFFEE2E2) // Soft coral red
                else -> MaterialTheme.colorScheme.surface
            }

            val borderColor = when {
                !isRevealed -> if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
                isCorrect -> Color(0xFF10B981)
                isSelected && !isCorrect -> Color(0xFFEF4444)
                else -> MaterialTheme.colorScheme.surfaceVariant
            }

            Card(
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = backgroundColor),
                border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(borderColor)),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = !isRevealed) { onSelectOption(index) }
                    .testTag("quiz_option_$index")
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(borderColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = ('A' + index).toString(),
                            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isRevealed && isCorrect) Color(0xFF047857) else MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = optionText,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )

                    if (isRevealed) {
                        if (isCorrect) {
                            Icon(
                                imageVector = Icons.Default.CheckCircle,
                                contentDescription = "Correct",
                                tint = Color(0xFF10B981)
                            )
                        } else if (isSelected) {
                            Icon(
                                imageVector = Icons.Default.Cancel,
                                contentDescription = "Incorrect",
                                tint = Color(0xFFEF4444)
                            )
                        }
                    }
                }
            }
        }

        // Explanation & Next Button (when revealed)
        if (session.isAnswerRevealed) {
            item {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Text(
                                text = "DBE CAPS Marking Memo & Explanation",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = question.explanation,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            item {
                Button(
                    onClick = onNextQuestion,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("quiz_next_button")
                ) {
                    Text(
                        text = if (session.currentIndex + 1 < session.questions.size) "Next Question →" else "View Results 🎉",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun QuizCompletionView(
    session: com.example.viewmodel.QuizSessionState,
    onRetry: () -> Unit,
    onExit: () -> Unit
) {
    val total = session.questions.size
    val score = session.score
    val percentage = if (total > 0) (score.toFloat() / total.toFloat() * 100).toInt() else 0
    val xpEarned = score * 25 + 20

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .testTag("quiz_completion_view"),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(
                            if (percentage >= 70) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                            else MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (percentage >= 70) Icons.Default.EmojiEvents else Icons.Default.Psychology,
                        contentDescription = null,
                        tint = if (percentage >= 70) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (percentage >= 80) "Matric Distinction Mastery! 🏆" else if (percentage >= 50) "Great Progress! Keep Going! 📚" else "Keep Practicing! 💪",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "You scored $score out of $total ($percentage%)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "+$xpEarned XP Earned!",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onRetry,
                        modifier = Modifier
                            .weight(1f)
                            .testTag("quiz_retry_button")
                    ) {
                        Text("Retry Drill")
                    }

                    Button(
                        onClick = onExit,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("quiz_finish_button")
                    ) {
                        Text("Back to Hub")
                    }
                }
            }
        }
    }
}
