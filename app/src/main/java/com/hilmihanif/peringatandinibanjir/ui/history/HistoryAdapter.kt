package com.hilmihanif.peringatandinibanjir.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hilmihanif.peringatandinibanjir.R
import com.hilmihanif.peringatandinibanjir.data.DataCuaca

class HistoryAdapter(private val listDataCuaca:List<DataCuaca>?): RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {


    class ViewHolder(view: View) :RecyclerView.ViewHolder(view){
        val rvSuhu:TextView
        val rvKelembaban:TextView
        val rvTime:TextView
        val rvCurah:TextView

        init{
            rvSuhu = view.findViewById(R.id.rVSuhu)
            rvKelembaban = view.findViewById(R.id.rvKelembaban)
            rvCurah = view.findViewById(R.id.rvCurah)
            rvTime = view.findViewById(R.id.rvTime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_data_cuaca_recycler,parent ,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listDataCuaca!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        listDataCuaca?.let {
            holder.rvCurah.text = it.get(position).curah_hujan.toString()
            holder.rvSuhu.text = it.get(position).suhu.toString()
            holder.rvKelembaban.text = it.get(position).kelembaban.toString()
            holder.rvTime.text = it.get(position).waktu_pengukuran
        }

    }
}