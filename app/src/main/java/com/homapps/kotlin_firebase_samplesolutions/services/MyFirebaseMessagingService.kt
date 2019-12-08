package com.homapps.kotlin_firebase_samplesolutions.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.homapps.kotlin_firebase_samplesolutions.MainActivity
import com.homapps.kotlin_firebase_samplesolutions.R

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

      setNotification(applicationContext,remoteMessage.data)

    }


    private fun setNotification(
        context: Context,
        data: MutableMap<String, String>
    ) {



        val notification_id = 234
        var CHANNEL_ID = ""
        val notificationManager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CHANNEL_ID = "my_channel_01"
            val name = "my_channel"
            val Description = "This is my channel"
            val importance = NotificationManager.IMPORTANCE_LOW
            //IMPORTANCE_LOW yaparak sadece barda gözükmesini sağladık.
            val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
            mChannel.description = Description
            mChannel.enableLights(true)
            mChannel.lightColor = Color.RED
            mChannel.enableVibration(true)
            mChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            mChannel.setShowBadge(false)
            notificationManager.createNotificationChannel(mChannel)
        }
        val  builder =  NotificationCompat.Builder(context,CHANNEL_ID)
        //  builder.priority= NotificationCompat.PRIORITY_MAX
        // yukardakini iptal ederek sadece barda gözükmesini sağladık.

        val notification_intent =  Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,notification_intent,0)
        builder.setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("New User Added "+data["title"])
             .setOngoing(true) // setCancelable
            .setAutoCancel(false)
            .setContentIntent(pendingIntent)
        notificationManager.notify(notification_id,builder.build())

    }


}