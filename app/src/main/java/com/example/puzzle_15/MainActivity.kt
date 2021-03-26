package com.example.puzzle_15

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.puzzle_15.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            startActivity(Intent(this,GameActivity::class.java))
        }
        binding.recordsButton.setOnClickListener {
            startActivity(Intent(this,RecordActivity::class.java))
        }
        binding.settingsButton.setOnClickListener {
            val intent = Intent(this,SettingActivity::class.java)
            startActivity(intent)
        }
        binding.exitButton.setOnClickListener {
            finish()
        }
    }
}