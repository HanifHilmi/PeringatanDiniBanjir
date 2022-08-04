package com.hilmihanif.peringatandinibanjir

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintSet


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Tentang Kami"

        val versionText:TextView = findViewById(R.id.versionCode)
        val igLayout: View = findViewById(R.id.ig_layout)

        versionText.text = "Version Build ${BuildConfig.VERSION_CODE} : ${BuildConfig.VERSION_NAME}"
        igLayout.setOnClickListener {
            val url= "http://www.instagram.com/kknstmkg_unit4"
            try{
                val uri:Uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(uri)
                intent.setPackage("com.instagram.android")
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }catch (a:ActivityNotFoundException){
                val uri:Uri = Uri.parse(url)
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(uri)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}