package com.example.nawalproj.UserPages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.loransmubarikyproj.Class.Favorite;
import com.example.loransmubarikyproj.Class.Product;
import com.example.loransmubarikyproj.Class.ProductAdapter;
import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.User.LoginActivity;
import com.example.loransmubarikyproj.User.SignupActivity;
import com.example.loransmubarikyproj.R;
import android.database.Cursor;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import com.example.loransmubarikyproj.Class.Product;

import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_BUYPRICE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_CATEGORY;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_DESCRIPTION;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_IMAGE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_KARAT;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SALEPRICE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_STOCK;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_TYPE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_YOP;

public class ProductView extends AppCompatActivity {

    List<Product> productList;
    RecyclerView recyclerView;
    ProductAdapter mAdapter;
    DBHelper dbHelper;
    String selctedCategory;
    CardView cardView;
    ImageView favbtn, cartiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);
        Bundle extras = getIntent().getExtras();
        selctedCategory = extras.getString("Category");
        recyclerView = findViewById(R.id.mainRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        productList = new ArrayList<>();
        dbHelper = new DBHelper(this);
        dbHelper = dbHelper.OpenReadAble();
        Product p = new Product(), p2;
        Cursor c;
        if (selctedCategory == "LastPieces") {
            c = p.SelectLastPieces(dbHelper.getDb());
        } else
            c = p.SelectByCategory(dbHelper.getDb(), selctedCategory);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            p2 = new Product(c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_TYPE)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_YOP)),
                    c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_STOCK)),
                    c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)),
                    c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_KARAT)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_CATEGORY)));
            productList.add(p2);
            c.moveToNext();
        }
        dbHelper.Close();
        // adapter
        mAdapter = new ProductAdapter(this, productList);
        recyclerView.setAdapter(mAdapter);
    }

}