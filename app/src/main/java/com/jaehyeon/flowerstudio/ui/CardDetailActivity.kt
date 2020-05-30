package com.jaehyeon.flowerstudio.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.jaehyeon.flowerstudio.R
import com.jaehyeon.flowerstudio.adapter.CardAdapter
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.android.synthetic.main.fragment_dictionary.*

class CardDetailActivity : AppCompatActivity() {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val myRef = database.reference.child("card")

    var cardId: String? = ""

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

    private fun delete(){
        myRef.child(cardId.toString()).ref.removeValue()
        Toast.makeText(this, "삭제되었어요!", Toast.LENGTH_SHORT).show()
    }
}
