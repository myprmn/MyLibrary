package com.example.mylibrary

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import android.util.Log
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_tambah_buku.*
import kotlinx.android.synthetic.main.fragment_data_buku.*
import java.net.URL
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class TambahBuku : AppCompatActivity() {

   lateinit var imageUri: Uri

    private lateinit var executor: Executor
    private lateinit var handler: Handler

    lateinit var uploadTask: UploadTask
    lateinit var fStor: FirebaseStorage
    lateinit var sRef: StorageReference

    private lateinit var mDBRef: DatabaseReference

    var uid: UUID = UUID.randomUUID()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_buku)

        mDBRef = FirebaseDatabase.getInstance().reference
        fStor = FirebaseStorage.getInstance("gs://mylibrary-b5098.appspot.com/")
        sRef = fStor.reference

        btnGambar.setOnClickListener {
            selectImage()
        }

        btnAddBuku.setOnClickListener {
            uploadBuku(imageUri)
        }
        executor = Executors.newSingleThreadExecutor()
        handler = Handler(Looper.getMainLooper())
    }
    private fun selectImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            imageUri = data.data!!
            ivGambar.setImageURI(imageUri)
        }
    }
    private fun addBooktoDB(
        namaBuku: String,
        penerbit: String,
        tahunTerbit: String,
        penulis: String,
        coverBook: String
    ){
        val nameUid = "${namaBuku} - ${uid}"
        mDBRef.child("Buku").child(nameUid)
            .setValue(DataModel(namaBuku,penerbit,tahunTerbit,penulis,coverBook))
    }

    private fun uploadBuku(imageUri: Uri) {
        var namaBuku = etAddJudulBuku.text.toString()
        var penerbit = etAddPenerbit.text.toString()
        var tahunTerbit = etAddTahunTerbit.text.toString()
        var penulis = etAddPenulis.text.toString()

        val uid = "$namaBuku - $uid"

        val imageRef = sRef.child("upload/images/${uid}.jpg")
        uploadTask = imageRef.putFile(imageUri)
        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                val coverBook = downloadUri.toString()

                Log.d("URL", downloadUri.toString())
                ivGambar.setImageResource(R.drawable.book)
                btnGambar.setOnClickListener { selectImage() }
                addBooktoDB(namaBuku,penerbit,tahunTerbit,penulis,coverBook)
                Toast.makeText(this, "gambar berhasil di upload", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Gambar gagal di upload", Toast.LENGTH_LONG).show()
            }
        }
    }
   /* private fun getBitmapFromUrl (src: String){
        executor.execute {
            val imageURL = src
            try {
                val ins = URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(ins)

                handler.post{
                    ivGambar.setImageBitmap(image)
                }
            } catch (e: Exception){
                e.printStackTrace()
            }
        }
    }*/
}



