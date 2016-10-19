package com.example.setditjenp2mkt.adapterdemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class StudentActivity extends AppCompatActivity {

    private ListView list_item;
    private TextView emptyTextView;
    private FloatingActionButton addStudent;
    private StudentList studentList;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_student_list, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_dummy:
                ArrayList<Student> students = populateUsersList();
                StudentAdapter StudentAdapter = new StudentAdapter(this,students);
                list_item = (ListView)findViewById(R.id.lv2);
                list_item.setAdapter(StudentAdapter);
                return true;
            case R.id.clear_list:
                clearlist();
                StudentAdapter StudentAdapters = new StudentAdapter(this,new ArrayList<Student>());
                list_item = (ListView)findViewById(R.id.lv2);
                list_item.setAdapter(StudentAdapters);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);
        studentList = StudentList.getInstance();
        StudentAdapter StudentAdapter = new StudentAdapter(this, StudentList.getList());
        list_item = (ListView)findViewById(R.id.lv2);
        list_item.setAdapter(StudentAdapter);
        emptyTextView = (TextView)findViewById(R.id.empty_tv);
        list_item.setEmptyView(emptyTextView);
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
    }

    private ArrayList<Student> populateUsersList() {
        StudentList students = StudentList.getInstance();
        students.addStudent(new Student(studentList.next()+"", "3145136210", "M. Fakhri Ali Ibrahim", "fakhri_siskom@yahoo.com"));
        students.addStudent(new Student(studentList.next()+"", "3145136197", "Muhammad Fachrizal", "bankai_yoy@gmail.com"));
        return StudentList.getList();
    }

    private ArrayList<Student> clearlist(){
        StudentList.getInstance().clearList();
        return StudentList.getList();
    }
}