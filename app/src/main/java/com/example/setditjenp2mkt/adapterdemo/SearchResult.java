package com.example.setditjenp2mkt.adapterdemo;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    ArrayList<Student> selected_items = new ArrayList<>();
    int jml = 0;

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
                int student = Integer.parseInt(searchList.get(position).getNo());
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
        list_item.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        list_item.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                jml++;
                mode.setTitle(jml + " items selected");
                selected_items.add(searchList.get(position));
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
                            int index = Integer.parseInt(msg.getNo());
                            AddStudentActivity.daftarstudent.remove(index-1);
                            studentList.changeNo(index-1);
                        }
                        Toast.makeText(getApplicationContext(), jml + " items deleted", Toast.LENGTH_SHORT).show();
                        jml = 0;
                        mode.finish();
                        Intent intent = new Intent(SearchResult.this, StudentActivity.class);
                        startActivity(intent);
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
    private ArrayList<Student> populateSearchList() {
        SearchResultList.getInstance().clearList();
        return SearchResultList.getList();
    }
}
