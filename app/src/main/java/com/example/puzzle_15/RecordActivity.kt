package com.example.puzzle_15

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.puzzle_15.databinding.ActivityRecordBinding

class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE)
        Records()

    }

    fun Records(){

        val myBase = MyBase(this)
        val lasttime = myBase.getlasttime()
        val laststep = myBase.getlaststep()
        val besttime = myBase.getbesttime()
        val beststep = myBase.getbeststep()
        val besttime2 = myBase.getbesttime2()
        val beststep2 = myBase.getbeststep2()


        binding.tvBesttime3.text = lasttime.toString()
        binding.tvSteps3.text = laststep.toString()

        val second1 = besttime%60
        val hour1 = besttime/3600
        val minut1 = (besttime-hour1*3600)/60
        binding.tvSteps1.text = beststep.toString()
        binding.tvBesttime1.text = String.format("%02d:%02d:%02d",hour1,minut1,second1)

        val second2 = besttime2%60
        val hour2 = besttime2/3600
        val minut2 = (besttime2-hour2*3600)/60
        binding.tvSteps2.text = beststep2.toString()
        binding.tvBesttime2.text = String.format("%02d:%02d:%02d",hour2,minut2,second2)

//        val second3 = lasttime%60
//        val hour3 = lasttime/3600
//        val minut3 = (lasttime-hour3*3600)/60
//        binding.tvSteps2.text = laststep.toString()
//        binding.tvBesttime2.text = String.format("%02d:%02d:%02d",hour3,minut3,second3)
//        }

    }
