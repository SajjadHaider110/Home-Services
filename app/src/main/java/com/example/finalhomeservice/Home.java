package com.example.finalhomeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
Button logoutbtn;
BottomNavigationView bnv;



   // String name[]={"Electrician","Carpenter","Repairer","Plumber","Gardener","Painter","Cleaner","Logout"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        getSupportFragmentManager().beginTransaction().replace(R.id.wraper, new homefragment()).commit();
        bnv=(BottomNavigationView) findViewById(R.id.bottomNavigation);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                Fragment temp= null;

                switch (item.getItemId()){

                    case R.id.menu_home: temp=new homefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.wraper, temp).commit();
                    break;

                    case R.id.menu_setting:
                        temp=new homefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.wraper, temp).commit();
                        Toast.makeText(Home.this, "Setting Menu is Clicked", Toast.LENGTH_SHORT).show();
                    break;
                    
                    case R.id.menu_profile:
                        temp=new homefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.wraper, temp).commit();
                        Toast.makeText(Home.this, "Profile Menu is Clicked!", Toast.LENGTH_SHORT).show();
                    break;
                }

//                getSupportFragmentManager().beginTransaction().replace(R.id.wraper, temp).commit();

                if(item.getItemId()==R.id.menu_logout){
                    AlertDialog.Builder builder= new AlertDialog.Builder(Home.this);
                    builder.setMessage("Are you sure you want to Logout.")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(Home.this,MainActivity.class));
                                    finish();
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
                }

                return true;
            }

        });
//        logoutbtn=findViewById(R.id.logoutbtn);



       // gird.setAdapter(new girdAdapter(name,icons,getApplicationContext()));
//        logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(Home.this,MainActivity.class));
//                finish();
//            }
//        });
    }
}