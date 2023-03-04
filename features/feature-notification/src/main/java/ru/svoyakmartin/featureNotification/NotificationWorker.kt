package ru.svoyakmartin.featureNotification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.icu.util.Calendar
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.launch
import ru.svoyakmartin.featureCharacterApi.CharacterFeatureApi


private const val CHANNEL_ID = "ru.svoyakmartin.rickandmorty.everyday"
private const val CHANNEL_NAME = "Everyday notification"
private const val CHANNEL_DESCRIPTION =
    "Everyday notification with random character from Rick and Morty"

class NotificationWorker(
    context: Context,
    params: WorkerParameters,
    private val characterFeatureApi: CharacterFeatureApi
) : CoroutineWorker(context, params) {
    private lateinit var job: Job

    override suspend fun doWork(): Result {
        return try {
            job = CoroutineScope(Dispatchers.IO).launch {
                characterFeatureApi.getRandomCharacterMap()
                    .cancellable()
                    .collect { response ->
                        if (checkError(response)) {
                            response?.let {
                                Log.e(
                                    "WORKER", "${response["id"]}___" +
                                            "${response["name"]}___" +
                                            "${response["image"]}___" +
                                            "${response["species"]}___" +
                                            "${response["type"]}!!!"
                                )
                                preSendNotification(response)
                            }
                        }
                    }
            }
            Result.success()
        } catch (e: Exception) {
            Log.e("ERROR", "???___???___???___???___???")
            Result.retry()
        }
    }

    private fun checkError(response: Map<String, Any>?): Boolean {
        return when {
            response?.get("id") != null -> true
            else -> {
                //todo error
                false
            }
        }
    }

    private fun preSendNotification(data: Map<String, Any>) {
        createChannel()

        Glide.with(applicationContext)
            .asBitmap()
            .load(data["image"])
            .into(object : CustomTarget<Bitmap?>() {

                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap?>?
                ) {
                    sendNotification(data, resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun sendNotification(data: Map<String, Any>, resource: Bitmap) {
        //        val flag = PendingIntent.FLAG_IMMUTABLE
//
//        val intent = Intent(applicationContext, !!!)// receiver
//        val pendingIntent =
//            PendingIntent.getBroadcast(applicationContext, 0, intent, flag)
//        val clickIntent = Intent(applicationContext, !!!)//activity
//        val pendingClickIntent =
//            PendingIntent.getBroadcast(applicationContext, 0, clickIntent, flag)

        createChannel()

        val title = data["name"].toString()
        val text = "${data["type"]}\n${data["species"]}"

        val builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID).apply {
            setSmallIcon(androidx.loader.R.drawable.notification_bg)// todo icon from app
            setLargeIcon(resource)
            setContentTitle(title)
            setContentText(text)
            setAutoCancel(true)
            setStyle(
                NotificationCompat.BigPictureStyle().bigPicture(resource)
                    .setBigContentTitle(title)
                    .setSummaryText(text)
            )
//            addAction(0, "Action", pendingIntent)
//            setContentIntent(pendingClickIntent)
        }

        (applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
            .notify(Calendar.getInstance().timeInMillis.toInt(), builder.build())

        job.cancel()
    }

    private fun createChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = CHANNEL_DESCRIPTION
        }

        val notificationManager =
            applicationContext.getSystemService(NotificationManager::class.java)

        notificationManager.createNotificationChannel(notificationChannel)
    }
}