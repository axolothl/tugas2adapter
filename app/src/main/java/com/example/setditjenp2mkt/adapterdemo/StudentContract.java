package com.example.setditjenp2mkt.adapterdemo;

import android.provider.BaseColumns;

/**
 * Created by setditjen P2MKT on 11/11/2016.
 */

public final class StudentContract {
    private StudentContract() {}

    public static final class StudentEntry implements BaseColumns {
        public final static String TABLE_NAME = "students";
        public final static String _ID = BaseColumns._ID;
        public final static String STUDENT_NOREG ="noreg";
        public final static String STUDENT_NAMA ="nama";
        public final static String STUDENT_EMAIL ="email";
        public final static String STUDENT_TELP ="telp";
    }
}
