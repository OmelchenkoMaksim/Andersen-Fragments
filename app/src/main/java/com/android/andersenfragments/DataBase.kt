package com.android.andersenfragments

import java.util.*

object DataBase {
    private val contactsList = mutableListOf<Contact>()

    init {
        contactsList.add(Contact("Alex", "Navalny", "8-800-555-35-35"))
        contactsList.add(Contact("Vova", "Putin", "+777"))
        contactsList.add(Contact("Barak", "Obama", "+666"))
    }

    fun addContact(contact: Contact) {
        contactsList.add(contact)
    }

    fun removeByUid(uid: UUID): Boolean {
        return contactsList.remove(contactsList.firstOrNull { contact ->
            contact.contactUID == uid
        })
    }

    fun replaceContactByUuid(uuid: UUID, newContact: Contact) {
        if (removeByUid(uuid)) {
            addContact(newContact)
        }
    }

    fun findByUid(uid: UUID): Contact? {
        return contactsList.firstOrNull { contact ->
            contact.contactUID == uid
        }
    }

    fun getContacts(): List<Contact> {
        return contactsList
    }
}