package com.example.setditjenp2mkt.adapterdemo;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private ListView list_item;
    private TextView emptyTextView;
    private FloatingActionButton addStudent;
    private AddStudentActivity studentList;
    private StudentAdapter studentAdapter;
    public ArrayList<Student> daftarstudent = new ArrayList<>();
    public AddStudentActivity instance = new AddStudentActivity();
    ArrayList<Student> selected_items = new ArrayList<>();
    int jml = 0;
    SQLiteDatabase sqLiteDatabase;
    StudentDbHelper studentDbHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        //studentList = AddStudentActivity.getInstance();
        studentList = instance;
        //studentAdapter = new StudentAdapter(StudentActivity.this, AddStudentActivity.getList());
        studentAdapter = new StudentAdapter(StudentActivity.this, daftarstudent);
        list_item = (ListView)findViewById(R.id.lv2);
        list_item.setAdapter(studentAdapter);
        emptyTextView = (TextView)findViewById(R.id.empty_tv);
        list_item.setEmptyView(emptyTextView);
        studentDbHelper = new StudentDbHelper(getApplicationContext());
        sqLiteDatabase = studentDbHelper.getReadableDatabase();
        cursor = studentDbHelper.getInfo(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do {
                String ID, noreg, nama, email, telp;
                ID = cursor.getString(0);
                noreg = cursor.getString(1);
                nama = cursor.getString(2);
                email = cursor.getString(3);
                telp = cursor.getString(4);
                Student student = new Student(ID,noreg,nama,email,telp);
                studentAdapter.add(student);
            }while (cursor.moveToNext());
        }
        addStudent = (FloatingActionButton)findViewById(R.id.floatingActionButton3);
        addStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);
                Student student = studentList.get(position);
                intent.putExtra("edit", true);
                intent.putExtra("student_position",student);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        list_item.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list_item.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                tanda();
                jml++;
                mode.setTitle(jml + " items selected");
                selected_items.add(studentList.get(position));

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater inflater = mode.getMenuInflater();
                inflater.inflate(R.menu.my_context, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.delete_id:
                        for (Student msg : selected_items)
                        {
                            daftarstudent.remove(msg);
                            int index = Integer.parseInt(msg.getNo());
                            studentList.changeNo(index-1);
                        }
                        Toast.makeText(getApplicationContext(), jml + " items deleted", Toast.LENGTH_SHORT).show();
                        jml = 0;
                        mode.finish();
                        return true;
                    default:
                        return false;
                }
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student_list, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView)search.getActionView();
        SearchManager searchManager = (SearchManager)getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_dummy:
                ArrayList<Student> students = populateUsersList();
                StudentAdapter studentAdapter = new StudentAdapter(this,students);
                //new DataSyncTask().execute(students);

                list_item = (ListView)findViewById(R.id.lv2);
                list_item.setAdapter(studentAdapter);
                return true;
            case R.id.clear_list:
                clearlist();
                StudentAdapter studentAdapters = new StudentAdapter(this,new ArrayList<Student>());
                list_item = (ListView)findViewById(R.id.lv2);
                list_item.setAdapter(studentAdapters);
                SQLiteDatabase db = studentDbHelper.getWritableDatabase();
                studentDbHelper.truncate(db);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<Student> populateUsersList() {
        //AddStudentActivity students = AddStudentActivity.getInstance();
        AddStudentActivity students = instance;
        SQLiteDatabase dbs = studentDbHelper.getWritableDatabase();
        ContentValues value = new ContentValues();
        students.addStudent(new Student(studentList.next()+"", "3145136210", "M. Fakhri Ali Ibrahim", "fakhri_siskom@yahoo.com", "085881562153"));
        //value.put(StudentContract.StudentEntry._ID, studentList.next());
        value.put(StudentContract.StudentEntry.STUDENT_NOREG, "3145136210");
        value.put(StudentContract.StudentEntry.STUDENT_NAMA, "M Fakhri Ali Ibrahim");
        value.put(StudentContract.StudentEntry.STUDENT_EMAIL, "fakhri_siskom@yahoo.com");
        value.put(StudentContract.StudentEntry.STUDENT_TELP, "085881562153");
        dbs.insert(StudentContract.StudentEntry.TABLE_NAME, null, value);
        /*students.addStudent(new Student(studentList.next()+"", "3145136197", "Muhammad Reyhan", "bankai_yoy@gmail.com", "0857xxxxxxxx"));
        value.put(StudentContract.StudentEntry.STUDENT_NOREG, "3145136197");
        value.put(StudentContract.StudentEntry.STUDENT_NAMA, "Muhammad Reyhan");
        value.put(StudentContract.StudentEntry.STUDENT_EMAIL, "bankai_yoy@gmail.com");
        value.put(StudentContract.StudentEntry.STUDENT_TELP, "0857xxxxxxxx");
        dbs.insert(StudentContract.StudentEntry.TABLE_NAME, null, value);
        students.addStudent(new Student(studentList.next()+"", "3145136210", "Muhammad Fachrizal", "bankai_yoy@gmail.com", "0857xxxxxxxx"));
        value.put(StudentContract.StudentEntry.STUDENT_NOREG, "3145136210");
        value.put(StudentContract.StudentEntry.STUDENT_NAMA, "Muhammad Fachrizal");
        value.put(StudentContract.StudentEntry.STUDENT_EMAIL, "bankai_yoy@gmail.com");
        value.put(StudentContract.StudentEntry.STUDENT_TELP, "0857xxxxxxxx");
        dbs.insert(StudentContract.StudentEntry.TABLE_NAME, null, value);
        students.addStudent(new Student(studentList.next()+"", "3145136197", "Gregorius Andito", "bankai_yoy@gmail.com", "0857xxxxxxxx"));
        value.put(StudentContract.StudentEntry.STUDENT_NOREG, "3145136197");
        value.put(StudentContract.StudentEntry.STUDENT_NAMA, "Gregorius Andito");
        value.put(StudentContract.StudentEntry.STUDENT_EMAIL, "bankai_yoy@gmail.com");
        value.put(StudentContract.StudentEntry.STUDENT_TELP, "0857xxxxxxxx");
        dbs.insert(StudentContract.StudentEntry.TABLE_NAME, null, value);*/
        //return AddStudentActivity.getList();
        return daftarstudent;
    }

    private ArrayList<Student> clearlist(){
        //AddStudentActivity.getInstance().clearList();
        instance.clearList();
        //return AddStudentActivity.getList();
        return daftarstudent;
    }

    public void tanda(){
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
            }
        });
    }

    private class DataSyncTask extends AsyncTask<AddStudentActivity,Void,StudentAdapter> {

        @Override
        protected StudentAdapter doInBackground(AddStudentActivity... params) {
            StudentAdapter studentAdapter = new StudentAdapter(StudentActivity.this, params[0]);
            return studentAdapter;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(StudentAdapter studentAdapter) {
            list_item.setAdapter(studentAdapter);
        }
    }
}