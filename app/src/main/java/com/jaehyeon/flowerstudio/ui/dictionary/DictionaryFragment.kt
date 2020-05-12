package com.jaehyeon.flowerstudio.ui.dictionary

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.jaehyeon.flowerstudio.R
import kotlinx.android.synthetic.main.fragment_dictionary.view.*

class DictionaryFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_dictionary, container, false)

        view.dictionary_toolbar_title.text = getString(R.string.dictionary_title)

        return view
    }
}
