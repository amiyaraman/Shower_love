package com.ART.shower_love.ui.donatereceive;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.ART.shower_love.R;
import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ItemDonarCompleteInfo extends AppCompatActivity {
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Create a storage reference from our app

        //StorageReference storageRef = storage.getReference();

// Create a child reference
// imagesRef now points to "images"

      //  StorageReference imagesRef = storageRef.child("images");

// Child references can also take paths
// spaceRef now points to "images/space.jpg
// imagesRef still points to "images"

        //StorageReference spaceRef = storageRef.child("images/"+homeClass.getHomeId()+".jpg");
        setContentView(R.layout.activity_item_donar_complete_info);
        TextView name = findViewById(R.id.item_donar_name_c);
        TextView Email = findViewById(R.id.IDemailc);
        TextView Gender = findViewById(R.id.IDGenderc);
        TextView Phone = findViewById(R.id.IDphonec);
        TextView Postal_code = findViewById(R.id.IDpostalcodec);
        TextView Address = findViewById(R.id.item_donar_address_c);
        TextView Item_condition = findViewById(R.id.itemcondition);
        TextView Recycle_condition = findViewById(R.id.itemrecycle);
        TextView Item_use = findViewById(R.id.itemuse);
        final ImageView imageView = findViewById(R.id.item_image_c);
        String image_url = getIntent().getExtras().getString("ImageUrl");

        storageReference = FirebaseStorage.getInstance().getReference("Images/"+image_url);
        final long ONE_MEGABYTE = 1024 * 1024;
        storageReference.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Bitmap bm = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        DisplayMetrics dm = new DisplayMetrics();
                        getWindowManager().getDefaultDisplay().getMetrics(dm);

                        imageView.setMinimumHeight(dm.heightPixels);
                        imageView.setMinimumWidth(dm.widthPixels);
                        imageView.setImageBitmap(bm);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });




        Bundle extra = getIntent().getExtras();
        name.setText(extra.getString("name"));
        Email.setText(extra.getString("email"));
        Address.setText(extra.getString("address"));
        Item_condition.setText(extra.getString("Condition"));
        Gender.setText(extra.getString("gender"));
        Phone.setText(extra.getString("phone"));
        Recycle_condition.setText(extra.getString("Recycle"));
        Item_use.setText(extra.getString("Use"));
        Postal_code.setText(extra.getString("postalcode"));

    }
}