package com.example.finalhomeservice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.storage.StorageManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserRegistration extends AppCompatActivity {
FirebaseDatabase mdatebase;
DatabaseReference mref;
FirebaseAuth mauth;
StorageReference storageReference;

Button registerbtn;
EditText fullname,email;
CircleImageView uimage;
Uri filepath;
Bitmap bitmap;
String Userid="";
    String Name,Email,Mobile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);
        mauth=FirebaseAuth.getInstance();
        mdatebase=FirebaseDatabase.getInstance();
        mref= mdatebase.getReference().child("HomeService");
        storageReference= FirebaseStorage.getInstance().getReference();

        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Userid= user.getUid();



        uimage=(CircleImageView) findViewById(R.id.uimage);
        uimage.bringToFront();
        uimage.setClickable(true);
        fullname=findViewById(R.id.fullname);
        email=findViewById(R.id.email);
        registerbtn=findViewById(R.id.registerbtn);

        uimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Dexter.withContext(getApplicationContext())
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Please Select a Image File"),101);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();




                Toast.makeText(UserRegistration.this, "Clicked", Toast.LENGTH_SHORT).show();
//                Dexter.withContext()
//                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
//                        .withListener(new PermissionListener() {
//                            @Override
//                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
//                                Intent intent= new Intent(Intent.ACTION_GET_CONTENT);
//                                intent.setType("image/*");
//                                startActivityForResult(Intent.createChooser(intent,"Please Select File"),101);
//                            }
//
//                            @Override
//                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
//
//                            }
//
//                            @Override
//                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
//                                permissionToken.continuePermissionRequest();
//                            }
//                        }).check();

            }
        });

//        uimage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//
//            }
//        });



        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Name=fullname.getText().toString();
                Email=email.getText().toString();
                Mobile=mauth.getCurrentUser().getPhoneNumber();

            if(!fullname.getText().toString().isEmpty()&&!email.getText().toString().isEmpty()){

                updatetofirebase();
//                mref.child("UserProfile").child(key).child("FullName").setValue(name);
//                mref.child("UserProfile").child(key).child("Email").setValue(mail);
//                mref.child("UserProfile").child(key).child("Mobile").setValue(mobile);

               // mref.child("Registered Users").setValue(key);
                Toast.makeText(UserRegistration.this, "Account Has Been Created Successfully!", Toast.LENGTH_SHORT).show();


                           }
            else{
                Toast.makeText(UserRegistration.this, "Complete All Above Fields First to Register", Toast.LENGTH_SHORT).show();

            }}

        });
    }

    private void updatetofirebase() {
        final ProgressDialog pd= new ProgressDialog(UserRegistration.this);
        pd.setTitle("File Uploader");
        pd.show();
            DatabaseReference myref=mref.child("UserProfile");
        final StorageReference uploader=storageReference.child("profileimages/"+"img"+System.currentTimeMillis());
        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                pd.dismiss();
                                final Map<String,Object> map= new HashMap<>();

                                map.put("uimage",uri.toString());
                                map.put("FullName",Name);
                                map.put("Email",Email);
                                map.put("Mobile",Mobile);
                                myref.child(Mobile).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            mref.child("UserProfile").child(Mobile).updateChildren(map);
                                            Toast.makeText(UserRegistration.this, "Data Updated!", Toast.LENGTH_SHORT).show();

                                        }
                                        else{
                                            mref.child("UserProfile").child(Mobile).setValue(map);
                                            Toast.makeText(UserRegistration.this, "Account Registered Successfully!", Toast.LENGTH_SHORT).show();
                                        }
                                        startActivity(new Intent(UserRegistration.this,Home.class));
                                        finish();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        });
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploaded: "+(int)percent+"%");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==101 && resultCode==RESULT_OK)
        {
            filepath=data.getData();
            try{
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                uimage.setImageBitmap(bitmap);
            }
            catch (Exception e){
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
//        Userid=user.getUid();
//        mref.child(Userid).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists())
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
    }


}