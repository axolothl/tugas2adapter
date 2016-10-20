package com.example.setditjenp2mkt.adapterdemo;

/**
 * Created by setditjen P2MKT on 07/10/2016.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


public class StudentAdapter extends ArrayAdapter<Student> {
    public StudentAdapter(Context context, ArrayList<Student> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Student user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_activity, parent, false);
        }
        // Lookup view for data population
        TextView no = (TextView) convertView.findViewById(R.id.nomor);
        TextView noreg = (TextView) convertView.findViewById(R.id.tvName);
        TextView nama = (TextView) convertView.findViewById(R.id.textView2);
        TextView email = (TextView) convertView.findViewById(R.id.tvHometown);
        TextView telp = (TextView) convertView.findViewById(R.id.hp);
        // Populate the data into the template view using the data object
        no.setText(user.getNo() + "");
        noreg.setText(user.getNoreg());
        nama.setText(user.getNama());
        email.setText(user.getEmail());
        telp.setText(user.getTelp());
        // Return the completed view to render on screen
        return convertView;
    }
}
