package com.homapps.kotlin_firebase_samplesolutions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.firebase.messaging.FirebaseMessaging
import com.homapps.kotlin_firebase_samplesolutions.fragments.FirstFragment
import com.homapps.kotlin_firebase_samplesolutions.fragments.SendMultipleDeviceFragment
import com.homapps.kotlin_firebase_samplesolutions.fragments.ServerTimeFragment
import com.homapps.kotlin_firebase_samplesolutions.model.TitleItem
import com.homapps.kotlin_firebase_samplesolutions.utils.logd
import com.homapps.kotlin_firebase_samplesolutions.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.view_holder.*
import zlc.season.sange.DataSource
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem
import zlc.season.yasha.linear

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeToTopic()

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
