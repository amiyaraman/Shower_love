package com.ART.shower_love.ui.donatereceive;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.ART.shower_love.PhoneNumber;
import com.ART.shower_love.R;

public class DonateReceiveFragment extends Fragment  implements  View.OnClickListener {

    private DonateReceiveViewModel donateReceiveViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        donateReceiveViewModel =
                new ViewModelProvider(this).get(DonateReceiveViewModel.class);
        View root = inflater.inflate(R.layout.fragment_donate_receive, container, false);
        Button donate_itemBTN = root.findViewById(R.id.donate_item);
        Button donate_bloodBTN = root.findViewById(R.id.donate_blood);
        Button receive_page  =root.findViewById(R.id.receive_blood);
        Button receive_item = root.findViewById(R.id.receive_item);
        receive_page.setOnClickListener(receive_listener);
        receive_item.setOnClickListener(receive_item_listener);
        donate_bloodBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( getContext(),BloodPhoneNumber.class));

            }
        });
        donate_itemBTN.setOnClickListener(this);
        donateReceiveViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

            }
        });

        return root;
    }
    View.OnClickListener receive_listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivity(new Intent( getContext(), DisplayBloodDonarInfoActivity.class));
        }
    };
    View.OnClickListener receive_item_listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent( getContext(), DisplayImagesActivity.class));

        }
    };


    @Override
    public void onClick(View view) {
        startActivity(new Intent(getActivity(), PhoneNumber.class));
    }
}