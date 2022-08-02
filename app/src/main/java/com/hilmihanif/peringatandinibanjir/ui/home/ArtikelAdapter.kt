package com.hilmihanif.peringatandinibanjir.ui.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.hilmihanif.peringatandinibanjir.ArtikelDetailActivity
import com.hilmihanif.peringatandinibanjir.R
import com.hilmihanif.peringatandinibanjir.data.DataCuaca
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext

class ArtikelAdapter (val listArtikel:List<HashMap<String,String>>?): RecyclerView.Adapter<ArtikelAdapter.ViewHolder>(){



        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val judul: TextView
            val image: ImageView
            val desc: TextView
            val card:CardView
            val context = itemView.context

            init{
                judul = view.findViewById(R.id.rvJudul)
                image = view.findViewById(R.id.rvImage)
                desc = view.findViewById(R.id.rvDesc)
                card = view.findViewById(R.id.rvArtikelCard)
            }


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.artikel_single_data,parent ,false)
            return ViewHolder(view)

        }

        override fun getItemCount(): Int = listArtikel!!.size

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            listArtikel?.let {
                holder.judul.text = it.get(position).get("judul")
                holder.desc.text = it.get(position).get("text")
                Picasso.get().load(it.get(position).get("image")).into(holder.image)

                holder.card.setOnClickListener {
                    val intent = Intent(holder.context, ArtikelDetailActivity::class.java)
                    intent.putExtra("Artikel", listArtikel[position])
                    holder.context.startActivity(intent)
                }
            }



        }

    }

