package com.example.loransmubarikyproj.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.loransmubarikyproj.Class.Product;
import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_BUYPRICE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_COLOR;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_DESCRIPTION;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_IMAGE;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_NAME;
import static com.example.loransmubarikyproj.DataBase.TablesString.ProductTable.COLUMN_PRODUCT_SALEPRICE;

public class AddProduct extends AppCompatActivity implements View.OnClickListener{
    private static int RESULT_LOAD_IMAGE = 1;
    EditText etname,etdisc,etcolor,etsaleprice,etbuyprice;
    ImageButton imageButton;
    Button btadd,btdelete,btupdate;
    boolean SelectedNewImage ;
    Product p;
    Uri selectedImageUri;
    String selectedId;
    DBHelper dbHelper;
    byte [] image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        etname = findViewById(R.id.etProdName);
        etdisc = findViewById(R.id.etDesc);
        etcolor = findViewById(R.id.etColor);
        etsaleprice = findViewById(R.id.etSalePrice);
        etbuyprice = findViewById(R.id.etBuyPrice);
        imageButton = findViewById(R.id.imageButton);
        btadd = findViewById(R.id.addButton);
        btadd.setOnClickListener(this);
        btdelete = findViewById(R.id.btDelete);
        btdelete.setOnClickListener(this);
        btupdate = findViewById(R.id.btUpdate);
        btupdate.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        dbHelper = new DBHelper(this);
        SelectedNewImage = false;
        Intent i = getIntent();
        if (i.getStringExtra("Selected_Id") == null) {
            btdelete.setVisibility(View.GONE);
            btupdate.setVisibility(View.GONE);
        } else {
            btadd.setVisibility(View.GONE);
            selectedId = i.getStringExtra("Selected_Id");
            setProduct();
        }
    }
        private void setProduct() {

            dbHelper.OpenReadAble();
            p=new Product();
            Cursor c = p.SelectById(dbHelper.getDb(),selectedId);
            if(c!=null){
                c.moveToFirst();
                etname.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_NAME)));
                etdisc.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_DESCRIPTION)));
                etbuyprice.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_BUYPRICE)));
                etsaleprice.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_SALEPRICE)));
                etcolor.setText(c.getString(c.getColumnIndexOrThrow(COLUMN_PRODUCT_COLOR)));
                image = c.getBlob(c.getColumnIndexOrThrow(COLUMN_PRODUCT_IMAGE));
                Bitmap bm = BitmapFactory.decodeByteArray(image, 0 ,image.length);
                imageButton.setImageBitmap(bm);
            }
            dbHelper.Close();

        }

/*
casting a bitmap bite
 */
        @Override
        public void onClick(View view) {
            if(view.getId()==R.id.addButton){
                dbHelper.OpenWriteAble();
                dbHelper = new DBHelper(this);

                byte[] data  = imageViewToByte();
                p=new Product(etname.getText().toString(),
                        etdisc.getText().toString(),
                        etcolor.getText().toString(),
                        Double.parseDouble(etsaleprice.getText().toString()),
                        Double.parseDouble(etbuyprice.getText().toString()),data);
                dbHelper.OpenWriteAble();
                if(p.Add(dbHelper.getDb())>-1){
                    Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show();
                    dbHelper.Close();
                   Intent i = new Intent(this,ShowProduct.class);
                    startActivity(i);
                }
            }
            if(view.getId()==R.id.btUpdate){
                p.setPid(Integer.parseInt(selectedId));
                p.setProdname(etname.getText().toString());
                p.setProddisc(etdisc.getText().toString());
                p.setBuyprice(Double.parseDouble(etbuyprice.getText().toString()));
                p.setSaleprice(Double.parseDouble(etsaleprice.getText().toString()));
                p.setColor(etcolor.getText().toString());
                if(SelectedNewImage)
                    p.setImageByte(imageViewToByte());
                else
                    p.setImageByte(image);
                dbHelper.OpenWriteAble();
                p.Update(dbHelper.getDb(),Integer.parseInt(selectedId));
                dbHelper.Close();
                Toast.makeText(this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,ShowProduct.class);
                startActivity(i);
            }
            if(view.getId()==R.id.btDelete){
                dbHelper.OpenWriteAble();
                p.Delete(dbHelper.getDb(),Integer.parseInt(selectedId));
                dbHelper.Close();
                Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this,ShowProduct.class);
                startActivity(i);
            }
            if(view.getId()==R.id.imageButton){
                Intent gallery = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, RESULT_LOAD_IMAGE);
            }
        }
        public byte[] imageViewToByte() {
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver() ,selectedImageUri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            return baos.toByteArray();
        }
//change
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {

            super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK && requestCode == 1){
                selectedImageUri = data.getData();
                imageButton.setImageURI(selectedImageUri);
                SelectedNewImage = true;
            }
        }
    }