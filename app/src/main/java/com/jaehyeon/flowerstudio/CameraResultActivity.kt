package com.jaehyeon.flowerstudio

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_camera_result.*

class CameraResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        val bytes: ByteArray? = intent.getByteArrayExtra("flowerImg")
        val fName = intent.getStringExtra("flowerName")

        val cameraImage = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
        val image_view = findViewById<ImageView>(R.id.image_view)
        val flower_name = findViewById<TextView>(R.id.flower_name)

        image_view.setImageBitmap(cameraImage)
        flower_name.text = fName

        // 사진 저장 버튼 이벤트
        val btn_save = findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener {
            cameraImage?.let { it1 -> saveImage(it1, "flower") }
            Toast.makeText(this, "저장됬어요!", Toast.LENGTH_SHORT).show()
        }

        // 취소 버튼 이벤트
        val btn_close = findViewById<Button>(R.id.btn_close)
        btn_close.setOnClickListener {
            Toast.makeText(this, "종료됬어요!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 캐릭터화 버튼 이벤트
        val btn_character = findViewById<Button>(R.id.btn_character)
        btn_character.setOnClickListener {
            startActivity(Intent(this, LoadingActivity::class.java)
                .putExtra("character", "character")
                .putExtra("flowerName", fName)
                .putExtra("flowerContext", flower_context.text)
                .putExtra("flowerImg", bytes)
            )
            finish()
        }
    }

    private fun saveImage(image: Bitmap, title: String) {
        MediaStore.Images.Media.insertImage(
            contentResolver,
            image,
            title,
            "Image of $title"
        )
    }
}