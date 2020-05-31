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
import com.jaehyeon.flowerstudio.controller.TranslationAPI
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import org.w3c.dom.Document
import org.w3c.dom.Element
import java.util.*
import kotlin.collections.ArrayList

class CameraResultActivity : AppCompatActivity() {

    private var context: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_result)

        val image_view = findViewById<ImageView>(R.id.image_view)
        val flower_name = findViewById<TextView>(R.id.flower_name)
        val flower_context = findViewById<TextView>(R.id.flower_context)
        val btn_close = findViewById<Button>(R.id.btn_close)
        val btn_save = findViewById<Button>(R.id.btn_save)
        val btn_character = findViewById<Button>(R.id.btn_character)

        // Intent 정보 받기
        val fName: String? = intent.getStringExtra("flowerName")
        val fLabel: String = ConvertLabel.convertKor(fName!!)
        if(fLabel == "unknown"){
            Toast.makeText(this, "꽃 인식 실패했어요!", Toast.LENGTH_SHORT).show()
            btn_character.setBackgroundColor(getColor(R.color.gray))
        } else {
//            context = TaskClassifier().execute(fLabel).get()
//            flower_context.text = context
        }

        val test: String = "Azalea"
        context = TaskClassifier().execute(test).get()
        flower_context.text = context

        flower_name.text = fLabel

        val bytes: ByteArray? = intent.getByteArrayExtra("flowerImg")
        val cameraImage = bytes?.size?.let { BitmapFactory.decodeByteArray(bytes, 0, it) }
        val bImage = Bitmap.createScaledBitmap(cameraImage!!, 1440, 2560, false)
        image_view.setImageBitmap(bImage)   // 사진 설정

        // 취소 버튼 이벤트
        btn_close.setOnClickListener {
            Toast.makeText(this, "종료됬어요!", Toast.LENGTH_SHORT).show()
            finish()
        }

        // 사진 저장 버튼 이벤트
        btn_save.setOnClickListener {
            saveImage(bImage)
            Toast.makeText(this, "저장됬어요!", Toast.LENGTH_SHORT).show()
        }

        // 캐릭터화 버튼 이벤트
        btn_character.setOnClickListener {
//            if(fLabel == "unknown"){
//                Toast.makeText(this, "꽃 이미지로 변환해 주세요!", Toast.LENGTH_SHORT).show()
//            } else {
                startActivity(Intent(this, LoadingActivity::class.java)
                    .putExtra("character", "character")
                    .putExtra("flowerName", fLabel)
                    .putExtra("flowerContext", context)
                    .putExtra("flowerImg", bytes)
                )
                finish()
//            }
        }
    }

    private class TaskClassifier : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }
        override fun doInBackground(vararg label: String): String {
            // val test = SearchAPI.search(label)  // 네이버 백과사전 API 검색
            // val link = test.getString("title")   // 검색된 링크를 받음
            //Log.d("123123 label", label[0])
            val link_text = label[0]
            val link2 = "https://en.m.wikipedia.org/wiki/$link_text"
            val doc: org.jsoup.nodes.Document = Jsoup.connect(link2).get()   // 링크로 크롤링
            // println(doc)
            // val element:Elements = doc.select("div[class=size_ct_v2]").select("p")
            val element:Elements = doc.select("div[class=mw-parser-output]").select("p")
            val ie1: ListIterator<org.jsoup.nodes.Element> = element.select("p").listIterator()
            var ts: String = "null"
            while(ts == "null"){
                ts = TranslationAPI.translation(ie1.next().text())
            }
            return ts
        }
        override fun onPostExecute(result: String) {
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