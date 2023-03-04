package ru.svoyakmartin.featureNotification

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.svoyakmartin.featureNotificationApi.NotificationFeatureApi
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NotificationFeatureApiImpl @Inject constructor(private val context: Context) :
    NotificationFeatureApi {
    override fun addRandomCharacterNotification() {
        val workManager = WorkManager.getInstance(context)
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(
            15,
            TimeUnit.MINUTES,
            14,
            TimeUnit.MINUTES
        )
            .build()

        workManager.enqueueUniquePeriodicWork(
            "RandomCharacterNotification",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            workRequest
        )
    }
}