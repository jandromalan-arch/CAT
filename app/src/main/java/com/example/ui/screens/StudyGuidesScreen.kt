package com.example.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.CatCurriculumData
import com.example.model.CurriculumModule
import com.example.util.PdfGenerator
import com.example.viewmodel.CatUiState

@Composable
fun StudyGuidesScreen(
    uiState: CatUiState
) {
    val context = LocalContext.current
    var previewModule by remember { mutableStateOf<CurriculumModule?>(null) }

    val modulesForGrade = remember(uiState.selectedGrade, uiState.selectedTerm) {
        CatCurriculumData.modules.filter { it.grade == uiState.selectedGrade && it.term == uiState.selectedTerm }
            .ifEmpty { CatCurriculumData.modules.filter { it.grade == uiState.selectedGrade } }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .testTag("study_guides_screen")
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.PictureAsPdf,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(32.dp)
                            )
                            Column {
                                Text(
                                    text = "Downloadable CAPS Study Guides",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                                Text(
                                    text = "Printable, offline A4 PDF revision sheets & formula summaries",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "${uiState.selectedGrade.displayName} • ${uiState.selectedTerm.displayName} Revision Pack",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            }

            // PDF Guides List
            items(modulesForGrade, key = { it.id }) { module ->
                PdfGuideCard(
                    module = module,
                    onPreview = { previewModule = module },
                    onDownload = {
                        val pdf = PdfGenerator.generateModulePdf(context, module)
                        PdfGenerator.shareOrOpenPdf(context, pdf)
                    }
                )
            }
        }
    }

    // Guide Preview Dialog
    previewModule?.let { module ->
        AlertDialog(
            onDismissRequest = { previewModule = null },
            confirmButton = {
                Button(
                    onClick = {
                        val pdf = PdfGenerator.generateModulePdf(context, module)
                        PdfGenerator.shareOrOpenPdf(context, pdf)
                        previewModule = null
                    }
                ) {
                    Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Download & Share PDF")
                }
            },
            dismissButton = {
                TextButton(onClick = { previewModule = null }) {
                    Text("Close")
                }
            },
            title = {
                Text(
                    text = "Guide Preview: ${module.title}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Format: Standard A4 Printable PDF",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = module.summary,
                        style = MaterialTheme.typography.bodySmall
                    )
                    HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))
                    Text("Included in this Guide:", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    Text("• ${module.keyConcepts.size} CAPS Syllabus Concepts", fontSize = 12.sp)
                    Text("• ${module.practicalTips.size} Practical Exam Hints", fontSize = 12.sp)
                    if (module.formulaCheatsheet.isNotEmpty()) {
                        Text("• ${module.formulaCheatsheet.size} Formula Reference Snippets", fontSize = 12.sp)
                    }
                    Text("• Complete Offline Capability & WhatsApp Sharing", fontSize = 12.sp)
                }
            }
        )
    }
}

@Composable
fun PdfGuideCard(
    module: CurriculumModule,
    onPreview: () -> Unit,
    onDownload: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.12f),
                modifier = Modifier.size(52.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Description,
                        contentDescription = "PDF Document",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(28.dp)
                    )
                }
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = module.title,
                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${module.strand.displayName} • ${module.keyConcepts.size} CAPS Concepts",
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilledTonalButton(
                        onClick = onDownload,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Icon(Icons.Default.Download, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Download", fontSize = 11.sp)
                    }

                    OutlinedButton(
                        onClick = onPreview,
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 2.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text("Preview", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
