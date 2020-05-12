package com.jaehyeon.flowerstudio

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.daimajia.numberprogressbar.NumberProgressBar
import com.daimajia.numberprogressbar.OnProgressBarListener
import java.util.*


class LoadingActivity : AppCompatActivity() {

    val LOADING_TIME_OUT:Long = 2000 // 1 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

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
            finish()
        }, LOADING_TIME_OUT)
    }

}
