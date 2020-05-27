package com.jaehyeon.flowerstudio

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.KeyEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.numberprogressbar.NumberProgressBar
import com.daimajia.numberprogressbar.OnProgressBarListener
import com.jaehyeon.flowerstudio.tensorflow.Classifier
import com.jaehyeon.flowerstudio.tensorflow.Classifier.Recognition
import com.jaehyeon.flowerstudio.tensorflow.TensorFlowFlowerClassifier
import com.jaehyeon.flowerstudio.ui.CharacterActivity
import java.util.*
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class LoadingActivity : AppCompatActivity() {

    private val LOADING_TIME_OUT:Long = 3000 // 3 sec
    private var classifyByte: Bitmap? = null
    private var results: List<Recognition>? = null

    private val MODEL_PATH = "model.tflite"
    private val LABEL_PATH = "labels.txt"
    private val INPUT_SIZE = 299

    private val executor: Executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val fName = intent.getStringExtra("flowerName")
        val fContext = intent.getStringExtra("flowerContext")
        val fImage: ByteArray? = intent.getByteArrayExtra("flowerImg")

        val image: Bitmap? = fImage?.size?.let { BitmapFactory.decodeByteArray(fImage, 0, it) }

        classifyByte = image?.let { Bitmap.createScaledBitmap(it, 299, 299, false) }

        val check = intent.getStringExtra("character")
        val loadingText = findViewById<TextView>(R.id.loading_text)

        if(check == "character"){
            loadingText.text = getString(R.string.loading_text2)
        }

        // ProgressBar 설정
        val npb = findViewById<NumberProgressBar>(R.id.number_progress_bar)
        val timer = Timer()
        npb.setOnProgressBarListener(OnProgressBarListener { current, max ->
            if (current == max) {
                timer.cancel()
            }
        })
        timer.schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread { npb.incrementProgressBy(1) }
            }
        }, 5, 15)

        // Activity TimeOut
        Handler().postDelayed({
            if(check == "character"){
                startActivity(Intent(this, CharacterActivity::class.java)
                    .putExtra("flowerName", fName)
                    .putExtra("flowerContext", fContext)
                    .putExtra("flowerImg", fImage)
                    // .putExtra("result", results.toString())
                )
                finish()
            }else{
                startActivity(Intent(this, CameraResultActivity::class.java)
                    .putExtra("flowerName", results!![0].title.toString())
                    .putExtra("flowerContext", fContext)
                    .putExtra("flowerImg", fImage)
                )
                finish()
            }
        }, LOADING_TIME_OUT)

        initTensorFlowAndLoadModel()
    }

    private fun initTensorFlowAndLoadModel() {
        executor.execute(Runnable {
            try {
                val classifier: Classifier = TensorFlowFlowerClassifier.create(
                    assets,
                    MODEL_PATH,
                    LABEL_PATH,
                    INPUT_SIZE
                )
                results = classifier.recognizeImage(classifyByte!!)
                Log.d("123123 results", results!![0].title.toString())
            } catch (e: Exception) {
                throw RuntimeException("Error initializing TensorFlow!", e)
            }
        })
    }

    // 로딩화면에서 뒤로가기 막기
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            false
        } else super.onKeyDown(keyCode, event)
    }

}