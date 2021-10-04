package com.mehedi.contactmanager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.mehedi.contactmanager.Data.DatabaseHandler;
import com.mehedi.contactmanager.Model.Contact;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayList<String> contactArrayList;
    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);
        contactArrayList = new ArrayList<>();

        DatabaseHandler db = new DatabaseHandler(MainActivity.this);

        Contact jeremy = new Contact();
        jeremy.setName("Jeremy");
        jeremy.setPhoneNumber("9954011542");

        Contact Mehedi = new Contact();
        Mehedi.setName("Mehedi");
        Mehedi.setPhoneNumber("7002515278");

        //db.addContact(Mehedi);

//        Contact c = db.getContact(2);
//        c.setName("Telonter");
//        c.setPhoneNumber("8887776660");

//        db.updateContact(c);
//        Log.d("boss", "onCreate: "+c.getName()+" ,"+c.getPhoneNumber());
        //db.deleteContent(c);
//        db.addContact(new Contact("boss","993933939"));
//        db.addContact(new Contact("James","213986"));
//        db.addContact(new Contact("Greg","098765"));
//        db.addContact(new Contact("Helena","40678765"));
//        db.addContact(new Contact("Carimo","768345"));
//
//        db.addContact(new Contact("Silo","3445"));
//        db.addContact(new Contact("Santos","6665"));
//        db.addContact(new Contact("Litos","5344"));
//        db.addContact(new Contact("Karate","96534"));
//        db.addContact(new Contact("Guerra","158285"));
//        db.addContact(new Contact("Gema","78130"));

        Log.d("count", "onCreate: "+ db.getCount());
        List<Contact> contactList = db.getAllContact();

        for(Contact contact :contactList) {
            Log.d("list", "onCreate: " + contact.getId() + ", " + contact.getName() + " ," + contact.getPhoneNumber());
            contactArrayList.add(contact.getName());
        }
        //array adapter
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                contactArrayList
        );
        //adding to listView
        listView.setAdapter(arrayAdapter);

        //attach event listener to listView

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("list", "onItemClick: "+contactArrayList.get(position));
            }
        });
    }
}