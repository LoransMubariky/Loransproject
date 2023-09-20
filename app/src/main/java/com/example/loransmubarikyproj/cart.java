package com.example.loransmubarikyproj;

import java.util.ArrayList;
import java.util.List;

public class cart {

    String cartid;

    String uid;

    List<Product> products;

    public cart(String cartid, String uid){
        this.cartid = cartid;
        this.uid = uid;
        products = new ArrayList<Product>();
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
