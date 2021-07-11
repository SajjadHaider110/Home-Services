package com.example.finalhomeservice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecycleAdapter extends FirebaseRecyclerAdapter<ServiceModel,RecycleAdapter.myviewholder> {


    public RecycleAdapter(@NonNull FirebaseRecyclerOptions<ServiceModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull ServiceModel model) {

        holder.nametext.setText(model.getFullName());
        holder.addresstext.setText(model.getAddress());
        holder.phonetext.setText(model.getMobile());
        Glide.with(holder.img1.getContext()).load(model.geturl()).into(holder.img1);

        holder.img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatActivity activity=  (AppCompatActivity)v.getContext();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.wraper, new ServiceDetail(model.FullName,model.Address,model.Mobile,model.url)).addToBackStack(null).commit();
            }
        });

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);


        return new myviewholder(view);
    }

    public class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView img1;
        TextView nametext,addresstext,phonetext;

        public myviewholder(@NonNull View itemView) {
            super(itemView);
            img1=itemView.findViewById(R.id.img1);
            nametext=itemView.findViewById(R.id.nametext);
            addresstext=itemView.findViewById(R.id.addresstext);
            phonetext=itemView.findViewById(R.id.phonetext);

        }
    }
}
