package com.example.setditjenp2mkt.adapterdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.setditjenp2mkt.adapterdemo.StudentContract.StudentEntry;

/**
 * Created by setditjen P2MKT on 11/11/2016.
 */

public class StudentDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = StudentDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "student_list.db";
    private static final int DATABASE_VERSION = 1;

    public StudentDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_STUDENT_TABLE =  "CREATE TABLE " + StudentEntry.TABLE_NAME + " ("
                + StudentEntry._ID + " INTEGER PRIMARY KEY, "
                + StudentEntry.STUDENT_NOREG + " TEXT NOT NULL, "
                + StudentEntry.STUDENT_NAMA + " TEXT NOT NULL, "
                + StudentEntry.STUDENT_EMAIL + " TEXT, "
                + StudentEntry.STUDENT_TELP + " TEXT )";

        db.execSQL(SQL_CREATE_STUDENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertStudent(SQLiteDatabase db, Student student){
        ContentValues values = new ContentValues();
        values.put(StudentEntry.STUDENT_NOREG, student.getNoreg());
        values.put(StudentEntry.STUDENT_NAMA, student.getNama());
        values.put(StudentEntry.STUDENT_EMAIL, student.getEmail());
        values.put(StudentEntry.STUDENT_TELP, student.getTelp());
        db.insert(StudentEntry.TABLE_NAME, null, values);
    }

    public void updateStudent(SQLiteDatabase db, Student student){

// New value for one column
        ContentValues values = new ContentValues();
        values.put(StudentEntry.STUDENT_NOREG, student.getNoreg());
        values.put(StudentEntry.STUDENT_NAMA, student.getNama());
        values.put(StudentEntry.STUDENT_EMAIL, student.getEmail());
        values.put(StudentEntry.STUDENT_TELP, student.getTelp());

// Which row to update, based on the ID
        String condition = StudentEntry._ID + " = ? , ";
        String[] conditionArgs = {student.getNo() + ""};
        db.update(
                StudentEntry.TABLE_NAME,
                values,
                condition,
                conditionArgs);

    }

    public Cursor getInfo(SQLiteDatabase db){
        Cursor cursor;
        String[] projection = {
                StudentEntry._ID,
                StudentEntry.STUDENT_NOREG,
                StudentEntry.STUDENT_NAMA,
                StudentEntry.STUDENT_EMAIL,
                StudentEntry.STUDENT_TELP
        };
        cursor = db.query(StudentEntry.TABLE_NAME, projection, null, null, null, null, null);
        return cursor;
    }

    public void truncate(SQLiteDatabase db){
        String sql = "DELETE FROM " + StudentEntry.TABLE_NAME + ";VACUUM;";
        db.execSQL(sql);
    }

}
