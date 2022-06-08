package com.example.mylibrary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_data_buku.*

class DataBuku : AppCompatActivity() {

    private lateinit var dataBukuRecyclerView: RecyclerView
    private lateinit var dataBukuList: ArrayList<DataModel>
    private lateinit var mDbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_buku)

        btnAddBukuBaru.setOnClickListener {
            startActivity(Intent(this,TambahBuku::class.java))
        }

        dataBukuRecyclerView = findViewById(R.id.rvDataBuku)
        dataBukuRecyclerView.layoutManager = LinearLayoutManager(this.baseContext)
        dataBukuRecyclerView.setHasFixedSize(true)

        dataBukuList = ArrayList()
        getListBuku()
    }

    private fun getListBuku() {
        mDbRef = FirebaseDatabase.getInstance().getReference("Buku")

        mDbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    dataBukuList.clear()
                    for (listDataBuku in snapshot.children){
                        val listBuku = listDataBuku.getValue(DataModel::class.java)
                        dataBukuList.add(listBuku!!)
                    }
                    dataBukuRecyclerView.adapter = DataAdapter(dataBukuList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}