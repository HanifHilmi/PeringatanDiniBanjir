package com.hilmihanif.peringatandinibanjir

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ProgressBar
import com.hilmihanif.peringatandinibanjir.data.ThisAppConst


class SplashActivity : AppCompatActivity() {




    private lateinit var pbSplash: ProgressBar
    private lateinit var mainActivtiyintent : Intent
    private var i:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        pbSplash = findViewById(R.id.pbSplash)
        mainActivtiyintent = Intent(this, Bottom_nav_Activity::class.java)

        val pref = getSharedPreferences(ThisAppConst.PREF, Context.MODE_PRIVATE)
        if (pref.getBoolean(ThisAppConst.NOTIF_BANJIR,true)){
            ThisAppConst.setNotifBanjir(true)
        }

        if (pref.getBoolean(ThisAppConst.NOTIF_DBD,true)){
            ThisAppConst.setNotifDBD(true)
        }

        loading()


    }

    private fun loading(){
        i = pbSplash.progress
        Thread {
            while (i<=100) {

                Handler(Looper.getMainLooper()).post {
                    i += 15
                    pbSplash.progress = i
                    Log.d(ContentValues.TAG,"i = ${i}")

                    if(i >= 100){
                        startActivity(mainActivtiyintent)
                        finish()
                    }
                }

                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()
    }


}