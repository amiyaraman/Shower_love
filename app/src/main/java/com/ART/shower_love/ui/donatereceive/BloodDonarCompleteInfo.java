package com.ART.shower_love.ui.donatereceive;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.ART.shower_love.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BloodDonarCompleteInfo extends AppCompatActivity {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("BloodDonation");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_donar_complete_info);
        TextView name = findViewById(R.id.blood_donar_name_c);
        TextView Email = findViewById(R.id.BDemailc);
        TextView Gender = findViewById(R.id.BDGenderc);
        TextView Phone = findViewById(R.id.BDphonec);
        TextView Postal_code = findViewById(R.id.BDpostalcodec);
        TextView Address = findViewById(R.id.Blood_donar_address_c);
        TextView Blood_Group = findViewById(R.id.blodddonarbloodgroupc);
        TextView Allergy = findViewById(R.id.donarallergyc);
        TextView Bleeding_disorder = findViewById(R.id.donarbleedingdisorder);
        TextView Cardiac = findViewById(R.id.donarcardiacc);
        TextView Disease = findViewById(R.id.donardeseasec);
        TextView medication = findViewById(R.id.donarmedicationc);
        TextView key2 = findViewById(R.id.textView40);
        String key = reference.getKey();
        key2.setText(key);

        Bundle extra = getIntent().getExtras();
        name.setText(extra.getString("name"));
        Email.setText(extra.getString("email"));
        Address.setText(extra.getString("address"));
        Blood_Group.setText(extra.getString("bloodgroup"));
        Gender.setText(extra.getString("gender"));
        Phone.setText(extra.getString("phone"));
        Allergy.setText(extra.getString("allergic"));
        Cardiac.setText(extra.getString("cardiac"));
        Disease.setText(extra.getString("disease"));
        medication.setText(extra.getString("medication"));
        Bleeding_disorder.setText(extra.getString("bleedingdisorder"));
        Postal_code.setText(extra.getString("postalcode"));

    }
}