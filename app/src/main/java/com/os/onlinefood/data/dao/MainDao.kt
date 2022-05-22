package com.os.onlinefood.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.os.onlinefood.data.model.DonutMaster
import com.os.onlinefood.data.model.ToppingMaster

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDonutMaster(donuts : DonutMaster)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertToppingMaster(donuts : List<ToppingMaster>)

    @Query("select * from donutmaster")
    fun getAllDonuts() : LiveData<List<DonutMaster>>

    @Query("select Count(*) from donutmaster")
    fun getDonutCount() : Int

    @Query("select * from toppingmaster where donutRefId =:id")
    fun getToppingById(id : Int) : List<ToppingMaster>

    @Query("select * from toppingmaster where donutRefId =:id")
    fun getAllToppingsById(id : Int) : LiveData<List<ToppingMaster>>



}