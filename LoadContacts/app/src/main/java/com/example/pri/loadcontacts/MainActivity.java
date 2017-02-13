package com.example.pri.loadcontacts;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static android.R.attr.scaleWidth;

public class MainActivity extends AppCompatActivity {
    private static final int PICK_CONTACT = 1001;
    TextView text1;
    Button button1;
    private static final String CONTACT_ID = ContactsContract.Contacts._ID;
    private static final String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

    private static final String PHONE_NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHONE_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button);
        text1 = (TextView) findViewById(R.id.text);




        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);

            }
        });
    }



        public static ArrayList<String> getAll(Context context) {
            ContentResolver cr = context.getContentResolver();

            Cursor pCur = cr.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    new String[]{PHONE_NUMBER, PHONE_CONTACT_ID},
                    null,
                    null,
                    null
            );
            if(pCur != null){
                if(pCur.getCount() > 0) {
                    HashMap<Integer, ArrayList<String>> phones = new HashMap<>();
                    while (pCur.moveToNext()) {
                        Integer contactId = pCur.getInt(pCur.getColumnIndex(PHONE_CONTACT_ID));
                        ArrayList<String> curPhones = new ArrayList<>();
                        if (phones.containsKey(contactId)) {
                            curPhones = phones.get(contactId);
                        }
                        curPhones.add(pCur.getString(pCur.getColumnIndex(PHONE_NUMBER)));
                        phones.put(contactId, curPhones);
                    }
                    Cursor cur = cr.query(
                            ContactsContract.Contacts.CONTENT_URI,
                            new String[]{CONTACT_ID, HAS_PHONE_NUMBER},
                            HAS_PHONE_NUMBER + " > 0",
                            null,null);
                    if (cur != null) {
                        if (cur.getCount() > 0) {
                            ArrayList<String> contacts = new ArrayList<>();
                            while (cur.moveToNext()) {
                                int id = cur.getInt(cur.getColumnIndex(CONTACT_ID));
                                if(phones.containsKey(id)) {
                                    contacts.addAll(phones.get(id));
                                }
                            }
                            return contacts;
                        }
                        cur.close();
                    }
                }
                pCur.close();
            }
            return null;
        }
    }
