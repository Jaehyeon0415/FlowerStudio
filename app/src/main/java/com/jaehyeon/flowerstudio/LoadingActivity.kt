package com.jaehyeon.flowerstudio

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.numberprogressbar.NumberProgressBar
import com.daimajia.numberprogressbar.OnProgressBarListener
import java.util.*


class LoadingActivity : AppCompatActivity() {

    val LOADING_TIME_OUT:Long = 2000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val fName = intent.getStringExtra("flowerName")
        val fContext = intent.getStringExtra("flowerContext")

        val check = intent.getStringExtra("character")
        val loadingText = findViewById<TextView>(R.id.loading_text)
        if(check == "character"){
            loadingText.text = getString(R.string.loading_text2)
        }
        val npb = findViewById<NumberProgressBar>(R.id.number_progress_bar);
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

        Handler().postDelayed({
            if(check == "character"){
                startActivity(Intent(this, CharacterActivity::class.java)
                    .putExtra("flowerName", fName)
                    .putExtra("flowerContext", fContext)
                )
                finish()
            }else{
                finish()
            }
        }, LOADING_TIME_OUT)
    }

}
