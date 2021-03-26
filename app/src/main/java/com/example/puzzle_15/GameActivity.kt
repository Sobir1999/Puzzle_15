package com.example.puzzle_15

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.puzzle_15.databinding.ActivityGameBinding
import java.util.*
import kotlin.math.abs
import kotlin.text.*

class GameActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameBinding
    private var emptyX = 3
    private var emptyY = 3
    private var group: RelativeLayout? = null
    private var tiles: IntArray? = null
    private  var button: Button? = null
    private var textViewsteps: TextView? = null
    private var timer: Timer = Timer()
    private var stepscount: Int = 0
    private var timecount: Int = 0
    private var buttons = Array(4) { Array(4) { button } }
    private lateinit var buttonshuffle: Button
    private lateinit var buttonstop: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadViews()
        loadNumbers()
        generateNumbers()
        loadDataToView()
        for (i in 0 until binding.group.childCount){
            buttons[i/4][i%4]!!.isClickable = false
        }
    }

    private fun loadDataToView() {
        emptyX = 3
        emptyY = 3
        for (i in 0 until binding.group.childCount-1){
            buttons[i / 4][i % 4]!!.text = tiles!![i].toString()
            buttons[i/4][i%4]!!.setBackgroundResource(R.drawable.button_default)
        }
        buttons[emptyX][emptyY]!!.text = ""
        buttons[emptyX][emptyY]!!.setBackgroundColor(ContextCompat.getColor(this, R.color.freebutton))

        buttonshuffle.setOnClickListener {
            generateNumbers()
            loadDataToView()
        }
        buttonstop.setOnClickListener {
            if (buttonstop.text == "Stop"){
                timer.cancel()
                buttonstop.text = getString(R.string.exit)
                for (i in 0 until binding.group.childCount){
                    buttons[i/4][i%4]!!.isClickable = false
                    buttonshuffle.isClickable = false
                }
            }else{
                if (buttonstop.text == "Start"){
                loadTimer()
                buttonstop.text = getString(R.string.stop)
                for (i in 0 until binding.group.childCount){
                    buttons[i/4][i%4]!!.isClickable = true
                    }
                }else{
                    val intent = Intent(this,MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun loadTimer() {
        timer.schedule(
                object : TimerTask() {
                    override fun run() {
                        timecount ++
                        val second = timecount % 60
                        val hour = timecount / 3600
                        val minut = (timecount - hour*3600)/60

                        runOnUiThread {
                        binding.tvTimecount.text = String.format("%02d:%02d:%02d",hour,minut,second) }
                    }
                },
                1000,
                1000
        )
    }


    private fun generateNumbers() {
        var n=15
        val random = Random()
        while (n>1){
            val randomInt: Int = random.nextInt(n--)
            val temp: Int = tiles!![randomInt]
            tiles!![randomInt] = tiles!![n]
            tiles!![n] = temp
        }
        if (!isSolvable()){
            generateNumbers()
        }
    }

    private fun isSolvable(): Boolean {
        var count = 0
        for (i in 0..15) {
            for (j in 0..15) {
                if (tiles!![j] > tiles!![i]) {
                    count++
                }
            }
        }
        return count % 2 == 0
    }

    private fun loadNumbers() {
        tiles = IntArray(16)
        for (i in 0 until binding.group.childCount-1){
            tiles!![i] = i+1
        }
    }

    private fun loadViews() {
        group = binding.group
        textViewsteps = binding.tvStepscount
        buttonshuffle = binding.tvShuffle
        buttonstop = binding.tvStop
        for (i in 0 until group!!.childCount){
            buttons[i/4][i%4] = group!!.getChildAt(i) as Button
            Log.d("Main", group!!.childCount.toString())
        }
    }
     fun buttonclick(view: View){
        val button: Button = view as Button
        val x: Int = button.tag.toString()[0] - '0'
        val y: Int = button.tag.toString()[1] - '0'

        if ((abs(emptyX - x) ==1&&(emptyY==y))||(abs(emptyY - y) ==1&&(emptyX==x))){
            buttons[emptyX][emptyY]!!.text = button.text.toString()
            buttons[emptyX][emptyY]!!.setBackgroundResource(R.drawable.button_default)
            button.text = ""
            button.setBackgroundColor(ContextCompat.getColor(this,R.color.freebutton))
            emptyX=x
            emptyY=y
            stepscount ++
            "Steps:$stepscount".also { textViewsteps!!.text = it }
            checkWin()
        }
    }

    private fun checkWin() {
        var iswin = 0
        if (emptyX == 3 && emptyY == 3){
        for (i in 0 until binding.group.childCount-1) {
            if (buttons[i / 4][i % 4]!!.text.toString() == (i + 1).toString()) {
                iswin ++
            } else{
                break
            }
                if (iswin == 15){
                    Toast.makeText(this,"You Win!!!",Toast.LENGTH_SHORT).show()
                    timer.cancel()
                    for (j in 0 until binding.group.childCount-1){
                    buttons[j/4][j%4]!!.isClickable = false
                    }
                    buttonshuffle.isClickable = false
                    buttonstop.text = "Records"
                    buttonstop.setOnClickListener {
                        val intent = Intent(this,RecordActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    }
                    savedata()
                }
            }
        }
    }

    private fun savedata() {
        val mybase = MyBase(this)
        mybase.savelaststep(stepscount)
        mybase.savelasttime(timecount)

        if (mybase.getbesttime2() == 0){
            mybase.savebesttime2(timecount)
            mybase.savebeststep2(stepscount)
        }else{
            if (mybase.getbesttime2() > timecount){
                if (mybase.getbesttime() == 0){
                    mybase.savebesttime(timecount)
                    mybase.savebeststep(stepscount)
                }else if (mybase.getbesttime() > timecount){
                    mybase.savebesttime2(mybase.getbesttime())
                    mybase.savebeststep2(mybase.getbeststep())
                    mybase.savebesttime(timecount)
                    mybase.savebeststep(stepscount)
                }else{
                    mybase.savebesttime2(timecount)
                    mybase.savebeststep2(stepscount)
                }
            }
        }
    }
}

