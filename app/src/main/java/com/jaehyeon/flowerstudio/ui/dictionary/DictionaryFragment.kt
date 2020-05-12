package com.jaehyeon.flowerstudio.ui.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.jaehyeon.flowerstudio.R
import com.jaehyeon.flowerstudio.adapter.CardAdapter
import com.jaehyeon.flowerstudio.adapter.CardAdapter.Companion.cardList
import com.jaehyeon.flowerstudio.model.Card
import kotlinx.android.synthetic.main.activity_card_detail.*
import kotlinx.android.synthetic.main.fragment_dictionary.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.*
import kotlinx.android.synthetic.main.fragment_dictionary.view.cardDetail_dictionary_toolbar_count


class DictionaryFragment : Fragment() {

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


        cardList.add(Card("민들레"))
        cardList.add(Card("해바라기"))
        cardList.add(Card("장미"))
        cardList.add(Card("할미꽃"))
        cardList.add(Card("데이지"))
        cardList.add(Card("튤립"))
        cardList.add(Card("네펜데스"))
        cardList.add(Card("카네이션"))
        cardList.add(Card("개나리"))
        cardList.add(Card("봉숭아"))
        cardList.add(Card("영산이"))

        view.recyclerView.adapter = mCardAdapter

        val numberOfColumns = 3
        val lm = GridLayoutManager(view.context, numberOfColumns)
        view.recyclerView.layoutManager = lm
        view.recyclerView.setHasFixedSize(true)

        return view
    }
}
