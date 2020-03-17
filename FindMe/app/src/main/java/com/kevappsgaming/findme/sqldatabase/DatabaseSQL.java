package com.kevappsgaming.findme.sqldatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kevappsgaming.findme.utils.ContactInformation;

import java.util.ArrayList;

public class DatabaseSQL extends SQLiteOpenHelper {

    private static final String DATABASE = "FINDME_DB";

    // EXISTING TABLES IN THE DATABASE
    private static final String USERINFO = "userinfotable";
    private static final String CONTACTS = "contactstable";
    private static final String RECEIVER = "chatreceivertable";

    // ATTRIBUTES OF THE USERINFO TABLE
    private static final String UID = "uid";
    private static final String FIRSTNAME = "firstname";
    private static final String LASTNAME = "lastname";
    private static final String EMAIL = "email";

    // ATTRIBUTES OF THE CONTACTS TABLE
    private static final String CONTACT_UID = "uid";
    private static final String CONTACT_FIRSTNAME = "firstname";
    private static final String CONTACT_LASTNAME = "lastname";

    // Attributes of the chatreceivertable
    private static final String RECEIVER_UID = "receiver_uid";
    private static final String RECEIVER_EMAIL = "receiver_email";
    private static final String RECEIVER_TOKEN = "receiver_token";


    public DatabaseSQL(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE IF NOT EXISTS userinfotable" + " (" + UID + " TEXT, " + FIRSTNAME + " TEXT, " + LASTNAME + " TEXT, " + EMAIL +  " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS contactstable" + " (" + CONTACT_UID + " TEXT, " + CONTACT_FIRSTNAME + " TEXT, " + CONTACT_LASTNAME + " TEXT)");
        db.execSQL("CREATE TABLE IF NOT EXISTS chatreceivertable" + " (" + RECEIVER_UID + " TEXT, " + RECEIVER_EMAIL + " TEXT, " + RECEIVER_TOKEN + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USERINFO);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + RECEIVER);
        onCreate(db);
    }

    private void emptyInfoTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ USERINFO);
        db.close();
    }


    private void emptyReceiverTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ RECEIVER);
        db.close();
    }

    public void saveUserInfo(String uid, String firstName, String lastName, String email){
        emptyInfoTable();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UID,uid);
        values.put(FIRSTNAME,firstName);
        values.put(LASTNAME,lastName);
        values.put(EMAIL,email);
        db.insert(USERINFO, null, values);
    }

    public void saveNewContact(String uid, String firstName, String lastName){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CONTACT_UID,uid);
        values.put(CONTACT_FIRSTNAME,firstName);
        values.put(CONTACT_LASTNAME,lastName);
        db.insert(CONTACTS, null, values);
    }

    public void saveReceiverInfo(String uid, String email, String token){
        emptyReceiverTable();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RECEIVER_UID,uid);
        values.put(RECEIVER_EMAIL,email);
        values.put(RECEIVER_TOKEN,token);
        db.insert(RECEIVER, null, values);
    }

    public ArrayList<String> getUserInfo() {
        ArrayList<String> myArray= new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            myArray.add(cur.getString(cur.getColumnIndex(UID)));
            myArray.add(cur.getString(cur.getColumnIndex(FIRSTNAME)));
            myArray.add(cur.getString(cur.getColumnIndex(LASTNAME)));
            myArray.add(cur.getString(cur.getColumnIndex(EMAIL)));
            cur.moveToNext();
        }
        cur.close();
        return myArray;
    }

    public ArrayList<ContactInformation> getContactsList() {
        ArrayList<ContactInformation> myArray= new ArrayList<ContactInformation>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ CONTACTS, null);
        cur.moveToFirst();
        while(cur.isAfterLast()==false){
            String uid = cur.getString(cur.getColumnIndex(CONTACT_UID));
            String firstName = cur.getString(cur.getColumnIndex(CONTACT_FIRSTNAME));
            String lastName = cur.getString(cur.getColumnIndex(CONTACT_LASTNAME));
            myArray.add(new ContactInformation(uid, firstName, lastName));
            cur.moveToNext();
        }
        cur.close();
        return myArray;
    }

    public String getEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        String email = cur.getString(cur.getColumnIndex(EMAIL));
        return email;
    }

    public String getUid(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ USERINFO, null);
        cur.moveToFirst();
        String uid = cur.getString(cur.getColumnIndex(UID));
        return uid;
    }

    // #############################################################
    // UTILITY FUNCTIONS FOR INSTANT MESSAGING

    public String getReceiverEmail(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ RECEIVER, null);
        cur.moveToFirst();
        String email = cur.getString(cur.getColumnIndex(RECEIVER_EMAIL));
        return email;
    }

    public String getReceiverUid(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ RECEIVER, null);
        cur.moveToFirst();
        String uid = cur.getString(cur.getColumnIndex(RECEIVER_UID));
        return uid;
    }

    public String getFirebaseToken(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cur = db.rawQuery("SELECT * FROM "+ RECEIVER, null);
        cur.moveToFirst();
        String token = cur.getString(cur.getColumnIndex(RECEIVER_TOKEN));
        return token;
    }
}
