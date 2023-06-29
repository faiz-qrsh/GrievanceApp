package com.example.grievancecell.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.grievancecell.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.Holder> {

    int img[];

    public SliderAdapter(int[] img) {
        this.img = img;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item,parent,false);

        return new Holder(view);
    }


    public void onBindViewHolder(Holder viewHolder, int position) {
        viewHolder.imageView.setImageResource(img[position]);
    }



    @Override
    public int getCount() {
        return img.length;
    }


    public class Holder extends SliderViewAdapter.ViewHolder{

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.sliderImg);
        }
    }
}
