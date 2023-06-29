package com.example.grievancecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.grievancecell.Models.Complaint;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ComplaintRegistration extends AppCompatActivity {
    private int COMPLAINT_COUNT=0;
    EditText doi,evidence,grievanceType,grievanceDetail;
    int day,month,year;
    Button registerBtn;
    String userMobNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_registration);
        doi=findViewById(R.id.doi);
        evidence=findViewById(R.id.evidence);
        grievanceType=findViewById(R.id.grievanceType);
        grievanceDetail=findViewById(R.id.detail);
        registerBtn=(Button)findViewById(R.id.registerComplaintBtn);
        Calendar calendar=Calendar.getInstance();
        doi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=calendar.get(Calendar.YEAR);
                month= calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog=new DatePickerDialog(ComplaintRegistration.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        doi.setText(SimpleDateFormat.getDateInstance().format(calendar.getTime()));
                    }
                },year, month,day);
                datePickerDialog.show();
            }
        });
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userMobNum=FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                COMPLAINT_COUNT++;
                String CId="C"+userMobNum.substring(3,8)+COMPLAINT_COUNT;
                Complaint complaint=new Complaint(CId,
                        grievanceType.getText().toString(),
                        doi.getText().toString(),
                        grievanceDetail.getText().toString(),
                        userMobNum.substring(3));
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference("Complaints");
                String id=databaseReference.push().getKey();
                databaseReference.child(id).setValue(complaint).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ComplaintRegistration.this, CId, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}