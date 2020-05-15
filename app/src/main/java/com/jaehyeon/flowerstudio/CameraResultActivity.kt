package com.jaehyeon.flowerstudio

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jaehyeon.flowerstudio.ui.home.HomeFragment
import kotlinx.android.synthetic.main.activity_camera_result.*
import java.net.URI

class CameraResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        val bytes: ByteArray? = intent.getByteArrayExtra("flowerImg")

        val cameraImage = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
        val image_view = findViewById<ImageView>(R.id.image_view)
        image_view.setImageBitmap(cameraImage)

        // 사진 저장 버튼 이벤트
        val btn_save = findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener {
            val uri = cameraImage?.let { it1 -> saveImage(it1, "flower") }
            Toast.makeText(this, "저장됬어요~", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 취소 버튼 이벤트
        val btn_close = findViewById<Button>(R.id.btn_close)
        btn_close.setOnClickListener {
            Toast.makeText(this, "종료됬어 씨발놈아", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 캐릭터화 버튼 이벤트
        val btn_character = findViewById<Button>(R.id.btn_character)
        btn_character.setOnClickListener {
            startActivity(Intent(this, LoadingActivity::class.java)
                .putExtra("character", "character")
                .putExtra("flowerName", flower_name.text)
                .putExtra("flowerContext", flower_context.text)
                .putExtra("flowerImg", bytes)
            )
            finish()
        }
    }

    private fun saveImage(image: Bitmap, title: String) {
        val savedImageURL = MediaStore.Images.Media.insertImage(
            contentResolver,
            image,
            title,
            "Image of $title"
        )
    }
}