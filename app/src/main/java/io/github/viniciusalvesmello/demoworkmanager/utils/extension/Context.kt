package io.github.viniciusalvesmello.demoworkmanager.utils.extension

import android.content.Context

fun Context.fileExists(fileName: String): Boolean {
    if (fileName.isEmpty()) return false
    if (this.fileList().none { it == fileName }) return false
    return true
}