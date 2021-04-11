package com.imran.appname

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.imran.appname.databinding.ActivityRecordBinding


class RecordActivity : AppCompatActivity() {

    lateinit var binding: ActivityRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecordBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonSave.setOnClickListener {

            //Todo Add record to database
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