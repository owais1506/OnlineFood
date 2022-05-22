package com.os.onlinefood.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.os.onlinefood.AppController
import com.os.onlinefood.R
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.data.repo.MyRepo
import com.os.onlinefood.databinding.ListDonutViewBinding
import com.os.onlinefood.ui.viewmodel.MainViewModel
import com.os.onlinefood.util.interfaces.DonutInterface
import org.koin.core.KoinComponent
import org.koin.core.inject

class GenericAdapter() : RecyclerView.Adapter<MyViewHolder>(),KoinComponent{

    lateinit var context: Context
    private var inflater: LayoutInflater ?=null
    lateinit var donutInfoBinding: ListDonutViewBinding
    val app  by inject<AppController>()
    val repo by inject<MyRepo>()

    companion object{
        var listener: DonutInterface ?=null
        var donutList = arrayListOf<DonutMaster>()
    }

    constructor(ctx: Context, callback: DonutInterface):this(){
        context = ctx
        listener = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        donutInfoBinding = ListDonutViewBinding.inflate(inflater?:LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(donutInfoBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val enquiryModel = donutList[position]
        holder.bind(enquiryModel)
        var animFadein: Animation = AnimationUtils.loadAnimation(context, R.anim.rotate)

        holder.surveyInfoBind.imgN.setOnClickListener {
            try {
                if(app.isConnected()) {
                    holder.surveyInfoBind.imgN.startAnimation(animFadein)
                    listener?.onDonutClick(enquiryModel.id)
                }else{
                    app.showErrorMsg("No Internet Connection")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        holder.surveyInfoBind.mainView.setOnClickListener {
          listener?.onDonutClick(enquiryModel.id)
        }
    }


    override fun getItemCount() = donutList.size

    fun setInfoModelArrayList(dataList: ArrayList<DonutMaster>) {
        donutList = dataList
        notifyDataSetChanged()
    }






}

class MyViewHolder(itemView: ListDonutViewBinding) : RecyclerView.ViewHolder(itemView.getRoot()),KoinComponent {


    var surveyInfoBind: ListDonutViewBinding = itemView
    val mainVm by inject<MainViewModel> ()


    fun bind(data: DonutMaster) {
        surveyInfoBind.enquiryModel = data
        surveyInfoBind.count.text = mainVm.getToppingCount(data.id).toString()
    }


}


