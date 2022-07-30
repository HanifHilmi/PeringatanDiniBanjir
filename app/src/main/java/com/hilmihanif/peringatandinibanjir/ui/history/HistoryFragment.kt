package com.hilmihanif.peringatandinibanjir.ui.history

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import com.hilmihanif.peringatandinibanjir.data.DataCuaca
import com.hilmihanif.peringatandinibanjir.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    lateinit var recyclerView:RecyclerView


    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
//
//        val textView: TextView = binding.textNotifications
//        notificationsViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }
        recyclerView = binding.dataCuacaRecycler
        recyclerView.layoutManager = LinearLayoutManager(context)
        getFirebaseData()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getFirebaseData(){
        val listData = mutableListOf<DataCuaca>()
        val database = Firebase.database
        val myRef = database.getReference("data_cuaca")
        val query = myRef.orderByKey().limitToLast(50)

        do{
            var error = false

            query.addValueEventListener(object: ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    try {
                        val mapData = snapshot.getValue<Map<String,DataCuaca>>()!!
                            .toList()
                            .sortedBy {(key,_)->key}.toMap()
                        Log.d(ContentValues.TAG, "history map:" +mapData.toString() )

                        for(i in mapData){
                            listData.add(mapData.get(i.key)!!)
                        }

                        recyclerView.adapter = HistoryAdapter(listData.toList().asReversed())

                    Log.d(ContentValues.TAG, "history:" +listData.toString() )
                    }catch (e:Exception){
                        Log.d(ContentValues.TAG, "history error:"+ e.toString())
                        error = true
                    }
                    //Log.d(ContentValues.TAG, "history last:" +query.get().toString() )
                }
            })

        }while(error)
    }
}

