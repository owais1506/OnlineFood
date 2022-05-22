package com.os.onlinefood.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.os.onlinefood.AppController
import com.os.onlinefood.R
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.data.model.ToppingMaster
import com.os.onlinefood.data.repo.MyRepo
import com.os.onlinefood.databinding.ListToppingViewBinding
import org.koin.core.KoinComponent

import org.koin.core.inject
import kotlin.random.Random

class ToppingAdapter() : RecyclerView.Adapter<MyViewHolder1>(), KoinComponent {

    lateinit var context: Context
    private var inflater: LayoutInflater?=null
    lateinit var toppingInfoBinding: ListToppingViewBinding
    val app  by inject<AppController>()
    val repo by inject<MyRepo>()

    companion object{
        var toppingList = arrayListOf<ToppingMaster>()
    }

    constructor(ctx: Context):this(){
        context = ctx
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder1 {
        toppingInfoBinding = ListToppingViewBinding.inflate(inflater?: LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder1(toppingInfoBinding)
    }

    override fun onBindViewHolder(holder: MyViewHolder1, position: Int) {

        val enquiryModel = toppingList[position]
        holder.bind1(enquiryModel)

    }


    override fun getItemCount() = toppingList.size

    fun setInfoModelArrayList(dataList: ArrayList<ToppingMaster>) {
        toppingList = dataList
        notifyDataSetChanged()
    }
}

class MyViewHolder1(itemView: ListToppingViewBinding) : RecyclerView.ViewHolder(itemView.getRoot()),
    KoinComponent {
    var surveyInfoBind: ListToppingViewBinding = itemView

    fun bind1(data: ToppingMaster) {
        surveyInfoBind.enquiryModel = data
        surveyInfoBind.count.text = Random.nextInt(100,500).toString()
    }


}