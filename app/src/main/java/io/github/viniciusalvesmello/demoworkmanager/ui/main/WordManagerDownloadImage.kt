package io.github.viniciusalvesmello.demoworkmanager.ui.main

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import io.github.viniciusalvesmello.demoworkmanager.utils.KEY_IMAGE_PATH
import io.github.viniciusalvesmello.demoworkmanager.utils.KEY_IMAGE_URL
import io.github.viniciusalvesmello.demoworkmanager.utils.URL_SEPARATOR_CHARACTER
import io.github.viniciusalvesmello.demoworkmanager.utils.extension.fileExists
import java.net.HttpURLConnection
import java.net.URL

class WordManagerDownloadImage(appContext: Context, workerParams: WorkerParameters) :
    Worker(appContext, workerParams) {
    override fun doWork(): Result {
        return try {
            val imageUrl = inputData.getString(KEY_IMAGE_URL)
            val filePath = if (imageUrl != null) downloadImage(imageUrl) else ""
            val outputData = workDataOf(KEY_IMAGE_PATH to filePath)
            Result.success(outputData)
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun downloadImage(imageUrl: String): String {
        try {
            val filename = imageUrl.substringAfterLast(URL_SEPARATOR_CHARACTER)
            if(applicationContext.fileExists(filename)) return ""
            val connection: HttpURLConnection = URL(imageUrl).openConnection() as HttpURLConnection
            connection.connect()
            if (connection.responseCode != 200) return ""
            BitmapFactory.decodeStream(connection.inputStream).compress(
                Bitmap.CompressFormat.PNG,
                100,
                applicationContext.openFileOutput(
                    filename,
                    Context.MODE_PRIVATE
                )
            )
            connection.disconnect()
            return applicationContext
                .getFileStreamPath(filename)
                .absolutePath
        } catch (e: Exception) {
            return ""
        }
    }
}