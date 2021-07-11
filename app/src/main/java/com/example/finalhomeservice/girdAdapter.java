package com.example.finalhomeservice;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class girdAdapter extends BaseAdapter {


    List<String> name;
    List<Integer> icons;
    Context context;

    public girdAdapter(List<String> name, List<Integer> icons, Context context, LayoutInflater inflater) {
        this.name = name;
        this.icons = icons;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    LayoutInflater inflater;

    public girdAdapter(List<String> name, List<Integer> icons, Context applicationContext) {
    }

//    public girdAdapter(String[] name, int[] images, Context context) {
//        this.name = name;
//        this.images = images;
//        this.context = context;
//
//        inflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }



    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.singleframe,parent,false);
            @SuppressLint("WrongViewCast") ImageView img=(ImageView)convertView.findViewById(R.id.textView);
            @SuppressLint("WrongViewCast") TextView tv=(TextView)convertView.findViewById(R.id.imageView);

            img.setImageResource(icons.get(position));
            tv.setText(name.get(position));
        }
        //LayoutInflater layoutInflater=(LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //View view=layoutInflater.inflate(R.layout.singleframe,null);



        return convertView;
    }
}
