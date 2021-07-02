package com.ART.shower_love.ui.donatereceive;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ART.shower_love.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DisplayImagesActivity extends AppCompatActivity {

    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<uploadinfo> list = new ArrayList<>();
    RecyclerViewAdapter.RecyclerViewListener listener2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_images);

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayImagesActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(DisplayImagesActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Information From Firebase.");

        // Showing progress dialog.
        progressDialog.show();

        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference().child("ItemsDonation");

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    uploadinfo imageUploadInfo = postSnapshot.getValue(uploadinfo.class);

                    list.add(imageUploadInfo);
                }
                setOnClickLIstner();

                adapter = new RecyclerViewAdapter(getApplicationContext(), list , listener2);

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }
            private void setOnClickLIstner() {
                listener2 = new RecyclerViewAdapter.RecyclerViewListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), ItemDonarCompleteInfo.class);
                        intent.putExtra("name",list.get(position).getName());
                        intent.putExtra("address",list.get(position).getAddress());
                        intent.putExtra("email",list.get(position).getEmail());
                        intent.putExtra("category",list.get(position).getCategory());
                        intent.putExtra("gender",list.get(position).getGender());
                        intent.putExtra("phone",list.get(position).getPhone());
                        intent.putExtra("Condition",list.get(position).getCondition());
                        intent.putExtra("Imagename",list.get(position).getImageName());
                        intent.putExtra("Recycle",list.get(position).getRecycle());
                        intent.putExtra("Use",list.get(position).getUse());
                        intent.putExtra("postalcode",list.get(position).getPostalCode());
                        intent.putExtra("ImageUrl",list.get(position).getImageURL());

                        startActivity(intent);


                    }
                };
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });

    }
}