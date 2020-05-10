package com.jaehyeon.flowerstudio.ui.camera

import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.fragment_camera.*


class CameraFragment : Fragment() {

    private val Image_Capture_Code = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

//        capture_btn.setOnClickListener(View.OnClickListener {
//            val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(cInt, Image_Capture_Code)
//        })

        return view
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == Image_Capture_Code) {
//            if (resultCode == RESULT_OK) {
//                val bp = data?.extras!!["data"] as Bitmap?
//                val imgCapture = view?.findViewById(R.id.image_view) as ImageView
//                imgCapture.setImageBitmap(bp)
//            } else if (resultCode == RESULT_CANCELED) {
//                //Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
//            }
//        }
//    }
}