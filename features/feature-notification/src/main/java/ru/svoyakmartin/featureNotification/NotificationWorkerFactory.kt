package ru.svoyakmartin.featureNotification

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi
import javax.inject.Inject

class NotificationWorkerFactory @Inject constructor(private val characterFeatureApi: CharacterFeatureApi): WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker {
        return NotificationWorker(appContext, workerParameters, characterFeatureApi)
    }
}