package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var secondsElapsed: Int = 0
    private var seconds: Int = 0
    private val tag = "MainActivity"
    private lateinit var textSecondsElapsed: TextView
    private var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.text = getString(R.string.second_elapsed, secondsElapsed++)
            }
        }
    }
    private lateinit var sharedPreferences: SharedPreferences

    /* override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.run {putInt("timeStop",secondsElapsed)}
        Log.i(tag,"MainActivity InstanceSaved")
    }
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState.run { secondsElapsed = getInt("timeStop")}
        Log.i(tag,"MainActivity InstanceRestore")
    }

         */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        sharedPreferences = getSharedPreferences("Main", Context.MODE_PRIVATE)
        Log.i(tag, "MainActivity: created")
    }

    override fun onStop() {
        super.onStop()
        with(sharedPreferences.edit()){
            putInt("timeStop",secondsElapsed)
            apply()
        }
        Log.i(tag, "MainActivity: stopped")
    }
    override fun onStart() {
        super.onStart()
        //secondsElapsed = seconds
        secondsElapsed = sharedPreferences.getInt("timeStop",0)
        with(sharedPreferences.edit()){
            remove("timeStop")
        }
        Log.i(tag, "MainActivity: started")
    }


    override fun onPause() {
        super.onPause()
        Log.i(tag, "MainActivity: Paused")

    }
    override fun onResume() {
        super.onResume()
        Log.i(tag, "MainActivity: resumed")
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.i(tag, "MainActivity: destroyed")
    }
}