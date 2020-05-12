package com.jaehyeon.flowerstudio.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.jaehyeon.flowerstudio.LoginActivity
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.fragment_profile.view.*

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        view.profile_toolbar_title.text = getString(R.string.profile_title)

        // 유저 정보 가져오기
        val user = FirebaseAuth.getInstance().currentUser

        view.profile_user_nickname.text = user?.displayName
        view.profile_user_id.text = user?.email

        view.logout_google.setOnClickListener{
            FirebaseAuth.getInstance().signOut();
            view.context.startActivity(
                Intent(view.context, LoginActivity::class.java)
            )
            Toast.makeText(context, "로그아웃에 성공하였습니다.", Toast.LENGTH_SHORT).show()

        }

        return view
    }
}