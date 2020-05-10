package com.jaehyeon.flowerstudio.ui.camera

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jaehyeon.flowerstudio.CameraActivity
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.fragment_camera.view.*

class CameraFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_camera, container, false)

        view.capture_btn.setOnClickListener{
            startActivity(Intent(view.context, CameraActivity::class.java))
        }

        return view
    }
}