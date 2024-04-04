package com.example.composehelloworld

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.lang.Thread.State

class MainViewModel : ViewModel() {
    private val _dataList = MutableLiveData(listOf<ContentDataModel>())
    val dataList : LiveData<List<ContentDataModel>> = _dataList


    fun addItem(item : String){
        val data = _dataList.value?.toMutableList()
        data?.add(ContentDataModel(item, data.size+1))
        _dataList.value = data
    }

    fun delItem(index : Int){
        val data = _dataList.value?.toMutableList()
        data?.removeAt(index)
        _dataList.value = data
    }


}