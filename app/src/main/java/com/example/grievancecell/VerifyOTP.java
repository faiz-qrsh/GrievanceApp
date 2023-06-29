package com.example.grievancecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grievancecell.Models.Complaint;
import com.example.grievancecell.Models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class VerifyOTP extends AppCompatActivity {

    EditText d1,d2,d3,d4,d5,d6;
    Button verifyBtn;
    TextView textView,resendOtp;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String name,mobNum,rollNum,password,backendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otp);
        d1=(EditText) findViewById(R.id.digit1);
        d2=(EditText) findViewById(R.id.digit2);
        d3=(EditText) findViewById(R.id.digit3);
        d4=(EditText) findViewById(R.id.digit4);
        d5=(EditText) findViewById(R.id.digit5);
        d6=(EditText) findViewById(R.id.digit6);

        mAuth=FirebaseAuth.getInstance();

        progressBar=(ProgressBar)findViewById(R.id.verifyingOtp);
        resendOtp=(TextView)findViewById(R.id.resendOTP);
        textView=(TextView)findViewById(R.id.enteredNumber);
        verifyBtn=(Button) findViewById(R.id.verifyBtn);
        mobNum=getIntent().getStringExtra("mobNum");
        textView.setText(String.format("+91-%s",mobNum));
        backendOTP=getIntent().getStringExtra("backendOtp");

        nextEditText();

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                verifyOTP();
            }
        });

        resendOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyBtn.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                sendOTP();
            }
        });
    }

    private void sendOTP() {
        PhoneAuthProvider.getInstance().verifyPhoneNumber("+91" + mobNum, 60,
                TimeUnit.SECONDS, VerifyOTP.this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {
                        verifyBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(VerifyOTP.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                        backendOTP = s;
                        verifyBtn.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(VerifyOTP.this, "OTP sent successfully", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void verifyOTP() {

        if (!d1.getText().toString().trim().isEmpty() && !d2.getText().toString().trim().isEmpty() && !d3.getText().toString().trim().isEmpty() && !d4.getText().toString().trim().isEmpty() && !d5.getText().toString().trim().isEmpty() && !d6.getText().toString().trim().isEmpty()) {
            String enteredOTP = d1.getText().toString().trim() +
                    d2.getText().toString().trim() +
                    d3.getText().toString().trim() +
                    d4.getText().toString().trim() +
                    d5.getText().toString().trim() +
                    d6.getText().toString().trim();
            if (backendOTP != null) {
                PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(backendOTP, enteredOTP);
                FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            verifyBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            if(getIntent().getStringExtra("activity").equalsIgnoreCase("signUp")){
                                createUser();
                            }else{
                                checkUser();
                            }

                        }
                        else {
                            Toast.makeText(VerifyOTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            verifyBtn.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            } else{
                Toast.makeText(VerifyOTP.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                verifyBtn.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        } else
            Toast.makeText(VerifyOTP.this, "Enter the OTP correctly", Toast.LENGTH_SHORT).show();
    }

    private void checkUser() {
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Users");
        String id=databaseReference.push().getKey();
        mobNum=getIntent().getStringExtra("mobNum");
        password=getIntent().getStringExtra("password");
        Query checkUser=databaseReference.orderByChild("mobNum").equalTo(mobNum);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    login();
                }else{
                    Toast.makeText(VerifyOTP.this, "User does not exist", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void createUser() {
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("Users");
        String id=databaseReference.push().getKey();
        name=getIntent().getStringExtra("name");
        rollNum=getIntent().getStringExtra("rollNum");
        mobNum=getIntent().getStringExtra("mobNum");
        password=getIntent().getStringExtra("password");
        Query checkUser=databaseReference.orderByChild("mobNum").equalTo(mobNum);
        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(VerifyOTP.this, "User already exists", Toast.LENGTH_SHORT).show();

                }else{
                    User newUser=new User(id, name, rollNum, mobNum, password);
                    databaseReference.child(id).setValue(newUser);
                    Toast.makeText(VerifyOTP.this, "User created successfully", Toast.LENGTH_SHORT).show();
                    login();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void login() {
        Intent intent = new Intent(VerifyOTP.this, MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("rollNum", rollNum);
        intent.putExtra("mobNum", mobNum);
        intent.putExtra("password", password);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    public void nextEditText() {
        d1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    d2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        d2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    d3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        d3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    d4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        d4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    d5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        d5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().trim().isEmpty()) {
                    d6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}