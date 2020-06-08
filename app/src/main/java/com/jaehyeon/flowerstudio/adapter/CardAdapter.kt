package com.jaehyeon.flowerstudio.adapter

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.jaehyeon.flowerstudio.CameraActivity
import com.jaehyeon.flowerstudio.R
import com.jaehyeon.flowerstudio.model.Card
import com.jaehyeon.flowerstudio.ui.CardDetailActivity
import kotlinx.android.synthetic.main.item_card.view.*


class CardAdapter(val context: Context, private val cardList: ArrayList<Card>):
    RecyclerView.Adapter<CardAdapter.Holder>() {

    companion object{
        val cardList: ArrayList<Card> = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(cardList[position])
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind (card: Card) {

            val storageRef: StorageReference = FirebaseStorage.getInstance().reference
            val pathRef = storageRef.child("${card.uid}/${card.image}")

            //itemView
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, CardDetailActivity::class.java)
                    .putExtra("cardTitle", card.title)
                    .putExtra("cardContext", card.context)
                    .putExtra("cardId", card.id)
                    .putExtra("uid", card.uid)
                    .putExtra("cardImage", pathRef.toString())
                    .putExtra("url", card.url)
                val bundle: Bundle = ActivityOptions.makeCustomAnimation(itemView.context, R.anim.slide_in_right, R.anim.slide_out_left).toBundle()
                ActivityCompat.startActivity(itemView.context, intent, bundle)
            }

            itemView.item_text.text = card.title

            // 이미지 다운
            pathRef.downloadUrl.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Glide 이용하여 이미지뷰에 로딩
                    Glide.with(itemView.context)
                        .load(task.result)
                        .override(1024, 980)
                        .into(itemView.item_image)
                } else {
                    // URL을 가져오지 못하면 토스트 메세지
                    Toast.makeText(itemView.context, task.exception!!.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount() = cardList.size

}