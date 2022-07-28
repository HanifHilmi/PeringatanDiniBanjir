package com.hilmihanif.peringatandinibanjir.data

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DataCuaca(
    val curah_hujan: Float? = null,
    val kelembaban: Float? = null,
    val suhu:Float? = null,
    val suhu_max:Float? = null,
    val suhu_min:Float? = null,
    val waktu_pengukuran:String? = null
)