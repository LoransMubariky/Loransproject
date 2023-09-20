package com.example.loransmubarikyproj;

public class Product implements SqlQuery {
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
    public boolean Add() {
        return false;
    }

    @Override
    public boolean Delete() {
        return false;
    }

    @Override
    public boolean Update() {
        return false;
    }
}
