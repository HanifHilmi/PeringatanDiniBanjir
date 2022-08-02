package com.hilmihanif.peringatandinibanjir.data

import android.content.ContentValues
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging

class ThisAppConst {
    companion object{
        val PREF = "pref"
        val NOTIF_BANJIR ="notif_banjir"
        val NOTIF_DBD = "notif_dbd"

        fun setNotifBanjir(checked: Boolean){
            if (checked){
                Firebase.messaging.subscribeToTopic("Banjir")
                    .addOnCompleteListener { task ->
                        var msg = "Subscribed to Banjir"
                        if (!task.isSuccessful) {
                            msg = "Subscribe failed"
                        }
                        Log.d(ContentValues.TAG, msg)
                    }
            }else{
                Firebase.messaging.unsubscribeFromTopic("Banjir")
            }

        }

        fun setNotifDBD(checked: Boolean){
            if(checked){
                Firebase.messaging.subscribeToTopic("DBD")
                    .addOnCompleteListener { task ->
                        var msg = "Subscribed to DBD"
                        if (!task.isSuccessful) {
                            msg = "Subscribe failed"
                        }
                        Log.d(ContentValues.TAG, msg)

                    }
            }else{
                Firebase.messaging.unsubscribeFromTopic("DBD")
            }
        }
    }

}