package com.imran.appname

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.imran.appname.datastorage.RaceDatabase
import com.imran.appname.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel : MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = MainViewModel(this)

        var button = findViewById<Button>(R.id.buttonAddRecord)

        button.setOnClickListener {

            Intent(MainActivity@this,  RecordActivity::class.java).also { intent->
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.getRecords().observe(this, { list->
            list.map {entity->
                Log.d(this.javaClass.simpleName, entity.toString())
            }
        })
    }

}