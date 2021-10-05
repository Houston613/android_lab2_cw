package ru.spbstu.icc.kspt.lab2.continuewatch

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    var seconds: Int = 0
    val tag = "MainActivity"
    lateinit var textSecondsElapsed: TextView


    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            textSecondsElapsed.post {
                textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
            }
        }
    }
    lateinit var sharedPreferences: SharedPreferences
    /*(override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("timeStop",secondsElapsed)
        Log.i(tag,"MainActivity InstanceSaved")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt("time")
        Log.i(tag,"MainActivity InstanceRestore")
    }


     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        backgroundThread.start()
        sharedPreferences = getSharedPreferences("Main",Context.MODE_PRIVATE)
        Log.i(tag, "MainActivity: created")
    }
    override fun onStop() {
        super.onStop()
        with(sharedPreferences.edit()){
            putInt("timeStop",secondsElapsed)
            apply()
        }
        Log.i(tag, "MainActivity: created")
    }
    override fun onStart() {
        super.onStart()
        secondsElapsed = seconds
        secondsElapsed = sharedPreferences.getInt("timeStop",0)
        with(sharedPreferences.edit()){
            remove("timeStop")
        }
        Log.i(tag, "MainActivity: started")
    }

    override fun onPause() {
        super.onPause()
        //seconds = secondsElapsed
        Log.i(tag, "MainActivity: Paused")

    }
    override fun onResume() {
        super.onResume()
        Log.i(tag, "MainActivity: resumed")
    }
}
