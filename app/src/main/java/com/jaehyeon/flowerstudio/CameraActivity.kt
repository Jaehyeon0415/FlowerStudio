package com.jaehyeon.flowerstudio

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View.FOCUSABLE_AUTO
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wonderkiln.camerakit.*
import com.wonderkiln.camerakit.CameraKit.Constants.FACING_BACK
import java.io.ByteArrayOutputStream


class CameraActivity : AppCompatActivity() {

    private lateinit var cameraView: CameraView
    private lateinit var btn_capture: ImageView
    private lateinit var text_camera_how: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        cameraView = findViewById(R.id.camera)
        btn_capture = findViewById(R.id.btn_capture)
        text_camera_how = findViewById(R.id.text_camera_how)

        setCamera()
        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP)
        btn_capture.setOnClickListener{
//            cameraView.setFocus(CameraKit.Constants.FOCUS_CONTINUOUS)
            cameraView.captureImage()
            runOnUiThread { text_camera_how.text = "촬영중 입니다. 잠시만 기다리세요." }
        }
    }

    private fun setCamera(){
        cameraView.addCameraKitListener(object : CameraKitEventListener {
            override fun onEvent(cameraKitEvent: CameraKitEvent) {}
            override fun onError(cameraKitError: CameraKitError) {}
            override fun onImage(cameraKitImage: CameraKitImage) {
                Thread(Runnable {
                    cameraView.setFocus(CameraKit.Constants.FOCUS_CONTINUOUS)
                    if(cameraKitImage.bitmap != null) {
                        val bitmap = Bitmap.createScaledBitmap(cameraKitImage.bitmap, 800, 600, false)
                        val stream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

                        val fImage = stream.toByteArray()
                        val intent = Intent(this@CameraActivity, LoadingActivity::class.java)
                        intent
                            .putExtra("flowerImg", fImage)

                        startActivity(intent)
                        finish()
                    }
                }).run()
            }
            override fun onVideo(cameraKitVideo: CameraKitVideo) {}
        })
    }

    override fun onResume() {
        super.onResume()
        cameraView.start()
    }

    override fun onPause() {
        cameraView.stop()
        super.onPause()
    }
}