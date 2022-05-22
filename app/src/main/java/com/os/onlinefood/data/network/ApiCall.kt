package com.os.onlinefood.data.network

import com.os.onlinefood.data.model.DonutMaster
import retrofit2.Call
import retrofit2.http.GET

interface ApiCall {
    @GET("0ba63b71-bb15-434e-9da3-98435dcb346d")
    fun getAllDonut(): Call<List<DonutMaster>>
}