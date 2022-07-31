package com.hilmihanif.peringatandinibanjir.data

import com.google.firebase.database.IgnoreExtraProperties
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@IgnoreExtraProperties
data class DataCuaca(
    val curah_hujan: Float? = null,
    val kelembaban: Float? = null,
    val suhu:Float? = null,
    val suhu_max:Float? = null,
    val suhu_min:Float? = null,
    val waktu_pengukuran:String? = null
){
    private val indo= Locale("id","ID")

    fun toDateWib():LocalDateTime{

        val text = this.waktu_pengukuran
        val string = text?.substring(14)?.dropLast(6)

        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy HH:mm:ssX",  indo)

        val utcDate = LocalDateTime.parse(string, formatter)
        val wibDate = utcDate.plusHours(7)

        return wibDate
    }

    fun getStringTimeWib():String{
        this.toDateWib().let {
            val formater = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy HH:mm:ss",indo)
            return formater.format(it)
        }

    }


}

@IgnoreExtraProperties
data class DataStatusDBD(
    val status:Int? = null,
    val tanggal:String? = null
)