package com.example.grievancecell.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grievancecell.CheckStatus;
import com.example.grievancecell.Models.Complaint;
import com.example.grievancecell.R;

import java.util.ArrayList;

public class GrievanceAdapter extends RecyclerView.Adapter<GrievanceAdapter.ViewHolder>{

    ArrayList<Complaint> list;
    Context context;

    public GrievanceAdapter(ArrayList<Complaint> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_complaint_box,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Complaint complaint=list.get(position);
        holder.cId.setText(complaint.getCId());
        holder.grievanceType.setText(complaint.getGrievanceType());
        holder.fetchStatusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, CheckStatus.class);
                intent.putExtra("CId",holder.cId.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView cId, grievanceType;
        ImageView fetchStatusBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cId=itemView.findViewById(R.id.complaintId);
            grievanceType=itemView.findViewById(R.id.complaintType);
            fetchStatusBtn=itemView.findViewById(R.id.fetchStatusBtn);
        }
    }
}
