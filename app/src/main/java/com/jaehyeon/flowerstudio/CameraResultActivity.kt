package com.jaehyeon.flowerstudio

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.jaehyeon.flowerstudio.controller.ConvertLabel
import com.jaehyeon.flowerstudio.controller.SearchAPI
import java.util.*

class CameraResultActivity : AppCompatActivity() {

    private var context: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        val image_view = findViewById<ImageView>(R.id.image_view)
        val flower_name = findViewById<TextView>(R.id.flower_name)
        val flower_context = findViewById<TextView>(R.id.flower_context)

        // Intent 정보 받기
        val fName: String? = intent.getStringExtra("flowerName")
        val fLabel: String = ConvertLabel.ConvertKor(fName!!)
        if(fLabel == "unknown"){Toast.makeText(this, "꽃 인식 실패했어요!", Toast.LENGTH_SHORT).show()}
        flower_name.text = fLabel

        val bytes: ByteArray? = intent.getByteArrayExtra("flowerImg")
        val cameraImage = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
        val bImage = Bitmap.createScaledBitmap(cameraImage!!, 1440, 2560, false)
        // 사진 설정
        image_view.setImageBitmap(bImage)

        context = TaskClassifier().execute(fLabel).get()
        flower_context.text = context

        // 취소 버튼 이벤트
        val btn_close = findViewById<Button>(R.id.btn_close)
        btn_close.setOnClickListener {
            Toast.makeText(this, "종료됬어요!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 사진 저장 버튼 이벤트
        val btn_save = findViewById<Button>(R.id.btn_save)
        btn_save.setOnClickListener {
            saveImage(bImage)
            Toast.makeText(this, "저장됬어요!", Toast.LENGTH_SHORT).show()
        }

        // 캐릭터화 버튼 이벤트
        val btn_character = findViewById<Button>(R.id.btn_character)
        btn_character.setOnClickListener {
            startActivity(Intent(this, LoadingActivity::class.java)
                .putExtra("character", "character")
                .putExtra("flowerName", fLabel)
                .putExtra("flowerContext", context)
                .putExtra("flowerImg", bytes)
            )
            finish()
        }
    }

    private class TaskClassifier : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg label: String): String {
            val test = SearchAPI.search(label)
            Log.d("123123 test!!", test)
            return test
        }

        override fun onPostExecute(result: String) {
            Log.d("123123 ddddd", result)
            super.onPostExecute(result)
        }
    }

    // 사진 기기에 저장
    private fun saveImage(image: Bitmap) {
        MediaStore.Images.Media.insertImage(
            contentResolver,
            image,
            UUID.randomUUID().toString(),
            "Image of $title"
        )
    }
}