package com.os.onlinefood.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.os.onlinefood.AppController
import com.os.onlinefood.MainActivity
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.databinding.FragmentDonutBinding
import com.os.onlinefood.ui.adapter.GenericAdapter
import com.os.onlinefood.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject

class DonutFragment : Fragment() {
    lateinit var MainAdapter: GenericAdapter
    lateinit var donutUi: FragmentDonutBinding
    val mainVm by inject<MainViewModel>()
    val app by inject<AppController> ()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainVm.getDonutMaster()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        donutUi = FragmentDonutBinding.inflate(inflater, container, false)
        MainAdapter = GenericAdapter(this.requireContext(), activity as MainActivity)
        donutUi.rvList.adapter = MainAdapter
        MainAdapter.notifyDataSetChanged()

        return donutUi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        mainVm.donutData?.observe(this.viewLifecycleOwner, Observer {
            if(it!=null){
                MainAdapter.setInfoModelArrayList(it as ArrayList<DonutMaster>)
                MainAdapter.notifyDataSetChanged()
            }
        })
    }

    companion object {
        val TAG: String = DonutFragment::class.simpleName.toString()
    }
}