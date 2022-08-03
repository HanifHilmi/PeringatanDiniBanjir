package com.hilmihanif.peringatandinibanjir

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Tentang Kami"

        val versionText:TextView = findViewById(R.id.versionCode)

        versionText.text = "Version Build ${BuildConfig.VERSION_CODE} : ${BuildConfig.VERSION_NAME}"


    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}