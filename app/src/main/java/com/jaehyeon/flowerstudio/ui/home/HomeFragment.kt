package com.jaehyeon.flowerstudio.ui.home

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.jaehyeon.flowerstudio.CameraActivity
import com.jaehyeon.flowerstudio.CameraResultActivity
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        view.home_toolbar_title.text = getString(R.string.app_name)

        view.btn_camera.setOnClickListener{
            val intent = Intent(view.context, CameraActivity::class.java)
            val bundle: Bundle = ActivityOptions.makeCustomAnimation(view.context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle()
            ActivityCompat.startActivity(view.context, intent, bundle)
        }

        val bitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.flower)
//        val scaled: Bitmap = Bitmap.createScaledBitmap(bitmap, 200, 200, true)
        view.home_image.setImageBitmap(bitmap)
        return view
    }
}
