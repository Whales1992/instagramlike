package com.example.instagramlike.fragments

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramlike.R
import kotlinx.android.synthetic.main.feed_fragment.view.*


public class AddFeedFragment : Fragment() {

    companion object {
        fun newInstance() = AddFeedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val l = inflater.inflate(R.layout.feed_fragment, container, false) as ConstraintLayout

        l.message.setText("HELLO")
        l.message.setTextColor(resources.getColor(R.color.primary_text_default_material_dark))
        return l
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

}
