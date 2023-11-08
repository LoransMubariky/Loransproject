package com.example.loransmubarikyproj;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.view.View;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loransmubarikyproj.Admin.AddProduct;
import com.example.loransmubarikyproj.Admin.ShowProduct;
import com.example.loransmubarikyproj.User.CartFragment;
import com.example.loransmubarikyproj.User.HomeFragment;
import com.example.loransmubarikyproj.User.InfoFragment;
import com.example.loransmubarikyproj.User.ProuductFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView username,email;

    private DrawerLayout drawerLayout;

    FirebaseAuth fauth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignore red line errors
        setSupportActionBar(toolbar);
        fauth=FirebaseAuth.getInstance();

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        FirebaseUser user = fauth.getCurrentUser();
        if(user!=null) {
            if (user.getDisplayName().startsWith("admin:")) {
                Intent i = new Intent(MainActivity.this, ShowProduct.class);
                startActivity(i);
            }

            View header = navigationView.getHeaderView(0);
            username = header.findViewById(R.id.usernameheader);
            email = header.findViewById((R.id.emailheader));
            username.setText(user.getDisplayName());
            email.setText(user.getEmail());
        }
            else {
            Intent i = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(i);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav,
                R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
     }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(R.id.nav_home==item.getItemId()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        }
        else if(R.id.nav_ShoppingBag==item.getItemId()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new CartFragment()).commit();
        }
        else if(R.id.nav_info==item.getItemId()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new InfoFragment()).commit();
        }
        else if(R.id.nav_about==item.getItemId()){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProuductFragment()).commit();
        }
        else if(R.id.nav_logout==item.getItemId()){
            fauth.signOut();
            startActivity(new Intent(this,LoginActivity.class));
            Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show();
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}