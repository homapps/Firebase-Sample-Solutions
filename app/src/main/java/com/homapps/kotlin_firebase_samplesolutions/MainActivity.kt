package com.homapps.kotlin_firebase_samplesolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.messaging.FirebaseMessaging
import com.homapps.kotlin_firebase_samplesolutions.fragments.FirstFragment
import com.homapps.kotlin_firebase_samplesolutions.utils.AppPreferences
import com.homapps.kotlin_firebase_samplesolutions.utils.logd






class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeToTopic()

        AppPreferences.init(this)

        supportFragmentManager.apply {
            beginTransaction()
                .addToBackStack("ContainerFragment")
                .replace(R.id.container,FirstFragment.newInstance())
                .commit()
        }

    }

    private fun subscribeToTopic() {

        FirebaseMessaging.getInstance().subscribeToTopic("Message_Notifications")
            .addOnCompleteListener { task ->
                if (task.isSuccessful)
                    getString(R.string.msg_subscribed).logd()
                else getString(R.string.msg_subscribe_failed).logd()
            }
    }

}
