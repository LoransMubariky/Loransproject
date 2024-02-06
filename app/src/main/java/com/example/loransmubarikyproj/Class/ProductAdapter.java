package com.example.loransmubarikyproj.Class;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.R;
import com.example.loransmubarikyproj.User.ProductInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {

    List<Product> productList;
    Context context;
    DBHelper dbHelper;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        dbHelper = new DBHelper(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.each_watch_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        // here we will find the position and start setting the output on our views

        String nameofProduct = productList.get(position).getProdname();
        double salepriceofproduct = productList.get(position).getSaleprice();
        byte[] images = productList.get(position).getImageByte();
        Bitmap bm = BitmapFactory.decodeByteArray(images, 0, images.length);

        holder.tvNameOfProduct.setText(nameofProduct);
        holder.tvPriceOfProduct.setText(salepriceofproduct + "");
        holder.imageOfProduct.setImageBitmap(bm);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    // in order to make our views responsive we can implement onclicklistener on our recyclerview
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        // here we will find the views on which we will inflate our data

        TextView tvNameOfProduct, tvPriceOfProduct;
        ImageView imageOfProduct, addtocart;

        public ViewHolder(View itemView) {
            super(itemView);

            tvNameOfProduct = itemView.findViewById(R.id.eachWatchName);
            tvPriceOfProduct = itemView.findViewById(R.id.eachWatchPriceTv);
            imageOfProduct = itemView.findViewById(R.id.eachWatchPic);
            addtocart = itemView.findViewById(R.id.eachWatchAddToCartBtn);
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseAuth fauth = FirebaseAuth.getInstance();
                    FirebaseUser curruser = fauth.getCurrentUser();
                    // getting the values from our views
                    dbHelper.OpenWriteAble();
                    Cart cart = new Cart(curruser.getUid(), productList.get(getLayoutPosition()).getPid(),1);
                    cart.Add(dbHelper.getDb());
                    dbHelper.Close();
                    Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
                }
            });
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductInfo.class);
                intent.putExtra("id", productList.get(getLayoutPosition()).getPid() + "");
                v.getContext().startActivity(intent);

        }
    }
}