package com.example.blackcardskmm.android.util

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.work.*
import com.example.blackcardskmm.android.util.workers.FileDownloadWorker
import com.example.blackcardskmm.domain.models.LoreFile

fun startDownloadingFile(
    context: Context,
    owner: LifecycleOwner,
    file: LoreFile,
    processing: (String) -> Unit,
    success: (String) -> Unit,
    failed: (String) -> Unit
) {
    val workManager = WorkManager.getInstance(context)
    val data = Data.Builder()

    data.apply {
        putString(FileDownloadWorker.FileParams.KEY_FILE_NAME, file.fullName)
        putString(FileDownloadWorker.FileParams.KEY_FILE_URL, file.url)
        putString(FileDownloadWorker.FileParams.KEY_FILE_TYPE, file.type)
    }

    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .setRequiresStorageNotLow(true)
        .setRequiresBatteryNotLow(true)
        .build()

    val fileDownloadWorker = OneTimeWorkRequestBuilder<FileDownloadWorker>()
        .setConstraints(constraints)
        .setInputData(data.build())
        .build()

    workManager.enqueueUniqueWork(
        "oneFileDownloadWork_${System.currentTimeMillis()}",
        ExistingWorkPolicy.KEEP,
        fileDownloadWorker
    )

    workManager.getWorkInfoByIdLiveData(fileDownloadWorker.id)
        .observe(owner) { info->
            info?.let {
                when (it.state) {
                    WorkInfo.State.SUCCEEDED -> {
                        success(it.outputData.getString(FileDownloadWorker.FileParams.KEY_FILE_URI) ?: "")
                    }
                    WorkInfo.State.FAILED -> {
                        failed("При загрузке файла произошла ошибка...")
                    }
                    WorkInfo.State.RUNNING -> {
                        processing("Загрузка файла...")
                    }
                    else -> Unit
                }
            }
        }
}