package com.example.finalhomeservice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;


public class ServiceDetail extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String name, address, mobile,url;
    public ServiceDetail() {

    }

    public ServiceDetail(String name, String address, String mobile, String url) {
    this.name=name;
    this.address=address;
    this.mobile= mobile;
    this.url= url;
    }


    public static ServiceDetail newInstance(String param1, String param2) {
        ServiceDetail fragment = new ServiceDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.service_detail, container, false);

        CircleImageView imageholder=view.findViewById(R.id.imagegholder);
        TextView nameholder=view.findViewById(R.id.nameholder);
        TextView addressholder=view.findViewById(R.id.addressholder);
        TextView mobileholder=view.findViewById(R.id.mobileholder);
        ImageButton callbtn=view.findViewById(R.id.btcall);

        nameholder.setText(name);
        addressholder.setText(address);
        mobileholder.setText(mobile);
        callbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s="tel:"+mobile;
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(s));
                startActivity(intent);
            }
        });
//        imageholder.setImageResource(url);
        Glide.with(getContext()).load(url).into(imageholder);

        return  view;


    }
   public void onBackPressed(){
       AppCompatActivity activity=  (AppCompatActivity)getContext();
       activity.getSupportFragmentManager().beginTransaction().replace(R.id.wraper, new Service_provider_list()).addToBackStack(null).commit();

   }

}