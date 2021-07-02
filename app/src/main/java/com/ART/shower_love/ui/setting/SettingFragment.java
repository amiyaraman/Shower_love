package com.ART.shower_love.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ART.shower_love.LoginActivity;
import com.ART.shower_love.PhoneNumber;
import com.ART.shower_love.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class SettingFragment extends Fragment implements View.OnClickListener {
    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String user_id;
    private static final String TAG = "SettingFragment";
    String b;
    String c;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting,container,false);

       Button  loguot_button = root.findViewById(R.id.logout_fragmentbutton);
        final TextView pusername = root.findViewById(R.id.test_username);
        final TextView pemail = root.findViewById(R.id.test_useremail);
        final TextView pphone = root.findViewById(R.id.user_phone);

        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user_id =  Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        final DocumentReference documentReference = fstore.collection("UserDetail").document(user_id);
        documentReference.addSnapshotListener(requireActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null){
                    String a= value.getString("UserName");
                 b= value.getString("Email");

                pusername.setText(a);
                pemail.setText(b);
                c=value.getString("Phone");
                pphone.setText(c);}
            }

        });



        //This part is for showing user profile in the app drawer
        loguot_button.setOnClickListener(this);

       return root;
    }


    @Override
    public void onClick(View view) {
        LogOut();
    }
    private void LogOut(){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(getActivity(),"Log out successful",Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getActivity(),LoginActivity.class));
        requireActivity().finish();
    }
}
