package com.example.grievancecell;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.grievancecell.Models.Complaint;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class CheckStatus extends AppCompatActivity {

    String CId,complaintStatus="";
    TextView cid,type,status,doi,detail,dor;
    TextView level2_1, level2_2,level2_3;
    ImageView level2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        CId=getIntent().getStringExtra("CId");
        cid= findViewById(R.id.cid);
        type=findViewById(R.id.detail_type);
        doi=findViewById(R.id.detail_doi);
        dor=findViewById(R.id.date);
        status=findViewById(R.id.detail_status);
        detail=findViewById(R.id.detail_detail);

        cid.setText("Id : "+CId);

        Query query=FirebaseDatabase.getInstance().getReference().child("Complaints")
                .orderByChild("cid").equalTo(CId);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Complaint complaint=dataSnapshot.getValue(Complaint.class);
                    type.setText("Type : "+complaint.getGrievanceType());
                    status.setText(complaint.getStatus());
                    complaintStatus=complaint.getStatus();
                    if(complaintStatus.equalsIgnoreCase("Registered")){
                        level2=findViewById(R.id.imageView3);
                        level2.setVisibility(View.GONE);
                        level2_1=findViewById(R.id.level2);
                        level2_1.setVisibility(View.GONE);
                        level2_2=findViewById(R.id.textView5);
                        level2_2.setVisibility(View.GONE);
                        level2_3=findViewById(R.id.level2_3);
                        level2_3.setVisibility(View.GONE);
                    }
                    doi.setText("Date of Incident : "+complaint.getDoi());
                    dor.setText(complaint.getDoi());
                    detail.setText(complaint.getGrievanceDetail());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CheckStatus.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}