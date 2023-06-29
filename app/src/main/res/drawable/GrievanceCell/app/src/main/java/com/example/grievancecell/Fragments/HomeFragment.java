package com.example.grievancecell.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grievancecell.Adapters.SliderAdapter;
import com.example.grievancecell.ComplaintRegistration;
import com.example.grievancecell.MainActivity;
import com.example.grievancecell.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class HomeFragment extends Fragment {

    SliderView sliderView;
    int [] images={R.drawable.image1,
            R.drawable.image2,
            R.drawable.image3,
            R.drawable.image4};
    String name="User",mobNum;
    TextView textView;
    FloatingActionButton floatingActionButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if(getArguments()!=null) {
            name = getArguments().getString("name");
        }
        mobNum= FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        textView=view.findViewById(R.id.welcomeText);
        floatingActionButton=view.findViewById(R.id.fab);

        textView.setText("Welcome "+name);
        sliderView=view.findViewById(R.id.SliderView);
        SliderAdapter sliderAdapter=new SliderAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        sliderView.startAutoCycle();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(), ComplaintRegistration.class);
                startActivity(intent);
            }
        });
        return view;
    }
}