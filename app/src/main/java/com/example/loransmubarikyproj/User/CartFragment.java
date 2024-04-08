package com.example.loransmubarikyproj.User;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loransmubarikyproj.Class.AlarmNotification;
import com.example.loransmubarikyproj.Class.AlarmReceiver;
import com.example.loransmubarikyproj.Class.Cart;
import com.example.loransmubarikyproj.Class.CartAdapter;
import com.example.loransmubarikyproj.DataBase.DBHelper;
import com.example.loransmubarikyproj.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.example.loransmubarikyproj.DataBase.TablesString.CartTable.COLUMN_AMOUNT;
import static com.example.loransmubarikyproj.DataBase.TablesString.CartTable.COLUMN_PRODUCT_ID;
import static com.example.loransmubarikyproj.DataBase.TablesString.CartTable.COLUMN_USER_ID;
import static com.example.loransmubarikyproj.DataBase.TablesString.CartTable._ID;


public class CartFragment extends Fragment {

    RecyclerView recyclerView;
    Button checkout;
    List<Cart> cartList;
    DBHelper dbHelper;
    RecyclerView.Adapter mAdapter;
    CardView orderplace;
    TextView price;

    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        recyclerView = v.findViewById(R.id.cartRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(null));
        cartList = new ArrayList<>();
        price = v.findViewById(R.id.cartFragmentTotalPriceTv);
        dbHelper = new DBHelper(getContext());
        dbHelper = dbHelper.OpenReadAble();
        String uid = FirebaseAuth.getInstance().getUid();
        Cart p = new Cart(), p2;
        Cursor c = p.SelectByUserId(dbHelper.getDb(),uid);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            p2 = new Cart(c.getInt(c.getColumnIndexOrThrow(_ID)),
                    c.getString(c.getColumnIndexOrThrow(COLUMN_USER_ID)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_PRODUCT_ID)),
                    c.getInt(c.getColumnIndexOrThrow(COLUMN_AMOUNT)));
            cartList.add(p2);
            c.moveToNext();
        }
        dbHelper.Close();
        // adapter
        mAdapter = new CartAdapter(v,getContext(), cartList);
        recyclerView.setAdapter(mAdapter);
        orderplace = v.findViewById(R.id.cartFragmentCardView);
        checkout = v.findViewById(R.id.cartFragmentCheckoutBtn);
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.OpenWriteAble();
                orderplace.setVisibility(View.VISIBLE);
                for(Cart cart : cartList){
                    cart.Delete(dbHelper.getDb(),cart.getCartid());
                }
                dbHelper.Close();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("channel_01","my channel", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("my descript channel");
                    NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
                    notificationManager.createNotificationChannel(channel);
                }

                Intent intent = new Intent(getContext(), AlarmNotification.class);
                pendingIntent = PendingIntent.getBroadcast(getContext(), 1, intent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                long delaymili = SystemClock.elapsedRealtime()+7000;
                alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, delaymili, pendingIntent);
                //Toast.makeText(getContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }

}