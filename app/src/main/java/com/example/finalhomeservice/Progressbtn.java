package com.example.finalhomeservice;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

class Progressbtn {
    private CardView cardView;
    private ProgressBar progressBar;
    private ConstraintLayout layout;
    private TextView textView;
    Animation fade_in;
    Progressbtn(Context ct, View view){
        cardView= view.findViewById(R.id.cardView);
        progressBar= view.findViewById(R.id.progressBar);
        textView= view.findViewById(R.id.textView);
        layout=view.findViewById(R.id.constrain_layout);
    }
    void buttonAcitivated(){
        progressBar.setVisibility(View.GONE);
        textView.setText("Please Wait...");
    }
    void buttonFinished(){
        progressBar.setVisibility(View.VISIBLE);
        textView.setText("Done");
        layout.setBackgroundColor(cardView.getResources().getColor(R.color.green));
    }
}
