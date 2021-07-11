package com.example.finalhomeservice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;



public class homefragment extends Fragment {

    List<String> name;
    List<Integer> icons;

   // RecyclerView.Adapter adapter;

    GridView gird;
  //  RecyclerView recyclerView;
  AutoCompleteTextView autoCompleteTextView;
    String city,type;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView=inflater.inflate(R.layout.fragment_homefragment,container,false);
        autoCompleteTextView=rootView.findViewById(R.id.autoCompleteText);
        String []option={"Karachi","Hyderabad","Sukkar","Nawabshah"};
        ArrayAdapter arrayAdapter= new ArrayAdapter(getContext(),R.layout.option_item,option);
        autoCompleteTextView.setText(arrayAdapter.getItem(0).toString(),false);

        autoCompleteTextView.setAdapter(arrayAdapter);

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             city  = autoCompleteTextView.getText().toString();

            }
        });



        icons=new ArrayList<>();
        name=new ArrayList<>();

        icons.add(R.drawable.light);
        icons.add(R.drawable.carpenter);
        icons.add(R.drawable.repair);
        icons.add(R.drawable.plumber);
        icons.add(R.drawable.gardener);
        icons.add(R.drawable.paint);
        icons.add(R.drawable.washing);
        icons.add(R.drawable.logout);

        name.add("Electrician");
        name.add("Carpenter");
        name.add("Repairer" );
        name.add("Plumber");
        name.add("Gardener");
        name.add("Painter");
        name.add("Cleaner");
        name.add("Logout");
//        recyclerView=rootView.findViewById(R.id.recycle);
//
//        adapter= new Adapter(getContext(), name, icons);



//        GridLayoutManager gridLayoutManager= new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        recyclerView.setAdapter(adapter);
//

        gird=rootView.findViewById(R.id.gird_view);

        MyAdapter girdadapter=new MyAdapter(getContext(),name,icons);
        gird.setAdapter(girdadapter);
        gird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                Service_provider_list ldf= new Service_provider_list();
//                Bundle args= new Bundle();
//                args.putString("info",city);
//                ldf.setArguments(args);
                if(position==0)
                {
                   // args.putString("Type","Electrician");
                    type="Electrician";
                }
                else if (position==1)
                {
                    //args.putString("Type","Carpenter");
                    type="Carpenter";
                }
                else if (position==2)
                {
                   // args.putString("Type","Repairer");
                    type="Repairer";
                }
                else if (position==3)
                {
                  //  args.putString("Type","Plumber");
                    type="Plumber";
                }
                else if (position==4)
                {
                    //args.putString("Type","Gardener");
                    type="Gardener";
                }
                else if (position==5)
                {
                  //  args.putString("Type","Painter");
                    type="Painter";
                }
                else if (position==6)
                {
                    //args.putString("Type","Cleaner");
                    type="Cleaner";
                }

                if(position==7){

                    AlertDialog.Builder builder= new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to Logout.")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    getActivity().startActivity(new Intent(getContext(),MainActivity.class));
                                   getActivity().finish();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog= builder.create();
                    alertDialog.show();




//                    getActivity().startActivity(new Intent(getContext(),new MainActivity().getClass()));
                    //FragmentTransaction fr = getFragmentManager().beginTransaction();
                    //fr.remove(homefragment.this).commit();

                }
                else {

//                if(getActivity().getSupportFragmentManager()!=null && getActivity().getSupportFragmentManager().getFragments().size()>0){
//                    for(int i=0; i<getActivity().getSupportFragmentManager().getFragments().size(); i++){
//                        Fragment frg=getActivity().getSupportFragmentManager().getFragments().get(i);
//                        if(frg!=null){
//                            getActivity().getSupportFragmentManager().beginTransaction().remove(frg).commit();
//                        }
//                    }
//                }
//                String backstate=homefragment.class.getName();
//                FragmentManager manager= getActivity().getSupportFragmentManager();
//                boolean fragmentPopped = manager.popBackStackImmediate (backstate, 0);
//                if(!fragmentPopped){
//                    FragmentTransaction ft = manager.beginTransaction();
//                    ft.replace(R.id.wraper,new Service_provider_list(city,type));
//                    ft.addToBackStack(backstate);
//                    ft.commit();
//                }



//                FragmentTransaction fr = getFragmentManager().beginTransaction();
//                fr.replace(R.id.wraper,new Service_provider_list());
//                fr.addToBackStack(backstate);
//                fr.commit();

                    if (city==null){
    Toast.makeText(getContext(), "Please First Select Your City!", Toast.LENGTH_SHORT).show();
}
                    else
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.wraper, new Service_provider_list(city,type)).addToBackStack(null).commit();


            }}
        });






        //gird.setAdapter(new girdAdapter(name,icons,getContext()));

        //gird.setAdapter(new girdAdapter(name,icons, (FragmentActivity) getContext()));

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        String []option={"Karachi","Hyderabad","Sukkar","Nawabshah"};
        ArrayAdapter arrayAdapter= new ArrayAdapter(getContext(),R.layout.option_item,option);
        autoCompleteTextView.setText(city);
        autoCompleteTextView.setAdapter(arrayAdapter);
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                city  = arrayAdapter.getItem(position).toString();
//            }
//        });
    }
}