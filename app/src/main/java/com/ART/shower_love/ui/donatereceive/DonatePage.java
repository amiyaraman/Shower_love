package com.ART.shower_love.ui.donatereceive;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.Objects;

public class DonatePage extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Uri FilePathUri;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;
    String user_id;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    int Image_Request_Code = 7;
    ProgressDialog progressDialog;
    ImageView iimageg;
    EditText idiscripathin;

    String [] condition = {"Excellent","Good","Bad","Worst"};
    String [] recycle = {"Yes","No",};
    String [] use = {"Can use directly","Need a recycle"};
    String ItemCondition , ItemRecycle, ItemUse;
    String itemChatogory , UserGender , PostalCode , useraddress;
    String complete_phone_number;
    String name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.donate_page);
        idiscripathin = findViewById(R.id.item_discripation);
        iimageg = findViewById(R.id.item_image);
        Button submit  = findViewById(R.id.submit_info);
        Button brwsimage  = findViewById(R.id.upload_image);
        Spinner cond = findViewById(R.id.spinnercondition);
        Spinner rcyc = findViewById(R.id.spinnerrecycle);
        Spinner usecondition = findViewById(R.id.spinneruse);
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        user_id =  Objects.requireNonNull(fAuth.getCurrentUser()).getUid();
        final DocumentReference documentReference = fstore.collection("UserDetail").document(user_id);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value!=null){
                     name= value.getString("UserName");
                    email= value.getString("Email");
                }
            }
        });
        progressDialog = new ProgressDialog(DonatePage.this);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        databaseReference = FirebaseDatabase.getInstance().getReference("ItemsDonation");
        UserGender= getIntent().getStringExtra("Gender");
        itemChatogory = getIntent().getStringExtra("chatogary");
        PostalCode = getIntent().getStringExtra("postalcode");
        useraddress = getIntent().getStringExtra("address");
        complete_phone_number = getIntent().getStringExtra("phone");
        brwsimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Image_Request_Code);

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                UploadImage();

            }
        });
        ArrayAdapter<String> ConditionAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,condition);
        ArrayAdapter<String> RecycleAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,recycle);
        ArrayAdapter<String> UseAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,use);
        ConditionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        RecycleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        UseAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cond.setAdapter(ConditionAdapter);
        rcyc.setAdapter(RecycleAdapter);
        usecondition.setAdapter(UseAdapter);
        cond.setOnItemSelectedListener(this);
        rcyc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ItemRecycle = recycle[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        usecondition.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ItemUse = use[position];

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        ItemCondition = condition[position];


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                iimageg.setImageBitmap(bitmap);
            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
    public void UploadImage() {

        if (FilePathUri != null) {

            progressDialog.setTitle("Image is Uploading...");
            progressDialog.show();
            StorageReference storageReference2 = storageReference.child(System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));
            storageReference2.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String TempImageName = idiscripathin.getText().toString().trim();
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                            @SuppressWarnings("VisibleForTests")
                            uploadinfo imageUploadInfo = new uploadinfo(TempImageName, taskSnapshot.getUploadSessionUri().toString(),ItemCondition,ItemRecycle,ItemUse,itemChatogory,UserGender,PostalCode,useraddress,complete_phone_number,name,email);
                            String ImageUploadId = databaseReference.push().getKey();
                            databaseReference.child(ImageUploadId).setValue(imageUploadInfo);
                        }
                    });
        }
        else {

            Toast.makeText(DonatePage.this, "Please Select Image or Add Image Name", Toast.LENGTH_LONG).show();

        }
    }
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }


}
