package com.example.mylibrary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_data_buku.view.*

class DataAdapter (
        val dataListBuku: ArrayList<DataModel>
        ) : RecyclerView.Adapter<DataAdapter.BukuViewHolder>(){
        class BukuViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_data_buku,parent, false)
                return BukuViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
                val currentItem = dataListBuku[position]

                holder.itemView.tvJudulBuku.text = currentItem.namaBuku
                holder.itemView.tvPenerbit.text = currentItem.penerbit
                holder.itemView.tvTahunTerbit.text = currentItem.tahunTerbit
                holder.itemView.tvPenulis.text = currentItem.penulis

                Glide.with(holder.itemView)
                        .load(currentItem.coverBuku)
                        .placeholder(R.drawable.librarylogo)
                        .error(R.drawable.librarylogo)
                        .centerCrop()
                        .into(holder.itemView.ivCoverBuku)
        }

        override fun getItemCount(): Int {
                return dataListBuku.size
        }
}