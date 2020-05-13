package com.jaehyeon.flowerstudio.ui.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jaehyeon.flowerstudio.R
import com.jaehyeon.flowerstudio.adapter.CardAdapter
import com.jaehyeon.flowerstudio.adapter.CardAdapter.Companion.cardList
import com.jaehyeon.flowerstudio.model.Card
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.cardDetail_dictionary_toolbar_count


class DictionaryFragment : Fragment() {

    private var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private var myRef = database.reference.child("card")

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionary, container, false)
        val mCardAdapter = CardAdapter(
            view.context,
            cardList
        )

//        // 툴바 타이틀 설정
//        dictionary_toolbar_title.text = getString(R.string.dictionary_title)

        // 도감 카드 초기화
        cardList.clear()

        // 도감 카드리스트 도출
        myRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                for(item in p0.children){
                    val value = item.getValue(Card::class.java)
                    if(value != null){
                        cardList.add(value)
                    }
                    (view.recyclerView.adapter as CardAdapter).notifyDataSetChanged()
                }
            }

            override fun onCancelled(p0: DatabaseError) {}
        })


        view.recyclerView.adapter = mCardAdapter

        val numberOfColumns = 3
        val lm = GridLayoutManager(view.context, numberOfColumns)
        view.recyclerView.layoutManager = lm
        view.recyclerView.setHasFixedSize(true)

        return view
    }
}
