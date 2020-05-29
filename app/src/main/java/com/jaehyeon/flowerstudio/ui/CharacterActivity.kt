package com.jaehyeon.flowerstudio.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
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

        // Intent 값 받아오기
        val fName = intent.getStringExtra("flowerName")
        val fContext = intent.getStringExtra("flowerContext")
        val bytes: ByteArray? = intent.getByteArrayExtra("flowerImg")
        val cameraImage = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
        val ch_image_view = findViewById<ImageView>(R.id.ch_image_view)
        val ch_flower_name = findViewById<TextView>(R.id.ch_flower_name)

        // 이미지와 이름 설정
        ch_image_view.setImageBitmap(cameraImage)
        ch_flower_name.text = fName

        // 취소 버튼 설정
        val btnChCancel = findViewById<Button>(R.id.btn_chCancel)
        btnChCancel.setOnClickListener {
            Toast.makeText(this, "취소가 되었어요!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 캐릭터 이미지 도감에 저장
        val btnCharacterSave = findViewById<Button>(R.id.btn_character_save)
        btnCharacterSave.setOnClickListener {
            // 도감 카드 추가
            addCard(fName!!, fContext!!)
            Toast.makeText(this, "저장되었어요!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    /**
     *  생성된 카드 Firebase에 저장
     **/

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
