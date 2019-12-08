package com.homapps.kotlin_firebase_samplesolutions.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.homapps.kotlin_firebase_samplesolutions.R
import com.homapps.kotlin_firebase_samplesolutions.model.TitleItem
import com.homapps.kotlin_firebase_samplesolutions.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_first.*
import kotlinx.android.synthetic.main.fragment_first.view.*
import kotlinx.android.synthetic.main.view_holder.*
import zlc.season.yasha.YashaDataSource
import zlc.season.yasha.YashaItem
import zlc.season.yasha.linear


class FirstFragment : Fragment() {
lateinit var viewFirst:View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewFirst=inflater.inflate(R.layout.fragment_first, container, false)

        setUpRecyclerView()

        return viewFirst
    }

    private fun setUpRecyclerView() {

        val titleItems = mutableListOf<YashaItem>()
        for (title in resources.getStringArray(R.array.titles)) {
            titleItems.add(TitleItem(title))
        }
        val dataSource= YashaDataSource()
        dataSource.addItems(titleItems)

        viewFirst.recycler_view.linear(dataSource)
        {
            renderItem<TitleItem> {
                res(R.layout.view_holder)
                onBind {
                    tv_title.text = data.toString()
                    tv_title.setOnClickListener { textView->
                        replaceFragment(tv_title.text.toString())
                    }
                }
            }
        }

    }

    private fun replaceFragment(text: String) {

        val fragment= when(text)
        {
            resources.getStringArray(R.array.titles)[0]-> SendMultipleDeviceFragment.newInstance()
            resources.getStringArray(R.array.titles)[1]-> ServerTimeFragment.newInstance()
            else-> SendMultipleDeviceFragment.newInstance()
        }

        fragmentManager?.apply {
            beginTransaction()
                .addToBackStack("ContainerFragment")
                .replace(R.id.container,fragment)
                .commit()
        }

    }


    companion object {
        fun newInstance() : FirstFragment = FirstFragment()
    }
}
