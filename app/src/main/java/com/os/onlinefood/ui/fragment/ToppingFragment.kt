package com.os.onlinefood.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.os.onlinefood.AppController
import com.os.onlinefood.MainActivity
import com.os.onlinefood.R
import com.os.onlinefood.data.model.ToppingMaster
import com.os.onlinefood.databinding.FragmentDonutBinding
import com.os.onlinefood.databinding.FragmentToppingBinding
import com.os.onlinefood.ui.adapter.GenericAdapter
import com.os.onlinefood.ui.adapter.ToppingAdapter
import com.os.onlinefood.ui.viewmodel.MainViewModel
import org.koin.android.ext.android.inject


class ToppingFragment : Fragment() {

    lateinit var ToppingAdapter: ToppingAdapter
    lateinit var ToppingUi: FragmentToppingBinding
    val mainVm by inject<MainViewModel>()
    val app by inject<AppController> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        ToppingUi = FragmentToppingBinding.inflate(inflater, container, false)
        return ToppingUi.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ToppingAdapter = ToppingAdapter(this.requireContext())
        ToppingUi.rvList.adapter = ToppingAdapter
        ToppingAdapter.notifyDataSetChanged()

        val bundle = arguments
        val donutId = bundle?.getInt("Id")?:0
        mainVm.getAllToppingsById(donutId)?.observe(this.viewLifecycleOwner, Observer {
            if(it!=null){
                ToppingAdapter.setInfoModelArrayList(it as ArrayList<ToppingMaster>)
                ToppingAdapter.notifyDataSetChanged()
            }
        })
    }



    companion object {
        val TAG: String = ToppingFragment::class.simpleName.toString()
    }


}