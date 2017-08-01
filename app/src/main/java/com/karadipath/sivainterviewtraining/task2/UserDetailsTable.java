package com.karadipath.sivainterviewtraining.task2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.karadipath.sivainterviewtraining.DataBase.DateBaseHandler;
import com.karadipath.sivainterviewtraining.home.UtilFunctions;

import org.json.JSONException;

import java.util.HashMap;




public class UserDetailsTable extends DateBaseHandler {
    public static String CreateTableStatement = "CREATE TABLE USERDETAILS(USERID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,UserName Text,UserEmail TEXT,PIN TEXT,mobile TEXT,DOB TEXT,Gender TEXT)";

    public static String DropTableStatement = "DROP TABLE IF EXISTS USERDETAILS";
    SQLiteDatabase db = null;
    Cursor cursor;
    Context mContext;
    private HashMap<String, String> userDetail;

    public UserDetailsTable(Context context) {
        super(context);
        mContext = context;
    }

    public String InsertUserDetails(HashMap<String, String> mRegistrationDetails) throws JSONException {
        ContentValues values;
        try {

            String selectQuery = "SELECT *  FROM USERDETAILS Where UserEmail ='" + mRegistrationDetails.get("UserEmail") + "' COLLATE NOCASE";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                deleteUserDetail(mRegistrationDetails
                        .get("UserEmail"));
            }
            values = new ContentValues();
            values.put("UserEmail", mRegistrationDetails
                    .get("UserEmail"));
            values.put("UserName", mRegistrationDetails
                    .get("UserName"));

            values.put("PIN", mRegistrationDetails
                    .get("PIN"));
            values.put("mobile", mRegistrationDetails
                    .get("mobile"));


            db = this.getWritableDatabase();
            db.insertOrThrow("USERDETAILS", null, values);

            cursor.close();
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }
            return UtilFunctions.SUCCESS;

        } catch (Exception e) {
            return UtilFunctions.FALSE;
        } finally {
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }
        }


    }

    public boolean deleteAll() {
        db = this.getWritableDatabase();
        boolean isDeleteSuccess = db.delete("USERDETAILS", null, null) > 0;
        db.close();
        return isDeleteSuccess;
    }


    private void deleteUserDetail(String userEmail) {
        db = this.getWritableDatabase();
         db.delete("USERDETAILS", "UserEmail = '" + userEmail + "' COLLATE NOCASE", null);
    }

    public HashMap<String, String> getUserDetail() {
        HashMap<String, String> UserDetail = null;
        String selectQuery = "";

        try {
            selectQuery = "SELECT DISTINCT USERID,UserName,UserEmail,PIN,mobile,DOB,Gender  FROM USERDETAILS";

            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                UserDetail = new HashMap<String, String>();
                UserDetail.put("USERID", cursor.getString(0));
                UserDetail.put("UserName", cursor.getString(1));
                UserDetail.put("UserEmail", cursor.getString(2));
                UserDetail.put("PIN", cursor.getString(3));
                UserDetail.put("mobile", cursor.getString(4));
                UserDetail.put("DOB", cursor.getString(5));
                UserDetail.put("Gender", cursor.getString(6));

            }
            cursor.close();
            db.close();


        } catch (Exception e) {
            throw e;
        } finally {
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }

        }
        return UserDetail;
    }

    public String InsertUserDetails_pro(HashMap<String, String> mProfileDetails)throws JSONException {
        ContentValues values;
        try {

            String selectQuery = "SELECT *  FROM USERDETAILS Where UserEmail ='" + mProfileDetails.get("UserEmail") + "' COLLATE NOCASE";
            db = this.getReadableDatabase();
            cursor = db.rawQuery(selectQuery, null);
            if (cursor.moveToFirst()) {
                deleteUserDetail(mProfileDetails
                        .get("UserEmail"));
            }
            values = new ContentValues();
            values.put("UserEmail", mProfileDetails
                    .get("UserEmail"));
            values.put("UserName", mProfileDetails
                    .get("UserName"));

            values.put("PIN", mProfileDetails
                    .get("PIN"));
            values.put("mobile", mProfileDetails
                    .get("mobile"));
            values.put("DOB", mProfileDetails
                    .get("DOB"));
            values.put("Gender", mProfileDetails
                    .get("Gender"));


            db = this.getWritableDatabase();
            db.insertOrThrow("USERDETAILS", null, values);

            cursor.close();
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }
            return UtilFunctions.SUCCESS;

        } catch (Exception e) {
            return UtilFunctions.FALSE;
        } finally {
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }
        }


    }

    public String UpdateUserDetails_pro(HashMap<String, String> mProfileDetails, String userID) {
        ContentValues values;
        boolean isInsertSuccess = false;
        try {
            values = new ContentValues();

            values.put("USERID", userID);
            values.put("UserEmail", mProfileDetails
                    .get("UserEmail"));
            values.put("UserName", mProfileDetails
                    .get("UserName"));

            values.put("PIN", mProfileDetails
                    .get("PIN"));
            values.put("mobile", mProfileDetails
                    .get("mobile"));
            values.put("DOB", mProfileDetails
                    .get("DOB"));
            values.put("Gender", mProfileDetails
                    .get("Gender"));



            db = this.getWritableDatabase();
            db.insertOrThrow("USERDETAILS", null, values);

          /*  int eff = db.update("USERDETAILS", values, "USERID='" + mProfileDetails.get("USERID") + "' COLLATE NOCASE", null);
            int gh = eff;*/

            db.close();

            return UtilFunctions.SUCCESS;


        } catch (Exception e) {
            throw e;
            // return UtilFunctions.FALSE;
        } finally {
            if (db != null) {
                if (db.isOpen()) {
                    db.close();
                }
                db = null;
            }
        }
    }
}
