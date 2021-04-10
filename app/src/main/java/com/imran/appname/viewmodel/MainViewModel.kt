package com.imran.appname.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.appname.datastorage.RaceDao
import com.imran.appname.datastorage.RaceDatabase
import com.imran.appname.datastorage.RecordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(context: Context) : ViewModel() {
    private var raceDao : RaceDao = RaceDatabase.get(context).getDao()

    //Live data can be observed by an observer whenever value wrapped in the live data changes.
    private var liveRecords = MutableLiveData<List<RecordEntity>>()

    fun getRecords (): MutableLiveData<List<RecordEntity>> {

         viewModelScope.launch {
             var result = withContext(Dispatchers.IO){ raceDao.getAllRecords() }
             liveRecords.value = result // updating the value
        }

        return liveRecords
    }
}