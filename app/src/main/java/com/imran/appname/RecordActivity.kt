package com.imran.appname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.imran.appname.databinding.ActivityRecordBinding
import com.imran.appname.datastorage.RaceDatabase
import com.imran.appname.datastorage.RecordEntity
import com.imran.appname.viewmodel.RecordViewModel

class RecordActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecordBinding
    private lateinit var viewModel : RecordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordBinding.inflate(layoutInflater)
        viewModel = RecordViewModel(this)

        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {

            var entity = RecordEntity(
                binding.editName.text.toString(),
                binding.editLength.text.toString().toInt(),
                binding.editTime.text.toString().toInt()
            )

            //This is the non blocking way of accessing database
            viewModel.addRaceRecord(entity)

            finish() // This will finish this activity. app will navigate back to main activity.
        }

        binding.editName.addTextChangedListener(textWatcher)
        binding.editLength.addTextChangedListener(textWatcher)
        binding.editTime.addTextChangedListener(textWatcher)



    }


    var textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {

            binding.buttonSave.isEnabled =  binding.editName.text.isNotEmpty()
                    && binding.editLength.text.isNotEmpty() && binding.editTime.text.isNotEmpty()

        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}