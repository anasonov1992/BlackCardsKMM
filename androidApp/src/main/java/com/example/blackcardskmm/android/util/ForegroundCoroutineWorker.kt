package com.example.blackcardskmm.android.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.blackcardskmm.android.R
import kotlinx.coroutines.delay


class ForegroundCoroutineWorker(private val appContext: Context, workerParams: WorkerParameters)
    : CoroutineWorker(appContext, workerParams) {
    // constants
    val CHANNEL_ID = "foreground"
    val NOTIFICATION_ID = 42

    override suspend fun doWork(): Result {
        val foregroundInfo = getForegroundInfo()
        setForegroundAsync(foregroundInfo)

        Log.i(CHANNEL_ID, "doWork...")

        //emulate long-running activity
        delay(30000)

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        return ForegroundInfo(NOTIFICATION_ID, createLocalNotification())
    }

    private fun createLocalNotification(): Notification {
        createNotificationChannel()

        return NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Important background job")
            .setContentText("Foreground Service")
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_HIGH)

            val manager = appContext.getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}