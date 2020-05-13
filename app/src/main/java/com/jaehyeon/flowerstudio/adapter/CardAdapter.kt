package com.jaehyeon.flowerstudio.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jaehyeon.flowerstudio.ui.CardDetailActivity
import com.jaehyeon.flowerstudio.R
import com.jaehyeon.flowerstudio.model.Card
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
        holder.bind(cardList[position], context)
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind (card: Card, context: Context) {
            //itemView.
            itemView.setOnClickListener {
//                // image 넘겨줌
//                val bitmap = ((itemView.cardImage).drawable as BitmapDrawable).bitmap
//                val stream = ByteArrayOutputStream()
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//                val image = stream.toByteArray()

                itemView.context.startActivity(
                    Intent(itemView.context,
                        CardDetailActivity::class.java)
                        .putExtra("cardTitle", card.title)
                        .putExtra("cardContext", card.context)
                        .putExtra("cardId", card.id)
//                        .putExtra("cardWriter", card.writer)
//                        .putExtra("cardPrice", card.price)
//                        //.putExtra("cardImage", image)
//                        .putExtra("cardCategory", card.category)
//                        .putExtra("cID", card.id)
                )
            }

            itemView.item_text.text = card.title
            //itemView.cardImage.setImageResource(card.image)
            //itemView.cardID.text = card.id
        }
    }

    override fun getItemCount() = cardList.size

}