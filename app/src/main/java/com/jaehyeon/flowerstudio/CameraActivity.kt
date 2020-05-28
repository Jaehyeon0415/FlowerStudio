package com.jaehyeon.flowerstudio

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wonderkiln.camerakit.*
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
        checkPermission()
        setCamera()

        cameraView.setFocus(CameraKit.Constants.FOCUS_TAP)
        btn_capture.setOnClickListener{
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
                            .putExtra("check", "camera")

                        startActivity(intent)
                        finish()
                    }
                }).run()
            }
            override fun onVideo(cameraKitVideo: CameraKitVideo) {}
        })
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { // 마시멜로우 버전과 같거나 이상이라면
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
                || checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) !== PackageManager.PERMISSION_GRANTED
            ) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    Toast.makeText(this, "외부 저장소 사용을 위해 읽기/쓰기 필요", Toast.LENGTH_SHORT).show()
                }
                requestPermissions(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    2
                ) //마지막 인자는 체크해야될 권한 갯수
            }
        }
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