package com.example.util

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import com.example.model.CurriculumModule
import com.example.model.GradeLevel
import com.example.model.CurriculumTerm
import java.io.File
import java.io.FileOutputStream

object PdfGenerator {

    fun generateModulePdf(context: Context, module: CurriculumModule): File {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // Standard A4 (in points)
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas

        val titlePaint = Paint().apply {
            color = Color.rgb(0, 109, 68) // Emerald Green
            textSize = 20f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val subheaderPaint = Paint().apply {
            color = Color.rgb(217, 119, 6) // Amber
            textSize = 12f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val sectionHeadingPaint = Paint().apply {
            color = Color.rgb(2, 132, 199) // Cyan
            textSize = 14f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            isAntiAlias = true
        }

        val bodyPaint = Paint().apply {
            color = Color.rgb(30, 41, 59)
            textSize = 10f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
            isAntiAlias = true
        }

        val formulaPaint = Paint().apply {
            color = Color.rgb(15, 23, 42)
            textSize = 9.5f
            typeface = Typeface.create(Typeface.MONOSPACE, Typeface.BOLD)
            isAntiAlias = true
        }

        val backgroundBoxPaint = Paint().apply {
            color = Color.rgb(241, 245, 249)
            style = Paint.Style.FILL
        }

        var yPosition = 50f

        // Document Header
        canvas.drawText("CAT MASTER SA — STUDY GUIDE", 40f, yPosition, subheaderPaint)
        yPosition += 25f
        canvas.drawText(module.title, 40f, yPosition, titlePaint)
        yPosition += 18f
        canvas.drawText("${module.grade.displayName} • ${module.term.displayName} • ${module.strand.displayName}", 40f, yPosition, subheaderPaint)
        yPosition += 25f

        // Summary Box
        canvas.drawRect(40f, yPosition, 555f, yPosition + 45f, backgroundBoxPaint)
        canvas.drawText("CAPS SYLLABUS OVERVIEW:", 50f, yPosition + 18f, sectionHeadingPaint)
        canvas.drawText(module.summary.take(85) + (if (module.summary.length > 85) "..." else ""), 50f, yPosition + 35f, bodyPaint)
        yPosition += 65f

        // Key Concepts Section
        canvas.drawText("KEY CAPS CURRICULUM CONCEPTS", 40f, yPosition, sectionHeadingPaint)
        yPosition += 18f

        for (concept in module.keyConcepts) {
            canvas.drawText("• ", 40f, yPosition, sectionHeadingPaint)
            // Handle simple word wrap
            val line = if (concept.length > 85) concept.take(85) + "..." else concept
            canvas.drawText(line, 55f, yPosition, bodyPaint)
            yPosition += 18f
        }
        yPosition += 10f

        // Practical Tips Section
        if (module.practicalTips.isNotEmpty()) {
            canvas.drawText("PRACTICAL EXAM TIPS & TECHNIQUE", 40f, yPosition, sectionHeadingPaint)
            yPosition += 18f
            for (tip in module.practicalTips) {
                canvas.drawText("✓ ", 40f, yPosition, subheaderPaint)
                val line = if (tip.length > 85) tip.take(85) + "..." else tip
                canvas.drawText(line, 55f, yPosition, bodyPaint)
                yPosition += 18f
            }
            yPosition += 10f
        }

        // Formula / Cheatsheet Section
        if (module.formulaCheatsheet.isNotEmpty()) {
            canvas.drawText("FORMULA & SHORTCUT CHEATSHEET", 40f, yPosition, sectionHeadingPaint)
            yPosition += 18f
            for ((formula, desc) in module.formulaCheatsheet) {
                canvas.drawRect(40f, yPosition - 12f, 555f, yPosition + 18f, backgroundBoxPaint)
                canvas.drawText(formula, 50f, yPosition, formulaPaint)
                canvas.drawText("→ $desc", 50f, yPosition + 13f, bodyPaint)
                yPosition += 36f
            }
        }

        // Footer
        val footerPaint = Paint().apply {
            color = Color.rgb(100, 116, 139)
            textSize = 9f
            typeface = Typeface.create(Typeface.DEFAULT, Typeface.ITALIC)
        }
        canvas.drawText("CAT Master SA • CAPS Aligned Curriculum Material • Offline Printable Resource", 40f, 800f, footerPaint)

        pdfDocument.finishPage(page)

        // Save file to app cache
        val fileName = "CAT_Guide_${module.id}.pdf"
        val file = File(context.cacheDir, fileName)
        FileOutputStream(file).use { out ->
            pdfDocument.writeTo(out)
        }
        pdfDocument.close()
        return file
    }

    fun shareOrOpenPdf(context: Context, file: File) {
        val uri = FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "application/pdf"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "CAT Study Guide: ${file.nameWithoutExtension}")
            putExtra(Intent.EXTRA_TEXT, "Here is the South African CAPS CAT study guide from CAT Master SA.")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(Intent.createChooser(intent, "Share Study Guide PDF"))
    }
}
