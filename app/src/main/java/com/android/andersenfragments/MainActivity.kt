package com.android.andersenfragments

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity(), ListFragment.OnListItemClickListener,
    DetailsFragment.OnSaveClickListener {

    private var isTablet: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        isTablet = findViewById<View>(R.id.fragment_details_container) != null

        updateMainContainer()
    }

    override fun onItemClicked(contact: Contact) {
        val detailsFragment = DetailsFragment.newInstance(contact)
        if (isTablet) {
            replaceFragmentInContainer(
                detailsFragment,
                R.id.fragment_details_container,
                addToBackStack = false
            )
        } else {
            replaceFragmentInContainer(
                detailsFragment,
                R.id.fragment_container_main,
                addToBackStack = true
            )
        }
    }

    override fun onSaveClicked() {
        if (isTablet) {
            replaceFragmentInContainer(
                ListFragment.newInstance(),
                R.id.fragment_container_main,
                addToBackStack = false
            )
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun updateMainContainer() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_main)

        if (currentFragment == null) {
            replaceFragmentInContainer(
                ListFragment.newInstance(),
                R.id.fragment_container_main,
                addToBackStack = false
            )
        }
    }

    private fun replaceFragmentInContainer(
        fragment: Fragment,
        @IdRes containerId: Int,
        addToBackStack: Boolean
    ) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction
            .replace(containerId, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment.toString())
        }

        transaction.commit()
    }
}