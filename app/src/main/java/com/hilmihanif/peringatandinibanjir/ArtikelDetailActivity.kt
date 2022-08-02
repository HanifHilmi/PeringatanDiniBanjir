package com.hilmihanif.peringatandinibanjir

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ArtikelDetailActivity : AppCompatActivity() {
    private var mapArtikel:HashMap<String,String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_artikel_detail)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Artikel"


        mapArtikel = intent.getSerializableExtra("Artikel")!! as HashMap<String, String>?
        val judul= findViewById<TextView>(R.id.detailJudul)
        val sumber= findViewById<TextView>(R.id.detailSumber)
        val image = findViewById<ImageView>(R.id.detailImage)
        val text= findViewById<TextView>(R.id.detailText)

        mapArtikel?.let {
            judul.text = it["judul"]
            sumber.text = "Sumber: ${it["sumber"]}"
            Picasso.get().load(it["image"]).into(image)
            text.text = it["text"]
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                text.justificationMode = JUSTIFICATION_MODE_INTER_WORD
            }
        }



    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}