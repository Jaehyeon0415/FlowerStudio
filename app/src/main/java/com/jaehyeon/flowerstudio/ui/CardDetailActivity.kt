package com.jaehyeon.flowerstudio.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.activity_card_detail.*

class CardDetailActivity : AppCompatActivity() {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef = database.reference.child("card")
    private val storageRef: StorageReference = FirebaseStorage.getInstance().reference

    var cardId: String? = ""
    var pathRef: StorageReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_detail)

        // 툴바 사용하기
        val toolbar = findViewById<Toolbar>(R.id.cardDetail_dictionary_toolbar)
        setSupportActionBar(toolbar)
        val ab = supportActionBar!!
        ab.setDisplayShowTitleEnabled(false)
        ab.setDisplayHomeAsUpEnabled(true)

        // 툴바 타이틀 설정
        val title = findViewById<TextView>(R.id.cardDetail_dictionary_toolbar_title)
        title.text = getString(R.string.dictionary_title)

        cardDetail_flower_name.text = intent.getStringExtra("cardTitle")
        cardId = intent.getStringExtra("cardId")
        cardDetail_flower_context.text = intent.getStringExtra("cardContext")

        // 이미지 로드
        val image = findViewById<ImageView>(R.id.cardDetail_image_view)
        val cardUid = intent.getStringExtra("uid")
        pathRef = storageRef.child("$cardUid/$cardId")
        pathRef!!.downloadUrl.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // Glide 이용하여 이미지뷰에 로딩
                Glide.with(this)
                    .load(task.result)
                    .override(1024, 980)
                    .into(image)
            } else {
                // URL을 가져오지 못하면 토스트 메세지
                Toast.makeText(this, task.exception!!.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    // 툴바 옵션 생성
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.card_detail_menu, menu)
        return true
    }

    // 툴바에 뒤로가기 버튼
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        android.R.id.home -> {
            finish()
            true
        }
        R.id.delete -> {
            delete()
            Toast.makeText(this, "삭제되었어요!", Toast.LENGTH_SHORT).show()
            finish()
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    // 카드 삭제
    private fun delete(){
        myRef.child(cardId.toString()).ref.removeValue()
        pathRef!!.delete().addOnSuccessListener {
            Log.d("Image delete success!!", "Image delete success!!")
        }.addOnFailureListener {
            throw RuntimeException(it)
        }
    }
}
