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

public class DisplayBloodDonarInfoActivity extends AppCompatActivity {

    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<BloodDonoruploadinfo> list = new ArrayList<>();
    BloodRecyclerViewAdapter.RecyclerViewListener listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_blodd_donar_info);

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.bloddrecyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayBloodDonarInfoActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(DisplayBloodDonarInfoActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Data From Firebase.");

        // Showing progress dialog.
        progressDialog.show();

        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference().child("BloodDonation");

        // Adding Add Value Event Listener to databaseReference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    BloodDonoruploadinfo  bloodDonoruploadinfo = postSnapshot.getValue(BloodDonoruploadinfo.class);

                    list.add(bloodDonoruploadinfo);
                }
                setOnClickLIstner();

                adapter = new BloodRecyclerViewAdapter(getApplicationContext(), list ,listener);

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            private void setOnClickLIstner() {
                listener = new BloodRecyclerViewAdapter.RecyclerViewListener() {
                    @Override
                    public void onClick(View v, int position) {
                        Intent intent = new Intent(getApplicationContext(), BloodDonarCompleteInfo.class);
                        intent.putExtra("name",list.get(position).getName());
                        intent.putExtra("address",list.get(position).getAddress());
                        intent.putExtra("email",list.get(position).getEmail());
                        intent.putExtra("bloodgroup",list.get(position).getBloodGroup());
                        intent.putExtra("gender",list.get(position).getGender());
                        intent.putExtra("phone",list.get(position).getPhone());
                        intent.putExtra("allergic",list.get(position).getAllergi());
                        intent.putExtra("cardiac",list.get(position).getCardiac());
                        intent.putExtra("disease",list.get(position).getDisease());
                        intent.putExtra("medication",list.get(position).getMedication());
                        intent.putExtra("bleedingdisorder",list.get(position).getBleedingdisorder());
                        intent.putExtra("postalcode",list.get(position).getPostalCode());
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