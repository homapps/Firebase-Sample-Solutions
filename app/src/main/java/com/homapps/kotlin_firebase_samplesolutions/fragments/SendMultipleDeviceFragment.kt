package com.homapps.kotlin_firebase_samplesolutions.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homapps.kotlin_firebase_samplesolutions.R
import com.homapps.kotlin_firebase_samplesolutions.utils.FirestoreUtil
import com.homapps.kotlin_firebase_samplesolutions.utils.onChange
import kotlinx.android.synthetic.main.fragment_send_multiple_device.*
import kotlinx.android.synthetic.main.fragment_send_multiple_device.view.*


class SendMultipleDeviceFragment : Fragment() {

    lateinit var viewSendMultipleDevice:View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewSendMultipleDevice=inflater.inflate(R.layout.fragment_send_multiple_device, container, false)

        setViews()

        return viewSendMultipleDevice
    }

    private fun setViews() {

        viewSendMultipleDevice.apply {

            etName.onChange {name->
                btnAddUser.isEnabled = name.length>0
            }
            btnAddUser.setOnClickListener {
                FirestoreUtil.addUserToDatabase(etName.text.toString())
            }
        }

    }


    companion object {
        fun newInstance() : SendMultipleDeviceFragment =SendMultipleDeviceFragment()
    }

}
