package com.hilmihanif.peringatandinibanjir.ui.home

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hilmihanif.peringatandinibanjir.data.DataCuaca
import com.hilmihanif.peringatandinibanjir.databinding.FragmentHomeBinding

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


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        textView = binding.textHome
        suhuTextView =binding.suhuTextView
        kelembabanTextView  = binding.kelembabanTextView
        curahTextView = binding.curahTextView
        statusTextView = binding.statusTextView
        lastUpdateTextView = binding.lastUpdateTextView


        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

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
        val childEventListener =  object: ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val key = snapshot.key!!
                Log.d(ContentValues.TAG, "onChildAdded:" + key)
                Log.d(ContentValues.TAG, "value on:" + snapshot.toString())

                try{
                    snapshot.getValue<DataCuaca>()?.let {
                        val ts = it.suhu.toString()+  "\u2103"
                        val tk = it.kelembaban.toString() + "%RH"
                        val tc = it.curah_hujan.toString() + "mm"
                        suhuTextView.text = ts
                        kelembabanTextView.text = tk
                        curahTextView.text = tc
                        lastUpdateTextView.text = it.waktu_pengukuran
                    }
                }catch (e:Exception){
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
        }

        myRef.addChildEventListener(childEventListener)
    }

    private fun statusBanjir(suhu:Float,kelembaban:Float,curah:Float):String{
        return ""
    }

}