package com.hilmihanif.peringatandinibanjir.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hilmihanif.peringatandinibanjir.R
import com.hilmihanif.peringatandinibanjir.data.DataCuaca
import com.hilmihanif.peringatandinibanjir.data.DataStatusDBD
import com.hilmihanif.peringatandinibanjir.databinding.FragmentHomeBinding
import com.hilmihanif.peringatandinibanjir.ui.history.HistoryAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    lateinit var textView: TextView
    lateinit var suhuTextView:TextView
    lateinit var kelembabanTextView :TextView
    lateinit var curahTextView:TextView
    lateinit var statusTextView:TextView
    lateinit var lastUpdateTextView:TextView
    lateinit var statusDBDTextView:TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        textView = binding.textHome
        suhuTextView =binding.suhuTextView
        kelembabanTextView  = binding.kelembabanTextView
        curahTextView = binding.curahTextView
        statusTextView = binding.statusTextView
        lastUpdateTextView = binding.lastUpdateTextView
        statusDBDTextView = binding.statusDBDTextView

//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

//        homeViewModel.data.observe(viewLifecycleOwner){
//
//            val ts = it.suhu.toString()+  "\u2103"
//            val tk = it.kelembaban.toString() + "%RH"
//            val tc = it.curah_hujan.toString() + "mm"
//            suhuTextView.text = ts
//            kelembabanTextView.text = tk
//            curahTextView.text = tc
//            lastUpdateTextView.text = it.waktu_pengukuran
//        }

        getFirebaseData()



        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFirebaseData(){
        val database = Firebase.database
        val myRef = database.getReference("data_cuaca")
        val newRef = myRef.orderByKey().limitToLast(1)
        val stRef = database.getReference("status_dbd").orderByKey().limitToLast(3)



        newRef.addChildEventListener(object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val key = snapshot.key!!
                Log.d(ContentValues.TAG, "onChildAdded:" + key)
                Log.d(ContentValues.TAG, "value on:" + snapshot.toString())

                try{
                    snapshot.getValue<DataCuaca>()?.let {
                        val ts = it.suhu.toString()+  "\u2103"
                        val tk = it.kelembaban.toString() + "%RH"
                        val tc = it.curah_hujan.toString() + "mm"
                        val sb = statusBanjir()
                        suhuTextView.text = ts
                        kelembabanTextView.text = tk
                        curahTextView.text = tc
                        lastUpdateTextView.text = "Update Terakhir: ${it.getStringTimeWib() } WIB"

                        if(sb == 2){
                            statusTextView.text = "Berpotensi Banjir"
                            statusTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.warning_color,null))
                        }else if(sb == 1){
                            statusTextView.text = "Waspada"
                            statusTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.cautions_color,null))
                        }else{
                            statusTextView.text = "Aman"
                            statusTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.safe_color,null))

                        }

                    }
                }catch (e:Exception){
                    Log.e(ContentValues.TAG,"error "+ e)
                    getFirebaseData()
                }

//
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
        })

        do{
            var error = false

            stRef.addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        var status_sum = 0
                        val mapData = snapshot.getValue<Map<String,DataStatusDBD>>()!!
                        Log.d(ContentValues.TAG, "status map:" +mapData.toString() )

                        for(i in mapData.toList()){
                            status_sum  += i.second.status!!
                        }
                        if(status_sum >= 4){
                            statusDBDTextView.text = "Waspada"
                            statusDBDTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.warning_color,null))

                        }else{
                            statusDBDTextView.text = "Aman"
                            statusDBDTextView.setTextColor(ResourcesCompat.getColor(resources, R.color.safe_color,null))
                        }


                        //Log.d(ContentValues.TAG, "history:" +listData.toString() )
                    }catch (e:Exception){
                        Log.d(ContentValues.TAG, "status error:"+ e.toString())
                        error = true
                    }
                    //Log.d(ContentValues.TAG, "history last:" +query.get().toString() )
                }
            })

        }while(error)







    }





    private fun statusBanjir():Int{

        var rr = 0.0
        var count = 0

        val database = Firebase.database
        val ref = database.getReference("data_cuaca").orderByKey().limitToLast(30)

        do{

            var error = false
            ref.get().addOnSuccessListener {
                try {
                    val listData = it.getValue<Map<String,DataCuaca>>()!!
                        .toList()
                    Log.d(ContentValues.TAG, "status Banjir map:" +listData.toString() )

                    for(i in listData){
                        rr += i.second.curah_hujan!!
                        count++
                    }
                    if(count != 0){
                        rr /= count
                    }

                    Log.d(ContentValues.TAG, "status banjir rr_avg: $rr"  )
                }catch (e:Exception){
                    Log.e(ContentValues.TAG, "status banjir error"+ e.toString())
                    error = true
                }
            }.addOnFailureListener{
                Log.e(ContentValues.TAG, "status banjir failed")
            }
//            ref.addValueEventListener(object: ValueEventListener {
//                override fun onCancelled(error: DatabaseError) {
//                }
//
//                override fun onDataChange(snapshot: DataSnapshot) {
//
//                    try {
//                        val listData = snapshot.getValue<Map<String,DataCuaca>>()!!
//                            .toList()
//                        Log.d(ContentValues.TAG, "status Banjir map:" +listData.toString() )
//
//                        for(i in listData){
//                            rr += i.second.curah_hujan!!
//                            count++
//                        }
//                        if(count != 0){
//                            rr /= count
//                        }
//
//                        Log.d(ContentValues.TAG, "status banjir rr_avg: $rr"  )
//                    }catch (e:Exception){
//                        Log.e(ContentValues.TAG, "status banjir error"+ e.toString())
//                        error = true
//                    }
//                    //Log.d(ContentValues.TAG, "history last:" +query.get().toString() )
//                }
//            })

        }while(error)
        if(rr >= 50){
            return 2
        }else if(rr >= 30){
            return 1
        }else{
            return 0
        }

    }

}