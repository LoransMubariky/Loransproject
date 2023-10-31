package com.example.loransmubarikyproj;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import org.w3c.dom.Text;

public class SignupActivity extends AppCompatActivity {

    EditText admincode;
    Button registerButton;

    EditText fullNameEditText;
EditText emailEditText;
EditText passwordEditText;
EditText repasswordEditText;
TextView errorText;
    Switch isadmin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        fullNameEditText=findViewById(R.id.ETFullName);
        emailEditText=findViewById(R.id.ETEmailSignup);
        passwordEditText=findViewById(R.id.ETPasswordSignup);
        repasswordEditText=findViewById(R.id.ETrePasswordSignup);
        registerButton=findViewById(R.id.BTRegister);
        errorText=findViewById(R.id.errortext);
        isadmin = findViewById(R.id.SwAdmin);
        admincode = findViewById(R.id.etAdminCode);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAccount();
            }
        });
    }
    private void createNewAccount(){
        errorText.setVisibility(View.GONE);
        if(fullNameEditText.getText().toString().equals("")){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("please enter your full name");
            return;
        }
        if(emailEditText.getText().toString().equals("")){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("please enter your email");
            return;
        }
        if(passwordEditText.getText().toString().equals("")){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("please enter your password");
            return;
        }
        if(repasswordEditText.getText().toString().equals("")){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("please confirm your password");
            return;
        }
        if(!repasswordEditText.getText().toString().equals(passwordEditText.getText().toString())){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("password dosn't match");
            return;
        }
        if(isadmin.isChecked() && !admincode.getText().toString().equals("13579")){
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("admin code is incorrect");
            return;
        }

        final FirebaseAuth mAuth= FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            FirebaseUser user = mAuth.getCurrentUser();
                            String name=fullNameEditText.getText().toString();
                            if(isadmin.isChecked()){
                                name = "admin: "+name;
                            }



                            UserProfileChangeRequest  profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                startActivity(new Intent(SignupActivity.this,MainActivity.class));
                                            }
                                        }
                                    });
                        } else {

                            //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            errorText.setVisibility(View.VISIBLE);
                            errorText.setText(task.getException().getMessage());

                        }
                    }
                });
    }
}