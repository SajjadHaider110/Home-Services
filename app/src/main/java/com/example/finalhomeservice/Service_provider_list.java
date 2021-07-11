package com.example.finalhomeservice;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Service_provider_list extends Fragment {

    TextView tv;
    RecyclerView recyclerView;
    RecycleAdapter recycleAdapter;
    DatabaseReference mref;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    String city;
    String type;

    public Service_provider_list() {
        // Required empty public constructor
    }

    public Service_provider_list(String city, String type) {
        this.city=city;
        this.type=type;
    }


    public static Service_provider_list newInstance(String param1, String param2) {
        Service_provider_list fragment = new Service_provider_list();
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
        View rootView=inflater.inflate(R.layout.fragment_service_provider_list, container, false);
//        String value= getArguments().getString("info");
//        String Type = getArguments().getString("Type");
        if(city==null){
            Toast.makeText(getContext(), "No City Selected", Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(getContext(), ""+city+" type:"+type, Toast.LENGTH_SHORT).show();

        }
        mref=FirebaseDatabase.getInstance().getReference();
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        FirebaseRecyclerOptions<ServiceModel>   options=
                new FirebaseRecyclerOptions.Builder<ServiceModel>()
                        .setQuery(mref.child("HomeService").child("Service Providers Profile").child(city).child(type),ServiceModel.class)
                        .build();
        recycleAdapter = new RecycleAdapter(options);
        recyclerView.setAdapter(recycleAdapter);




        return rootView;


        // Inflate the layout for this fragment



    }

    @Override
    public void onStart() {
        super.onStart();
        recycleAdapter.startListening();

    }

    @Override
    public void onStop() {
        super.onStop();
        recycleAdapter.stopListening();
    }
}