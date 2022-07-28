package com.hilmihanif.peringatandinibanjir.ui.home

import android.content.ContentValues
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hilmihanif.peringatandinibanjir.data.DataCuaca

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        this.value = "This is home Fragment"

    }

    private val _data = MutableLiveData<DataCuaca>().apply {
        val database = Firebase.database
        val myRef = database.getReference("data_cuaca")
//        val lastQuery = myRef.child("data_cuaca").orderByKey().limitToLast(1)
//        lastQuery.addListenerForSingleValueEvent(object: ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                Log.d(ContentValues.TAG, "last data:" + snapshot.toString())
//
////                value = snapshot.getValue<DataCuaca>()
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
        val childEventListener =  object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val key = snapshot.key!!
                Log.d(ContentValues.TAG, "onChildAdded:" + key)
                Log.d(ContentValues.TAG, "value on:" + snapshot.toString())
//                do {
//                    Log.d(ContentValues.TAG, "do start" + key)
//                    myRef.child(key).get().addOnSuccessListener {
//                        Log.i("firebase", "Got value :${it.value.toString()}")
//                        value = it.getValue<DataCuaca>()
//
//                    }.addOnFailureListener{
//                        Log.e("firebase", "Error getting data", it)
//                    }
//                }while (value == null)

//                if(snapshot.getValue<DataCuaca>() != null){
//                    value = snapshot.getValue<DataCuaca>()
//                    Log.d(ContentValues.TAG, "value :" + value.toString())
//                }


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(ContentValues.TAG, "error :" + error.toString())

            }
        }

        myRef.addChildEventListener(childEventListener)
    }
    val text: LiveData<String> = _text
    val data: LiveData<DataCuaca> = _data
}