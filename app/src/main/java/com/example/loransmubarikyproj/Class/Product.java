package com.example.loransmubarikyproj.Class;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Product implements SqlInterface {
    protected String pid;
    protected String prouductname;
    protected String prouductdisc;
    protected String prouductimg;
    protected int stock;
    protected double saleprice;
    protected double buyprice;

    public Product( String pid, String prouductname , String prouductdisc , String prouductimg,int stock , double saleprice , double buyprice ){

        this.pid=pid;
        this.prouductname=prouductname;
        this.prouductdisc=prouductdisc;
        this.prouductimg=prouductimg;
        this.stock=stock;
        this.saleprice=saleprice;
        this.buyprice=buyprice;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getProuductname() {
        return prouductname;
    }

    public void setProuductname(String prouductname) {
        this.prouductname = prouductname;
    }

    public String getProuductdisc() {
        return prouductdisc;
    }

    public void setProuductdisc(String prouductdisc) {
        this.prouductdisc = prouductdisc;
    }

    public String getProuductimg() {
        return prouductimg;
    }

    public void setProuductimg(String prouductimg) {
        this.prouductimg = prouductimg;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getSaleprice() {
        return saleprice;
    }

    public void setSaleprice(double saleprice) {
        this.saleprice = saleprice;
    }

    public double getBuyprice() {
        return buyprice;
    }

    public void setBuyprice(double buyprice) {
        this.buyprice = buyprice;
    }


    @Override
    public long Add(SQLiteDatabase db) {
        return 0;
    }

    @Override
    public int Delete(SQLiteDatabase db, int id) {
        return 0;
    }

    @Override
    public int Update(SQLiteDatabase db, int id) {
        return 0;
    }

    @Override
    public Cursor Select(SQLiteDatabase db) {
        return null;
    }
}
