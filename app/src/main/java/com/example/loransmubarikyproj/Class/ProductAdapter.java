package com.example.loransmubarikyproj.Class;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.DataBase.TablesString;
import com.example.loransmubarikyproj.R;

import com.example.loransmubarikyproj.UserPages.DetailedActivity;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;
    int pid;
    String uid;
    View view;
    Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int i) {

        view = LayoutInflater.from(context).inflate(R.layout.each_jewelry, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {

        // here we will find the position and start setting the output on our views


        String typeOfProduct = productList.get(position).getProdType();
        int yop = productList.get(position).getProdYOP();
        double price = productList.get(position).getSalesprice();
        byte[] images = productList.get(position).getProdimg();
        Bitmap bm = BitmapFactory.decodeByteArray(images, 0 ,images.length);
        pid = productList.get(position).getPid();
        uid = FirebaseAuth.getInstance().getUid();
        Favorite fav = new Favorite(uid,pid);
        DBHelper dbHelper = new DBHelper(context);
        dbHelper.OpenReadAble();
        Cursor c = fav.SelectById(dbHelper.getDb(),uid,pid);
        if(c.getCount() > 0){
            Bitmap bImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.redheart);
            holder.favBtn.setImageBitmap(bImage);
        }
        dbHelper.Close();
        holder.tvTypeOfProduct.setText(typeOfProduct);
        holder.tvJewelryPrice.setText(price+"");
        holder.tvJewelryYOP.setText(yop+"");
        holder.imageOfProduct.setImageBitmap(bm);


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // in order to make our views responsive we can implement onclicklistener on our recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // here we will find the views on which we will inflate our data

        TextView tvTypeOfProduct, tvJewelryPrice, tvJewelryYOP;
        ImageView imageOfProduct, addToCartBtn, favBtn;

        public ViewHolder(View itemView) {
            super(itemView);

            tvTypeOfProduct = itemView.findViewById(R.id.eachJewelryType);
            tvJewelryPrice = itemView.findViewById(R.id.eachJewelryPriceTv);
            tvJewelryYOP = itemView.findViewById(R.id.eachJewelryYOPTv);
            imageOfProduct = itemView.findViewById(R.id.eachJewelryIv);
            addToCartBtn = itemView.findViewById(R.id.eachJewelryAddToCartBtn);
            addToCartBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pid = productList.get(getLayoutPosition()).getPid();
                    uid = FirebaseAuth.getInstance().getUid();
                    Cart c = new Cart(uid,pid,1);
                    DBHelper dbHelper = new DBHelper(context);
                    dbHelper.OpenWriteAble();
                    if(c.Add(dbHelper.getDb())>-1)
                        Toast.makeText(context, "Added To Cart Successfully", Toast.LENGTH_SHORT).show();
                    dbHelper.Close();
                }
            });
            favBtn = itemView.findViewById(R.id.favbtn);
            favBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pid = productList.get(getLayoutPosition()).getPid();
                    uid = FirebaseAuth.getInstance().getUid();
                    Favorite fav = new Favorite(uid,pid);
                    DBHelper dbHelper = new DBHelper(context);
                    dbHelper.OpenReadAble();
                    Cursor c = fav.SelectById(dbHelper.getDb(),uid,pid);
                    if(c.getCount() > 0){
                        c.moveToFirst();
                        Log.d("Count",""+c.getCount());
                        fav.Delete(dbHelper.getDb(),c.getInt(c.getColumnIndexOrThrow(TablesString.FavoriteTable._ID)));
                        Bitmap bImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.hearticon);
                        favBtn.setImageBitmap(bImage);
                    }
                    else {
                        dbHelper.Close();
                        dbHelper.OpenWriteAble();
                        if (fav.Add(dbHelper.getDb()) > -1) {
                            Bitmap bImage = BitmapFactory.decodeResource(view.getResources(), R.drawable.redheart);
                            favBtn.setImageBitmap(bImage);
                        }
                    }
                    dbHelper.Close();
                }
            });

            itemView.setOnClickListener(this);

        }
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailedActivity.class);
            intent.putExtra("id", productList.get(getLayoutPosition()).getPid() + "");
            v.getContext().startActivity(intent);
        }

    }
}

