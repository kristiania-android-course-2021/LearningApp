package com.imran.appname.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imran.appname.datastorage.RaceDao
import com.imran.appname.datastorage.RaceDatabase
import com.imran.appname.datastorage.RecordEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecordViewModel(context: Context) : ViewModel() {

    private var raceDao : RaceDao = RaceDatabase.get(context).getDao()


    fun addRaceRecord (entity : RecordEntity) {

        viewModelScope.launch {

            withContext(Dispatchers.IO){

                //This is a call in a thread
                raceDao.insertRecord(entity)
            }
        }
    }


}