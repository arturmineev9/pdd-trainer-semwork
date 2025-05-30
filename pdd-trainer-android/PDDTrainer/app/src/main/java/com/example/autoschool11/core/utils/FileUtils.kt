package com.example.autoschool11.core.utils

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun getFileFromUri(context: Context, uri: Uri): File {
    val inputStream = context.contentResolver.openInputStream(uri)!!
    val file = File(context.cacheDir, "upload.jpg")
    val outputStream = FileOutputStream(file)
    inputStream.copyTo(outputStream)
    inputStream.close()
    outputStream.close()
    return file
}
