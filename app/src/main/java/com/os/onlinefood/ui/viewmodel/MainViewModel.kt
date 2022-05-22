package com.os.onlinefood.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.os.onlinefood.AppController
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.data.network.ApiCall
import com.os.onlinefood.data.repo.MyRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext
import com.os.onlinefood.util.*
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback
import java.lang.Exception


class MainViewModel(val app: AppController, val repo: MyRepo) : ViewModel(), CoroutineScope {

    val output = MutableLiveData<Result<String>>()
    val api = app.getRetrofitClient().create(ApiCall::class.java)
    var donutData : LiveData<List<DonutMaster>> ?= MutableLiveData()

    val result : LiveData<Result<String>>
        get() = output

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO


    fun getAllDonutApi(){
        output.value = Result.Loading(true)

        launch(Dispatchers.IO) {
            api.getAllDonut().enqueue(object : Callback<List<DonutMaster>> {

                override fun onResponse(call: Call<List<DonutMaster>>, response: Response<List<DonutMaster>>) {
                    output.value = Result.Loading(false)
                    try {
                        if(response.isSuccessful){
                            //output.value = Result.SuccessMsg("Success")
                            repo.saveDonutData(response.body()).let {
                                Log.e("Status",it.toString() + " ")
                                if(it){
                                    output.value = Result.SuccessMsg("Success")
                                }else{
                                    output.value = Result.SuccessMsg("Failure")
                                }
                            }
                        }else{
                            Log.e("Call Else","Success")
                            output.value = Result.Error.RecoverableError(Exception(response.errorBody()?.string()))
                        }

                    }catch (ex : Exception){
                        Log.e("Exception",ex.toString())
                        output.value = Result.Error.RecoverableError(Exception(ex.message))
                    }
                }

                override fun onFailure(call: Call<List<DonutMaster>>, t: Throwable) {
                    Log.e("Call",t.message.toString())
                    output.value = Result.Loading(false)
                    output.value = Result.Error.NonRecoverableError(Exception(t.message))

                }
            })
        }
    }

    fun getDonutMaster(){
        donutData = repo.getLiveDonutDat()
    }

    fun getDonutCount() = repo.getDonutCount()

    fun getToppingCount(id : Int) = repo.getToppingCount(id)

    fun getAllToppingsById(id : Int) = repo.getAllToppingsById(id)

}