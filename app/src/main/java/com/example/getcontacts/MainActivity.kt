package com.example.getcontacts

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.app.ActivityCompat
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.widget.TextView;

class MainActivity : AppCompatActivity() {

    data class contact(val id : String = "", var data : String = "")

    private val all_contacts = View.OnClickListener {
        // CHECK PERMISSIONS
        val cont: TextView = findViewById(R.id.Contacts)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.READ_CONTACTS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // GET ALL CONTACTS FROM TELEPHONE BOOK
            val cursor = this.contentResolver.query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                null
            )
            var id : Int = 0
            val arr = arrayListOf<String>()
            cursor?.let {
                while (cursor.moveToNext()) {
                    id += 1
                    val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
                    val phone = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val curContact = contact()
                    val res : String = "$id $name $phone\n"
                    //curContact.data = res
                    arr.add(res)
                }
            }

            for (item in arr) {
                cont.text = cont.text.toString() + item
            }

            cursor?.close()
        } else {
            // IF PERMISSION NOT ARE FOND, THEN REQUEST
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                1
            )
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.getContact);
        button.setOnClickListener(all_contacts);
    }

}