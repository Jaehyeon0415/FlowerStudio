package com.jaehyeon.flowerstudio.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : AppCompatActivity() {

    private var database: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val user = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

//        val bytes: ByteArray? = intent.getByteArrayExtra("cardImage")
//        val image_uri = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
//        ch_image_view.setImageBitmap(image_uri)

        val fName = intent.getStringExtra("flowerName")
        val fContext = intent.getStringExtra("flowerContext")
        val fImage = intent?.getStringExtra("flowerImg")

        if (fImage != null) {
            ch_image_view.setImageURI(fImage.toUri())
        }

        val btnChCancel = findViewById<Button>(R.id.btn_chCancel)
        btnChCancel.setOnClickListener {
            Toast.makeText(this, "취소가 되었어요!", Toast.LENGTH_SHORT).show()
            finish()
        }

        val btnCharacterSave = findViewById<Button>(R.id.btn_character_save)
        btnCharacterSave.setOnClickListener {
            // 도감 카드 추가
            addCard(fName!!, fContext!!)
            Toast.makeText(this, "저장되었어요!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun addCard(fName: String, fContext: String){

        val uid = user?.uid
        val myRef = database.child("card").push()
        val key = myRef.key

        myRef.child("uid").setValue(uid.toString())
        myRef.child("id").setValue(key)
        myRef.child("title").setValue(fName)
        myRef.child("context").setValue(fContext)

    }
}
