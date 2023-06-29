package com.example.grievancecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class SignIn extends AppCompatActivity {
    TextView textView;
    EditText mobNum,password;
    Button signInBtn;
    ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mAuth=FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser()!=null){
            Intent intent=new Intent(SignIn.this,MainActivity.class);
            startActivity(intent);
        }
        textView=(TextView) findViewById(R.id.signUp);
        mobNum = (EditText)findViewById(R.id.registeredMobNum);
        password=(EditText)findViewById(R.id.password);
        signInBtn=(Button)findViewById(R.id.loginBtn);
        progressBar = (ProgressBar)findViewById(R.id.otpVerification);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otpVerification("+91"+mobNum.getText().toString());
                login();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SignIn.this,Registeration.class);
                startActivity(intent);
            }
        });
    }

    private void login() {

    }

    private void otpVerification(String s) {
        signInBtn.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                .setPhoneNumber(s)
                .setTimeout(10l, TimeUnit.SECONDS)
                .setActivity(SignIn.this)
                .setCallbacks(mCallbacks)
                .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

        private final PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                signInBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(SignIn.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                signInBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                Intent intent = new Intent(SignIn.this, VerifyOTP.class);
                intent.putExtra("mobNum", mobNum.getText().toString());
                intent.putExtra("password", password.getText().toString());
                intent.putExtra("activity","signIn");
                intent.putExtra("backendOtp",s);
                startActivity(intent);
            }
        };



}