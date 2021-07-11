package com.example.finalhomeservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "";
    EditText Number,Otptext;
    Button verifybtn,codebtn;
    TextView otptitle, resendtext,codetitle;
   // View view;
    String VerificationId, phone_no;
    private CountryCodePicker ccp;
    PhoneAuthProvider.ForceResendingToken token;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mdatabase;
    private DatabaseReference mref,myref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();


        mAuth = FirebaseAuth.getInstance();
        mdatabase=FirebaseDatabase.getInstance();
        mref=mdatabase.getReference().child("HomeService");


        otptitle=findViewById(R.id.otptitle);
        Otptext=findViewById(R.id.otpcode);
        resendtext=findViewById(R.id.resendotp);
        verifybtn=findViewById(R.id.verifybtn);
        codetitle=findViewById(R.id.codetitle);

        otptitle.setVisibility(View.GONE);
        Otptext.setVisibility(View.GONE);
        verifybtn.setVisibility(View.GONE);
        resendtext.setVisibility(View.GONE);
        codetitle.setVisibility(View.VISIBLE);





        Number=(EditText)findViewById(R.id.phone_no);
        ccp=findViewById(R.id.cp);
        ccp.registerCarrierNumberEditText(Number);
        
        codebtn=findViewById(R.id.codebtn);
        ProgressDialog dialog;
        dialog= new ProgressDialog(this);

        codebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (TextUtils.isEmpty(Number.getText().toString().trim())) {
                    Toast.makeText(MainActivity.this, " please enter Number", Toast.LENGTH_SHORT).show();

                }

                else {




                    dialog.setMessage("Sending OTP");
                    dialog.setCancelable(false);
                    dialog.show();

                    phone_no = ccp.getFullNumberWithPlus().replace(" ", "");
                    Authentication(phone_no);
                }






//                final Progressbtn progressbtn=new Progressbtn(MainActivity.this,view);
//                progressbtn.buttonAcitivated();
//               Handler handler= new Handler();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressbtn.buttonFinished();
//                    }
//                },30000);
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Otptext.getText().toString().isEmpty()){
                    Otptext.setError("Enter Otp First!");
                    return;
                }
             PhoneAuthCredential credential= PhoneAuthProvider.getCredential(VerificationId,Otptext.getText().toString());
                signInWithPhoneAuthCredential(credential);
            }
        });

        resendtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentication(phone_no);
                resendtext.setVisibility(View.GONE);
            }
        });


        callbacks= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                VerificationId=s;
                token= forceResendingToken;
                Toast.makeText(MainActivity.this, "OTP Has Been Sent!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                ccp.setVisibility(View.GONE);
                Number.setVisibility(View.GONE);

                    codebtn.setVisibility(View.GONE);
                otptitle.setVisibility(View.VISIBLE);
                Otptext.setVisibility(View.VISIBLE);
                verifybtn.setVisibility(View.VISIBLE);
                resendtext.setVisibility(View.GONE);
                codetitle.setVisibility(View.GONE);

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                resendtext.setVisibility(View.VISIBLE);
            }
        };




    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        myref=mref.child("UserProfile");
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {


                            myref.child(mAuth.getCurrentUser().getPhoneNumber()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if(snapshot.exists()){

                                        Toast.makeText(MainActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
                                        sendToHome();
                                    }
                                    else {
                                        startActivity(new Intent(MainActivity.this,UserRegistration.class));
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });




//                            String key=mAuth.getCurrentUser().getUid();
//                            mref.child("Registered Users").child(key).addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                    if (snapshot.exists()) {
//                                        Toast.makeText(MainActivity.this, "signInWithCredential:success", Toast.LENGTH_SHORT).show();
//                                        sendToHome();
//                                    }
//                                    else{
//                                        startActivity(new Intent(MainActivity.this,UserRegistration.class));
//                                        finish();
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
                            // Sign in success, update UI with the signed-in user's information

//                            Intent intent= new Intent(MainActivity.this,Home.class);
//                            startActivity(intent);
                        //    sendToHome();

                            //  FirebaseUser user = task.getResult().getUser();
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Toast.makeText(MainActivity.this, "signInWithCredential:failure", Toast.LENGTH_SHORT).show();
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });

    }

    private void Authentication(String gno) {

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(gno)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(callbacks)
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);

    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user=mAuth.getCurrentUser();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            sendToHome();
        }
    }

    private void sendToHome() {
        startActivity(new Intent(MainActivity.this,Home.class));
        finish();
    }
}