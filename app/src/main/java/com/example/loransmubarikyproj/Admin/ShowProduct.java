package com.example.loransmubarikyproj.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.loransmubarikyproj.Class.ListAdapter;
import com.example.loransmubarikyproj.Class.Product;
import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.R;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_BUYPRICE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_COLOR;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_DESCRIPTION;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_IMAGE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_NAME;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SALEPRICE;

public class ShowProduct extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener {
    ListView productListview;
    Button addnew;
    String [] product_string ;
    DBHelper db;
    Product p;
    Product[] product_info;
    Product selected_product;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_product);
        productListview = findViewById(R.id.lvproduct);
        productListview.setOnItemClickListener(this);
        addnew = findViewById(R.id.btAddNewProd);
        addnew.setOnClickListener(this);
        db = new DBHelper(getApplicationContext());
        p = new Product();
        getProductToArray();
        ListAdapter adapter = new ListAdapter(this,product_info);
        productListview.setAdapter(adapter);

    }
    public void getProductToArray(){
        db.OpenReadAble();
        Cursor c = p.Select(db.getDb());
        if(c.getCount()>0){
            product_string = new String[c.getCount()];
            product_info =  new Product[c.getCount()];
            int i =0;
            c.moveToFirst();
            while(!c.isAfterLast()){
                p.setPid(c.getInt(c.getColumnIndexOrThrow(BaseColumns._ID)));
                p.setProdname(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
                p.setProddisc(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)));
                p.setBuyprice(c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE)));
                p.setSaleprice(c.getDouble(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)));
                p.setColor(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_COLOR)));
                p.setImageByte(c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE)));

               /* if(cat.equals("PC"))
                    product_info[i]=new PC(p);
                else if(cat.equals("LabTop"))
                    product_info[i]=new LabTop(p);
                else if(cat.equals("Printer"))
                    product_info[i]=new Printer(p);
                else
                    product_info[i]=new Other(p);*/
                product_info[i]=new Product(p);
                product_string[i++] = p.toString();
                c.moveToNext();
                p = new Product();
            }
        }
        db.Close();
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selected_product = product_info[i];
        Intent in = new Intent(this, AddProduct.class);
        in.putExtra("Selected_Id", selected_product.getPid()+"");
        startActivity(in);
    }

    @Override
    public void onClick(View view) {
        Intent in = new Intent(this, AddProduct.class);
        startActivity(in);
    }
    @Override
    public void onStop() {
        FirebaseAuth fauth = FirebaseAuth.getInstance();
        fauth.signOut();
        super.onStop();
    }
}