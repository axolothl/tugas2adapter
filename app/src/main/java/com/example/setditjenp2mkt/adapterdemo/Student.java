package com.example.setditjenp2mkt.adapterdemo;

/**
 * Created by setditjen P2MKT on 07/10/2016.
 */

import java.io.Serializable;

public class Student implements Serializable{
    private String no;
    private String noreg;
    private String nama;
    private String email;

    public Student(String no, String noreg, String nama, String email) {
        this.setNo(no);
        this.setNoreg(noreg);
        this.setNama(nama);
        this.setEmail(email);
    }

    public String getNoreg() {
        return noreg;
    }

    public void setNoreg(String noreg) {
        this.noreg = noreg;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
