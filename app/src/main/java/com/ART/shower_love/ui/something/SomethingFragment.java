package com.ART.shower_love.ui.something;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.ART.shower_love.R;

public class SomethingFragment extends Fragment {


    private SomethingViewModel somethingViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        somethingViewModel =
                new ViewModelProvider(this).get(SomethingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_something, container, false);
       TextView insta= root.findViewById(R.id.insta_click);
       TextView facebook = root.findViewById(R.id.facebook_click);
       TextView twitter = root.findViewById(R.id.twitter_click);
       TextView mail = root.findViewById(R.id.mail_click);
       TextView website = root.findViewById(R.id.websit_click);
       TextView playstore = root.findViewById(R.id.playstore_click);

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.EMPTY.parse("https://www.instagram.com/shower___love/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setPackage("com.instagram.android");
                try {
                    startActivity(intent);
                }
                catch (ActivityNotFoundException e ){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.instagram.com/shower___love/")));
                }

            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.EMPTY.parse("https://www.facebook.com/Shower-Love-105623897979722/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://twitter.com/ShowerLove5");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("mailto:showlovetohumanity@gmail.com");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        website.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://shower-love.000webhostapp.com/");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });
        playstore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://wwww.google.com");
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });

        return root;
    }
}