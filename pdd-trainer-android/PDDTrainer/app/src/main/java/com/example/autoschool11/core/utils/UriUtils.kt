package com.example.autoschool11.core.utils


import android.content.Context
import android.net.Uri
import java.io.File

fun Uri.toFile(context: Context): File {
    val inputStream = context.contentResolver.openInputStream(this)
        ?: throw IllegalArgumentException("Не удалось открыть URI: $this")

    val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
    tempFile.outputStream().use { outputStream ->
        inputStream.copyTo(outputStream)
    }
    return tempFile
}
