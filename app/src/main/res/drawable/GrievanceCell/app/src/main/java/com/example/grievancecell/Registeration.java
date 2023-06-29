package com.example.grievancecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Registeration extends AppCompatActivity {

    TextView textView;
    EditText name,rollNum,mobNum,password1,password2;
    Button signUpBtn;
    FirebaseAuth mAuth;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);
        progressBar=(ProgressBar)findViewById(R.id.sendingOtp);
        mAuth=FirebaseAuth.getInstance();

        textView=(TextView) findViewById(R.id.signIn);
        name=(EditText) findViewById(R.id.name);
        rollNum=(EditText) findViewById(R.id.rollNum);
        mobNum=(EditText) findViewById(R.id.phoneNum);
        password1=(EditText) findViewById(R.id.password1);
        password2=(EditText) findViewById(R.id.password2);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Registeration.this,SignIn.class);
                startActivity(intent);
            }
        });
        signUpBtn=(Button) findViewById(R.id.signInBtn);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((password1.getText().toString().isEmpty()) || !(password1.getText().toString().equals(password2.getText().toString())))
                    Toast.makeText(Registeration.this, "Enter the password correctly", Toast.LENGTH_SHORT).show();
                else {
                    if(mobNum.getText().toString().length()==10) {
                        sendOTP("+91"+mobNum.getText().toString());

                    }else{
                        Toast.makeText(Registeration.this, "Enter the 10 digit Phone Number", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void sendOTP(String s) {
        signUpBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthOptions options=PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(s)
                .setTimeout(10l,TimeUnit.SECONDS)
                .setActivity(Registeration.this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            signUpBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            signUpBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Registeration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            signUpBtn.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(Registeration.this, VerifyOTP.class);
            intent.putExtra("name", name.getText().toString());
            intent.putExtra("rollNum", rollNum.getText().toString());
            intent.putExtra("mobNum", mobNum.getText().toString());
            intent.putExtra("password", password1.getText().toString());
            intent.putExtra("activity","signUp");
            intent.putExtra("backendOtp",s);
            startActivity(intent);
        }
    };
}