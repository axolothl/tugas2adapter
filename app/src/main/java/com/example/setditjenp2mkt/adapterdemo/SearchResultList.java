package com.example.setditjenp2mkt.adapterdemo;

import java.util.ArrayList;

/**
 * Created by setditjen P2MKT on 20/10/2016.
 */

public class SearchResultList {
    private static ArrayList<Student> searchResultList = new ArrayList<>();
    private static SearchResultList instance = new SearchResultList();
    public static SearchResultList getInstance(){
        return instance;
    }
    public static ArrayList<Student> getList(){
        return searchResultList;
    }
    public Student get(int index){
        Student student = searchResultList.get(index);
        return student;
    }
    public void clearList(){
        searchResultList.clear();
    }

}
