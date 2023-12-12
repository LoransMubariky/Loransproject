package com.example.loransmubarikyproj.User;

import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.*;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.loransmubarikyproj.Class.Product;
import com.example.loransmubarikyproj.Class.ProductAdapter;
import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.R;


import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    List<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    DBHelper dbHelper;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        productList = new ArrayList<>();
        dbHelper = new DBHelper(inflater.getContext());
        dbHelper = dbHelper.OpenReadAble();
        Product p = new Product();
        Cursor c = p.Select(dbHelper.getDb());
        c.moveToFirst();
        while(!c.isAfterLast()){

            p.setPid(c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID)));
            p.setProdname(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
            p.setProddisc(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)));
            p.setBuyprice(c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE)));
            p.setSaleprice(c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)));
            p.setColor(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_COLOR)));
            p.setImageByte(c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)));
            productList.add(p);
            c.moveToNext();
            p=new Product();
        }
        // adapter
        mAdapter = new ProductAdapter(inflater.getContext(), productList);
        recyclerView.setAdapter(mAdapter);
        // Inflate the layout for this fragment
        return  view;
    }
}