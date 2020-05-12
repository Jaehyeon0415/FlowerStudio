package com.jaehyeon.flowerstudio

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

//        val bytes: ByteArray? = intent.getByteArrayExtra("cardImage")
//        val image_uri = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }

//        ch_image_view.setImageBitmap(image_uri)

        val btnChCancel = findViewById<Button>(R.id.btn_chCancel)
        btnChCancel.setOnClickListener {
            Toast.makeText(this, "취소가 되었어요!", Toast.LENGTH_SHORT).show()
            finish()
        }

        val btnCharacterSave = findViewById<Button>(R.id.btn_character_save)
        btnCharacterSave.setOnClickListener {
            Toast.makeText(this, "저장되었어요!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
