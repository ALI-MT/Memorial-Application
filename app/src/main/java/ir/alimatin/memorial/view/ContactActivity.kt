package ir.alimatin.memorial.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ir.alimatin.memorial.R
import ir.alimatin.memorial.adapter.ContactAdapter
import ir.alimatin.memorial.model.DataContact
import ir.alimatin.memorial.simpleCamera.extensions.hasPermission
import ir.alimatin.memorial.simpleCamera.extensions.requestPermissionWithRationale
import ir.alimatin.memorial.viewmodel.ContactActivityViewModel
import ir.alimatin.memorial.viewmodel.UserVerifyActivityViewModel
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.activity_direct.ivBack
import kotlinx.android.synthetic.main.activity_timeline.rvList


class ContactActivity : AppCompatActivity() {
    lateinit var contactsViewModel: ContactActivityViewModel
    private lateinit var contInst: Context
    private val CONTACTS_READ_REQ_CODE = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        contactsViewModel =
            ViewModelProvider(this).get(ContactActivityViewModel::class.java)

        contInst = this
        configFabScroll()

        if (hasPermission(Manifest.permission.READ_CONTACTS)) {
            getDataContact()
        } else {
            requestPermissionWithRationale(
                Manifest.permission.READ_CONTACTS,
                CONTACTS_READ_REQ_CODE,
                getString(R.string.contact_permission_rationale)
            )
        }

        ivBack.setOnClickListener {
            finish()
        }
    }

    private fun configFabScroll() {
        rvList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val scrollY: Int = dy
                if (scrollY > 0) {
                    fab_contact.hide()
                } else {
                    fab_contact.show()
                }
            }
        })
    }

    private fun getDataContact() {
        contactsViewModel.fetchContacts().observe(this, Observer {
            initContact(it)
        })
    }

    private fun initContact(list: List<DataContact>) {
        rvList.apply {
            layoutManager = GridLayoutManager(contInst, 1, RecyclerView.VERTICAL, false)
            adapter = ContactAdapter(contInst, list)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CONTACTS_READ_REQ_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getDataContact()
        }
    }
}