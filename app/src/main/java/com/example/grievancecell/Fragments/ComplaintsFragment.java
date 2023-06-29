package com.example.grievancecell.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.grievancecell.Adapters.GrievanceAdapter;
import com.example.grievancecell.Models.Complaint;
import com.example.grievancecell.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ComplaintsFragment extends Fragment {


    public ComplaintsFragment() {
        // Required empty public constructor
    }
    ArrayList<Complaint> list=new ArrayList<>();
    RecyclerView recyclerView;
    Button fetchStatusBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_complaints, container, false);
        recyclerView=view.findViewById(R.id.listOfComplaints);
        GrievanceAdapter grievanceAdapter=new GrievanceAdapter(list,getContext());
        recyclerView.setAdapter(grievanceAdapter);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        Query query=FirebaseDatabase.getInstance().getReference().child("Complaints")
                        .orderByChild("userMobNum")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber().substring(3,13));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Complaint complaint=dataSnapshot.getValue(Complaint.class);
                    list.add(complaint);
                }
                grievanceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getCode(), Toast.LENGTH_SHORT).show();
            }
        });


        return view;
    }
}