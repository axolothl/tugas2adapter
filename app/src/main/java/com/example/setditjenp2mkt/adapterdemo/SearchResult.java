package com.example.setditjenp2mkt.adapterdemo;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/**
 * Created by setditjen P2MKT on 20/10/2016.
 */

public class SearchResult extends AppCompatActivity{
    private AddStudentActivity studentList;
    private Student student;
    private SearchResultList searchList;
    private int i;
    private TextView emptyTextView;
    private ListView list_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        emptyTextView = (TextView)findViewById(R.id.empty_tv);
        list_item = (ListView)findViewById(R.id.lv2_search);
        list_item.setEmptyView(emptyTextView);
        searchList = SearchResultList.getInstance();
        String keyword = new String();
        final Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())){
            keyword = searchIntent.getStringExtra(SearchManager.QUERY);
            setTitle("result for: " + keyword);
            Toast.makeText(SearchResult.this, "result for: " + keyword, Toast.LENGTH_SHORT).show();
        }
        list_item.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchResult.this, AddStudentActivity.class);
                int student = searchList.get(position).getNo();
                Student students = studentList.get(student-1);
                intent.putExtra("edit", true);
                intent.putExtra("student_position",students);
                intent.putExtra("position",student-1);
                startActivity(intent);
            }
        });

        ArrayList<Student> searchResult = populateSearchList();
        studentList = AddStudentActivity.getInstance();
        for (i = 0; i<studentList.size(); i++){
            if (studentList.get(i).getNoreg().contains(keyword.toLowerCase())){
                student = studentList.get(i);
                searchResult.add(student);
            }
        }

        StudentAdapter studentAdapter = new StudentAdapter(this, searchResult);
        ListView list_item = (ListView)findViewById(R.id.lv2_search);
        list_item.setAdapter(studentAdapter);
    }
    private ArrayList<Student> populateSearchList() {
        SearchResultList.getInstance().clearList();
        //SearchResultList students = SearchResultList.getInstance();
        return SearchResultList.getList();
    }
}
