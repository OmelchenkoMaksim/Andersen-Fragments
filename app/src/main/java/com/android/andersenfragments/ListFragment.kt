package com.android.andersenfragments

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginTop

const val UID_EXTRA = "UID_EXTRA"

class ListFragment : Fragment() {

    interface OnListItemClickListener {
        fun onItemClicked(contact: Contact)
    }

    private lateinit var listLayout: LinearLayout
    private lateinit var onListItemClickListener: OnListItemClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onListItemClickListener = context as OnListItemClickListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listLayout = view.findViewById(R.id.list_layout)
        fillList()
    }

    private fun fillList() {
        DataBase.getContacts().forEach { contact ->
            val textView = TextView(context)
            textView.apply {
                text = contact.getNameAndSurname()
                gravity = Gravity.CENTER
                textSize = 20f
                setOnClickListener {
                    onListItemClickListener.onItemClicked(contact)
                }
                listLayout.addView(textView)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = ListFragment()
    }
}