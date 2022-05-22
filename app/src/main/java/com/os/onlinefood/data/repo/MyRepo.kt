package com.os.onlinefood.data.repo

import android.util.Log
import com.os.onlinefood.AppController
import com.os.onlinefood.data.database.MainDatabase
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.data.model.ToppingMaster

class MyRepo(val app: AppController, val database: MainDatabase) {



    fun saveDonutData(master: List<DonutMaster>?) : Boolean{

        val bool = try {

            if(master!=null) {
                master.forEach { donut ->
                    database.mainDao()?.insertDonutMaster(donut)
                    val top = arrayListOf<ToppingMaster>()
                    donut.toppings.forEach {
                        top.add(it.apply {
                            it.donutRefId = donut.id
                        })
                    }

                    top.forEach{
                        Log.e("Repo ${it.pid}", "Ref Id :" + it.donutRefId.toString())
                    }

                     database.mainDao()?.insertToppingMaster(top)
                    }
                    true
                }else false
        }catch (ex:Exception){
            Log.e("Exception",ex.message.toString())
            false
        }
        return bool
    }

    fun getLiveDonutDat() = database.mainDao()?.getAllDonuts()

    fun getDonutCount() = database.mainDao()?.getDonutCount()?:0

    fun getToppingCount(id : Int) = database.mainDao()?.getToppingById(id)?.size

    fun getAllToppingsById(id : Int) = database.mainDao()?.getAllToppingsById(id)

}