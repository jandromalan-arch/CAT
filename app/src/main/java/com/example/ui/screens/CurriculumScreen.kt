package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CatCurriculumData
import com.example.model.CurriculumModule
import com.example.model.SubjectStrand
import com.example.util.PdfGenerator
import com.example.viewmodel.CatUiState
import com.example.viewmodel.CatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurriculumScreen(
    uiState: CatUiState,
    viewModel: CatViewModel,
    onOpenVideo: (String) -> Unit
) {
    val context = LocalContext.current
    val currentModules = remember(uiState.selectedGrade, uiState.selectedTerm, uiState.selectedStrandFilter, uiState.searchQuery) {
        CatCurriculumData.modules.filter { m ->
            m.grade == uiState.selectedGrade &&
            m.term == uiState.selectedTerm &&
            (uiState.selectedStrandFilter == null || m.strand == uiState.selectedStrandFilter) &&
            (uiState.searchQuery.isBlank() ||
                m.title.contains(uiState.searchQuery, ignoreCase = true) ||
                m.summary.contains(uiState.searchQuery, ignoreCase = true) ||
                m.keyConcepts.any { it.contains(uiState.searchQuery, ignoreCase = true) })
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("curriculum_screen")
    ) {
        // Search Bar & Filter Strip
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.setSearchQuery(it) },
                placeholder = { Text("Search CAPS notes, VLOOKUP, HTML tags...", fontSize = 13.sp) },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                trailingIcon = {
                    if (uiState.searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.setSearchQuery("") }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("curriculum_search_input")
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Strand Category Filters
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    FilterChip(
                        selected = uiState.selectedStrandFilter == null,
                        onClick = { viewModel.selectStrandFilter(null) },
                        label = { Text("All Strands", fontSize = 12.sp) },
                        leadingIcon = if (uiState.selectedStrandFilter == null) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        } else null
                    )
                }
                items(SubjectStrand.values()) { strand ->
                    val isSelected = uiState.selectedStrandFilter == strand
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.selectStrandFilter(if (isSelected) null else strand) },
                        label = { Text(strand.displayName.split(" ").first(), fontSize = 12.sp) },
                        leadingIcon = if (isSelected) {
                            { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(14.dp)) }
                        } else null
                    )
                }
            }
        }

        // Modules List
        if (currentModules.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "No modules found matching filter",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Try clearing the search query or switching terms.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                    )
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(currentModules, key = { it.id }) { module ->
                    val isDone = uiState.stats.completedModuleIds.contains(module.id)
                    ModuleCard(
                        module = module,
                        isCompleted = isDone,
                        onToggleDone = { viewModel.toggleModuleCompletion(module.id) },
                        onClick = { viewModel.openModuleDetail(module) },
                        onOpenVideo = {
                            module.videoTutorialId?.let { onOpenVideo(it) }
                        },
                        onExportPdf = {
                            val pdf = PdfGenerator.generateModulePdf(context, module)
                            PdfGenerator.shareOrOpenPdf(context, pdf)
                        }
                    )
                }
            }
        }
    }

    // Module Detail Dialog / Full Sheet
    uiState.activeModuleDetail?.let { module ->
        ModuleDetailDialog(
            module = module,
            isCompleted = uiState.stats.completedModuleIds.contains(module.id),
            onToggleDone = { viewModel.toggleModuleCompletion(module.id) },
            onDismiss = { viewModel.openModuleDetail(null) },
            onExportPdf = {
                val pdf = PdfGenerator.generateModulePdf(context, module)
                PdfGenerator.shareOrOpenPdf(context, pdf)
            }
        )
    }
}

@Composable
fun ModuleCard(
    module: CurriculumModule,
    isCompleted: Boolean,
    onToggleDone: () -> Unit,
    onClick: () -> Unit,
    onOpenVideo: () -> Unit,
    onExportPdf: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .testTag("module_card_${module.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row: Strand tag & Completion Checkbox
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (module.strand.isPractical) MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = module.strand.displayName,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = if (module.strand.isPractical) MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = if (isCompleted) "Completed" else "Mark Done",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    IconButton(
                        onClick = onToggleDone,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                            contentDescription = "Toggle Complete",
                            tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = module.title,
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Summary
            Text(
                text = module.summary,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2
            )

            // Formula Preview if applicable
            if (module.formulaCheatsheet.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Functions,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = module.formulaCheatsheet.first().first,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Bottom Actions Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // PDF Guide download button
                TextButton(
                    onClick = onExportPdf,
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Download,
                        contentDescription = "Download PDF",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("PDF Guide", fontSize = 12.sp)
                }

                // Video Tutorial button if available
                if (module.videoTutorialId != null) {
                    TextButton(
                        onClick = onOpenVideo,
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayCircle,
                            contentDescription = "Watch Video",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Video Lesson", fontSize = 12.sp, color = MaterialTheme.colorScheme.secondary)
                    }
                }

                FilledTonalButton(
                    onClick = onClick,
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text("Read Notes", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun ModuleDetailDialog(
    module: CurriculumModule,
    isCompleted: Boolean,
    onToggleDone: () -> Unit,
    onDismiss: () -> Unit,
    onExportPdf: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onExportPdf) {
                Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("Export PDF Guide")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        title = {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${module.grade.shortName} • ${module.term.displayName}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                    IconButton(onClick = onToggleDone) {
                        Icon(
                            imageVector = if (isCompleted) Icons.Default.CheckCircle else Icons.Outlined.CheckCircle,
                            contentDescription = null,
                            tint = if (isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        },
        text = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 450.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    Text(
                        text = module.summary,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Key CAPS Concepts
                item {
                    Text(
                        text = "Core CAPS Concepts",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    module.keyConcepts.forEach { concept ->
                        Row(modifier = Modifier.padding(vertical = 3.dp)) {
                            Text("• ", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
                            Text(concept, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }

                // Practical Exam Tips
                if (module.practicalTips.isNotEmpty()) {
                    item {
                        Text(
                            text = "Practical Exam Tips",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        module.practicalTips.forEach { tip ->
                            Row(modifier = Modifier.padding(vertical = 3.dp)) {
                                Text("✓ ", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                                Text(tip, style = MaterialTheme.typography.bodySmall)
                            }
                        }
                    }
                }

                // Formulas and Cheatsheets
                if (module.formulaCheatsheet.isNotEmpty()) {
                    item {
                        Text(
                            text = "Formula Cheatsheet",
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.tertiary
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        module.formulaCheatsheet.forEach { (formula, note) ->
                            Card(
                                shape = RoundedCornerShape(8.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    Text(
                                        text = formula,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontFamily = FontFamily.Monospace,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    Text(
                                        text = note,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}
