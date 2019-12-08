package com.homapps.kotlin_firebase_samplesolutions.utils

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import androidx.annotation.RequiresPermission
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe

object InternetConnectivity {

    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun observable(context: Context): Observable<Boolean> {
        val applicationContext = context.applicationContext
        val filter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        return createBroadcastReceiverObservable(applicationContext, filter)
            // To get initial connectivity status
            .startWith(Intent())
            .map { isConnected(applicationContext) }
            .distinctUntilChanged()
    }


    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


        androidQAndAbove({  // >= Build.VERSION_CODES.Q
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork())
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        },{
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            @Suppress("DEPRECATION")
            val activeNetwork = cm.activeNetworkInfo
            @Suppress("DEPRECATION")
            return activeNetwork != null && activeNetwork.isConnected
        })


            return false

    }

    private fun createBroadcastReceiverObservable(context: Context,
               intentFilter: IntentFilter,
               broadcastPermission: String? = null,
               schedulerHandler: Handler? = null): Observable<Intent> {
        return Observable.create(BroadcastReceiverObservableOnSubscribe(context,
            intentFilter,
            broadcastPermission,
            schedulerHandler))
    }
    internal class BroadcastReceiverObservableOnSubscribe(private val context: Context,
                                                          private val intentFilter: IntentFilter, private val broadcastPermission: String? = null,
                                                          private val schedulerHandler: Handler? = null) : ObservableOnSubscribe<Intent> {

        override fun subscribe(e: ObservableEmitter<Intent>) {
            val broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    e.onNext(intent)
                }
            }
            context.registerReceiver(broadcastReceiver, intentFilter, broadcastPermission, schedulerHandler)

            e.setCancellable { context.unregisterReceiver(broadcastReceiver) }
        }
    }
}