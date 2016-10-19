package com.example.setditjenp2mkt.adapterdemo;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddStudentActivity extends AppCompatActivity {
    private EditText editnoreg;
    private EditText editnama;
    private EditText editemail;
    private EditText editno;
    private Student student;
    private FloatingActionButton ok;
    private FloatingActionButton cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        editno = (EditText)findViewById(R.id.editno);
        editnoreg = (EditText)findViewById(R.id.editnoreg);
        editnama = (EditText)findViewById(R.id.editNama);
        editemail = (EditText)findViewById(R.id.editemail);
        ok = (FloatingActionButton)findViewById(R.id.ok);
        cancel = (FloatingActionButton)findViewById(R.id.cancel);

        Intent intent = getIntent();
        final boolean action = intent.getBooleanExtra("edit", false);
        final int position = intent.getIntExtra("position", 0);

        if (action == true){
            setTitle("Edit Data");
            student = (Student) intent.getSerializableExtra("student_position");
            editno.setText(student.getNo());
            editnoreg.setText(student.getNoreg());
            editnama.setText(student.getNama());
            editemail.setText(student.getEmail());
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStudentActivity.this, StudentActivity.class);
                String no = editno.getText().toString();
                String noreg = editnoreg.getText().toString();
                String nama = editnama.getText().toString();
                String email = editemail.getText().toString();
                student = new Student(no,noreg,nama,email);
                StudentList studentList = StudentList.getInstance();
                if(!action){
                    studentList.addStudent(student);
                    Toast.makeText(getApplicationContext(), "Student successfully added", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else{
                    studentList.edit(position, student);
                    Toast.makeText(getApplicationContext(), "Student successfully edited", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddStudentActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        Intent intent = getIntent();
        boolean action = intent.getBooleanExtra("edit", false);
        if (action){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_edit, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.delete:
                Intent intent = new Intent(AddStudentActivity.this, StudentActivity.class);
                int no = Integer.parseInt(student.getNo());
                StudentList list = StudentList.getInstance();
                list.delete(no-1);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
