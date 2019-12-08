package com.homapps.kotlin_firebase_samplesolutions.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.homapps.kotlin_firebase_samplesolutions.R
import kotlinx.android.synthetic.main.fragment_server_time.*
import kotlinx.android.synthetic.main.fragment_server_time.view.*
import java.text.SimpleDateFormat
import java.util.*


class ServerTimeFragment : Fragment() {

    lateinit var viewServerTime:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewServerTime=inflater.inflate(R.layout.fragment_server_time, container, false)

        viewServerTime.tvDeviceTime.text= getFormattedTime(System.currentTimeMillis())

        viewServerTime.btnShowServerTime.setOnClickListener {
            getServerTimeFromFirebase()
        }

        return viewServerTime
    }

    private fun getServerTimeFromFirebase() {
        val ref = FirebaseDatabase.getInstance().getReference(".info/serverTimeOffset")
        ref.addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {}

                override fun onDataChange(p0: DataSnapshot) {

                    val offset = p0.getValue(Long::class.java)

                    val estimatedServerTimeMs = System.currentTimeMillis() + offset!!

                    viewServerTime.tvServerTime.text= getFormattedTime(estimatedServerTimeMs)


                }
            }
        )
    }

    private fun getFormattedTime(timeINMilis:Long):String
    {
        val sdf = SimpleDateFormat("dd MMMM yyyy HH:mm", Locale.forLanguageTag("tr-TR"))

        val resultdate = Date(timeINMilis)

       return sdf.format(resultdate).toString()
    }



    companion object {
        fun newInstance() : ServerTimeFragment =ServerTimeFragment()
    }

}
