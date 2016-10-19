package com.example.setditjenp2mkt.adapterdemo;

import java.util.ArrayList;

/**
 * Created by setditjen P2MKT on 15/10/2016.
 */

public class StudentList {
    private static ArrayList<Student> daftarstudent = new ArrayList<>();
    private static StudentList instance = new StudentList();

    public static StudentList getInstance(){
        return instance;
    }
    public static ArrayList<Student> getList(){
        return daftarstudent;
    }
    public void addStudent(Student student){
        student.setNo(next()+"");
        daftarstudent.add(student);
    }
    public void clearList(){
        daftarstudent.clear();
    }
    public Student get(int index){
        Student student = daftarstudent.get(index);
        return student;
    }
    public void edit(int index, Student student){
        daftarstudent.set(index, student);
    }
    public int next(){
        return daftarstudent.size()+1;
    }
}

