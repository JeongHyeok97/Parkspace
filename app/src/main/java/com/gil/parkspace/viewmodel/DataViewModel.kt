package com.gil.parkspace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gil.parkspace.ParkingLot

class DataViewModel : ViewModel() {
    private val mDataList = MutableLiveData<List<ParkingLot>>()

    val dataList: LiveData<List<ParkingLot>>
        get() {
            return mDataList
        }

    fun setData(list: List<ParkingLot>){
        this.mDataList.value=list
    }
}