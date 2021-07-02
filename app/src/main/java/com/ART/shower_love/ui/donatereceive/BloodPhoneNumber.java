package com.ART.shower_love.ui.donatereceive;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ART.shower_love.OTPverifyActivity;
import com.ART.shower_love.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class BloodPhoneNumber extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static boolean isphoneverified = false;
    String[] BLOOD_GROUP = {"A+","B+","O+","AB+","A-","B-","O-","AB-"};
    String[] gender = {"Male", "Female", "Other"};
    String bloodgroup, UserGender, PostalCode, useraddress;
    String complete_phone_number;


    private EditText mCountrycode, EnteredPostalCode, EnteredAdress;
    private EditText mPhoneNumber;

    private Button mGeneratbtn;
    private ProgressBar mLoginProgress;

    private FirebaseUser mCurrentUser;
    private FirebaseAuth mAuth;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks McallBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_blood);

        mCountrycode = findViewById(R.id.blood_country_code_et);
        mPhoneNumber = findViewById(R.id.Blood_Mobile_Number_et);
        mGeneratbtn = findViewById(R.id.blood_button_generate_otp);
        mLoginProgress = findViewById(R.id.blood_generate_progress_bar);
        EnteredPostalCode = findViewById(R.id.blood_donar_postalcode);
        EnteredAdress = findViewById(R.id.blood_donator_address);


        mAuth = FirebaseAuth.getInstance();
        mCurrentUser = mAuth.getCurrentUser();


        mGeneratbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String country_code = mCountrycode.getText().toString();
                String phone_number = mPhoneNumber.getText().toString();
                PostalCode = EnteredPostalCode.getText().toString();
                useraddress = EnteredAdress.getText().toString();

                complete_phone_number = "+" + country_code + phone_number;

                if (country_code.isEmpty() || phone_number.isEmpty() || PostalCode.isEmpty() || useraddress.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please Fill", Toast.LENGTH_LONG).show();
                } else {
                    mLoginProgress.setVisibility(View.VISIBLE);

                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            complete_phone_number,
                            60,
                            TimeUnit.SECONDS,
                            com.ART.shower_love.ui.donatereceive.BloodPhoneNumber.this,
                            McallBack
                    );
                }
            }
        });
        final Spinner gen = findViewById(R.id.spinner2);
        ArrayAdapter<String> genderadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gender);
        genderadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gen.setAdapter(genderadapter);
        gen.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UserGender = gender[position];


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Spinner spin = findViewById(R.id.spinnercondition);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, BLOOD_GROUP);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);
        McallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                sendUserhome();
            }


            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                Toast.makeText(getApplicationContext(), "verification Failed" + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                Intent otpIntent = new Intent(com.ART.shower_love.ui.donatereceive.BloodPhoneNumber.this, BloodOTPverifyActivity.class);
                otpIntent.putExtra("AuthCredentials", s);
                otpIntent.putExtra("Gender", UserGender);
                otpIntent.putExtra("Blood Group", bloodgroup);
                otpIntent.putExtra("postalcode", PostalCode);
                otpIntent.putExtra("address", useraddress);
                otpIntent.putExtra("phone", complete_phone_number);
                startActivity(otpIntent);

            }
        };


    }


    private void sendUserhome() {
        Intent homeIntent = new Intent(com.ART.shower_love.ui.donatereceive.BloodPhoneNumber.this, BloodDonatePage.class);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        homeIntent.putExtra("Blood Group", bloodgroup);
        homeIntent.putExtra("Gender", UserGender);
        homeIntent.putExtra("postalcode", PostalCode);
        homeIntent.putExtra("address", useraddress);
        homeIntent.putExtra("phone", complete_phone_number);

        startActivity(homeIntent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (mCurrentUser != null && isphoneverified) {
            sendUserhome();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long id) {
        bloodgroup = BLOOD_GROUP[i];

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO - Custom Code

    }
}

