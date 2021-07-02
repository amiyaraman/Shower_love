package com.ART.shower_love.ui.donatereceive;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ART.shower_love.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class BloodDonatePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String user_id;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;


    String[] recycle = {"Yes", "No",};

    String Donardisease, allergie, cardiac;
    String Blood_Group, UserGender, PostalCode, useraddress;
    String complete_phone_number;
    String name, email;
    String medication;
    String bleedingdisorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.blood_donar_medical_information);
        Button submit = findViewById(R.id.blood_donar_disease_info_btn);
        Spinner Bleeding_disorder = findViewById(R.id.bleeding_disorder);
        final Spinner Medication = findViewById(R.id.medication);
        Spinner Cardiac = findViewById(R.id.cardiac);
        Spinner Disease = findViewById(R.id.disease);
        Spinner Allergie = findViewById(R.id.allergie);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user_id = Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        final DocumentReference documentReference = fstore.collection("UserDetail").document(user_id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null) {
                    name = value.getString("UserName");
                    email = value.getString("Email");
                }
            }
        });
        progressDialog = new ProgressDialog(com.ART.shower_love.ui.donatereceive.BloodDonatePage.this);
        databaseReference = FirebaseDatabase.getInstance().getReference("BloodDonation");
        UserGender = getIntent().getStringExtra("Gender");
        Blood_Group = getIntent().getStringExtra("Blood Group");
        PostalCode = getIntent().getStringExtra("postalcode");
        useraddress = getIntent().getStringExtra("address");
        complete_phone_number = getIntent().getStringExtra("phone");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });

        ArrayAdapter<String> RecycleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, recycle);


        RecycleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Cardiac.setAdapter(RecycleAdapter);
        Disease.setAdapter(RecycleAdapter);
        Allergie.setAdapter(RecycleAdapter);
        Bleeding_disorder.setAdapter(RecycleAdapter);
        Medication.setAdapter(RecycleAdapter);
        Cardiac.setOnItemSelectedListener(this);
        Bleeding_disorder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bleedingdisorder = recycle[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Medication.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                medication = recycle[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Disease.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Donardisease = recycle[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Allergie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                allergie = recycle[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        cardiac = recycle[position];


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void UploadImage() {


        BloodDonoruploadinfo imageUploadInfo = new BloodDonoruploadinfo(medication, bleedingdisorder, Donardisease, allergie, Blood_Group, cardiac, UserGender, PostalCode, useraddress, complete_phone_number, name, email);
        String ImageUploadId = databaseReference.push().getKey();
        databaseReference.child(ImageUploadId).setValue(imageUploadInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(com.ART.shower_love.ui.donatereceive.BloodDonatePage.this, "Information Updated ", Toast.LENGTH_LONG).show();
            }
        });

    }


}

