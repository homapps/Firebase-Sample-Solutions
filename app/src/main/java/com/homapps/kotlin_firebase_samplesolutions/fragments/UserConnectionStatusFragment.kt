package com.homapps.kotlin_firebase_samplesolutions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.homapps.kotlin_firebase_samplesolutions.R
import com.homapps.kotlin_firebase_samplesolutions.utils.AppPreferences
import com.homapps.kotlin_firebase_samplesolutions.utils.InternetConnectivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_user_connection_status.view.*


class UserConnectionStatusFragment : Fragment() {
lateinit var viewUserConnectionStatus:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewUserConnectionStatus=inflater.inflate(R.layout.fragment_user_connection_status, container, false)



        subscribe(InternetConnectivity.observable(context!!).subscribe{ connected ->
            if(connected)
                setUserOnlineOfflineStatus()
        })



        return viewUserConnectionStatus
    }

    private fun setUserOnlineOfflineStatus() {

        var userId=AppPreferences.userId?:""
        if(userId.length<1) {
            userId = System.currentTimeMillis().toString()
            AppPreferences.userId = userId
        }

        val userDataOnline= hashMapOf<String,String>(Pair("isonline","online"))
        FirebaseDatabase.getInstance().getReference("userstatus/"+ userId).setValue(userDataOnline)

        FirebaseDatabase.getInstance().getReference("/userstatus/" + userId).onDisconnect().removeValue()

        FirebaseDatabase.getInstance().getReference("userstatus/"+ userId).addChildEventListener(object:ChildEventListener{
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            viewUserConnectionStatus.tvConnectionStatus.text=getString(R.string.user_online)
            }

            override fun onChildRemoved(p0: DataSnapshot) {
                viewUserConnectionStatus.tvConnectionStatus.text=getString(R.string.user_offline)
            }
        })

    }

    fun subscribe(disposable: Disposable): Disposable {
        subscriptions.add(disposable)
        return disposable
    }
    val subscriptions = CompositeDisposable()

    override fun onStop() {
        subscriptions.clear()
        super.onStop()
    }

    companion object {
        fun newInstance() : UserConnectionStatusFragment = UserConnectionStatusFragment()
    }
}
