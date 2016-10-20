package com.example.setditjenp2mkt.adapterdemo;

/**
 * Created by setditjen P2MKT on 07/10/2016.
 */

import java.io.Serializable;

public class Student implements Serializable{
    private int no;
    private String noreg;
    private String nama;
    private String email;
    private String telp;

    public Student(int no, String noreg, String nama, String email, String telp) {
        this.setNo(no);
        this.setNoreg(noreg);
        this.setNama(nama);
        this.setEmail(email);
        this.setTelp(telp);
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

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }
}
