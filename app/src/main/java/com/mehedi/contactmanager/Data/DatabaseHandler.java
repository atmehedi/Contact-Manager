package com.mehedi.contactmanager.Data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mehedi.contactmanager.Model.Contact;
import com.mehedi.contactmanager.R;
import com.mehedi.contactmanager.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }
//creating table content and table
    @Override
    public void onCreate(SQLiteDatabase db) {
String CREATE_CONTACT_TABLE = "CREATE TABLE "+Util.TABLE_NAME+" ("+
        Util.KEY_ID+" INTEGER PRIMARY KEY,"+
        Util.KEY_NAME+" TEXT, "+Util.KEY_PHONE_NUMBER+" TEXT"+")";

//execution
       db.execSQL(CREATE_CONTACT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    String DROP_TABLE = String.valueOf(R.string.dp_drop);
    db.execSQL(DROP_TABLE, new String[]{Util.TABLE_NAME});
        //create table again
        onCreate(db);
    }

    //crud operation

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues value = new ContentValues();
        value.put(Util.KEY_NAME,contact.getName());
        value.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        //insert data into table

        db.insert(Util.TABLE_NAME,null,value);

        Log.d("database handler", "addContact: "+"item added");

        //close db connection
        db.close();
    }

    //get contacts
    public Contact getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME,new String[]{Util.KEY_ID,Util.KEY_NAME,Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?",new String[]{String.valueOf(id)},null,null,null);

        if (cursor != null)
            cursor.moveToFirst();

            Contact contact = new Contact();
            contact.setId(Integer.parseInt(cursor.getString(0)));
            contact.setName(cursor.getString(1));
            contact.setPhoneNumber(cursor.getString(2));

            return contact;

    }

    public List<Contact> getAllContact() {
        List<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //select all contacts

        String selectAll = "SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(selectAll,null);

        //get the data by loop

        if (cursor.moveToFirst()){
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                //add contact list
                contactList.add(contact);

            }while(cursor.moveToNext());

        }
        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME,contact.getName());
        values.put(Util.KEY_PHONE_NUMBER,contact.getPhoneNumber());

        //update the row
//update (table_name where id = content_id);
        return db.update(Util.TABLE_NAME,values,Util.KEY_ID +" =?",new String[]{String.valueOf(contact.getId())});

    }

    public void deleteContent(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

       db.delete(Util.TABLE_NAME,Util.KEY_ID + "=?",
               new String[]{String.valueOf(contact.getId())});
       db.close();
    }

    public int getCount() {
        String count = "SELECT * FROM "+Util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(count,null);
        return cursor.getCount();
    }

}
