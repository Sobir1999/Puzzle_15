package com.example.puzzle_15

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.puzzle_15.databinding.ActivityRecordBinding
import com.example.puzzle_15.databinding.ActivitySettingBinding

class SettingActivity : AppCompatActivity() {

    lateinit var binding: ActivitySettingBinding
    val myBase: MyBase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttoncleardata.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setMessage("All records will be removed")
                .setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
                    myBase!!.clear()
                })
                .setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
            val alert = builder.create()
            alert.setTitle("Are you sure?")
            alert.show()
        }

    }
}