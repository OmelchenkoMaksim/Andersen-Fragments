package com.android.andersenfragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import java.util.*

class DetailsFragment : Fragment() {

    interface OnSaveClickListener {
        fun onSaveClicked()
    }

    private lateinit var nameEditText: EditText
    private lateinit var surnameEditText: EditText
    private lateinit var numberEditText: EditText
    private lateinit var saveButton: Button

    private lateinit var contactUuid: UUID

    private lateinit var onSaveClickListener: OnSaveClickListener

    override fun onAttach(context: Context) {
        super.onAttach(context)

        onSaveClickListener = context as OnSaveClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contactUuid = requireArguments().getSerializable(UID_EXTRA) as? UUID
            ?: throw RuntimeException("UUID must be passed.")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = view.findViewById(R.id.name_field)
        surnameEditText = view.findViewById(R.id.surname_field)
        numberEditText = view.findViewById(R.id.number_field)
        saveButton = view.findViewById(R.id.button_apply_changes)
        saveButton.text = "СОХРАНИТЬ ИЗМЕНЕНИЯ?"
        saveButton.setOnClickListener { saveContact() }

        fillDetails()
    }

    private fun fillDetails() {
        DataBase.findByUid(contactUuid)?.apply {
            nameEditText.setText(name)
            surnameEditText.setText(surname)
            numberEditText.setText(phoneNumber)
        }
    }

    private fun saveContact() {
        DataBase.replaceContactByUuid(
            contactUuid,
            Contact(
                nameEditText.text.toString(),
                surnameEditText.text.toString(),
                numberEditText.text.toString()
            )
        )

        onSaveClickListener.onSaveClicked()
    }

    companion object {

        fun newInstance(contact: Contact): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(UID_EXTRA, contact.contactUID)
                }
            }
        }
    }
}