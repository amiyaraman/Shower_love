package com.ART.shower_love;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    private static final int RC_SIGN_IN = 10214;
    String UserId;
    private static final String TAG = "Registration";
    private FirebaseAuth fAuth;
    private FirebaseFirestore fstore;
    GoogleSignInClient mGoogleSignInClient;

    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        //WITH GOOGLE SIGN IN
        final Button login_with_google = findViewById(R.id.signupwithgoogle);
        login_with_google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();

            }
        });
        //END
        final EditText eName = findViewById(R.id.eName);
        final EditText eEmail = findViewById(R.id.eEmailAddress);
        final EditText ePassword = findViewById(R.id.ePassword);
        final EditText cePassword = findViewById(R.id.econfirmpassword);
        final EditText ePhone = findViewById(R.id.ePhone);
        final ProgressBar Rprogressbar = findViewById(R.id.Register_loading);
        Button SignupButton = findViewById(R.id.SignupButton);
        TextView old_member = findViewById(R.id.omember);
        fAuth = FirebaseAuth.getInstance();
         fstore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            if (fAuth.getCurrentUser().isEmailVerified()) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(Registration.this, "Welcome", Toast.LENGTH_SHORT).show();
                finish();
            } else if(!(fAuth.getCurrentUser().isEmailVerified())) {
                Toast.makeText(Registration.this, "verify your email", Toast.LENGTH_SHORT).show();

            }


        }


        SignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ename = eName.getText().toString();
                final String eemail = eEmail.getText().toString();
                final String epassword = ePassword.getText().toString().trim();

                if (eemail.isEmpty()) {
                    eEmail.setError("Email is Required");
                    return;
                }
                if (epassword.isEmpty()) {
                    ePassword.setError("Email is Required");
                    return;
                }
                //checking if the format of email is correct
                if (!eemail.contains("@") || Char_Count(eemail, '@') >= 2) {
                    Toast.makeText(Registration.this, "Please enter proper format of email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (epassword.length() <= 6) {
                    ePassword.setError("Password must be greater then 8 characters");
                    return;
                }
                String cepassword = cePassword.getText().toString();
                if (!epassword.equals(cepassword)) {
                    cePassword.setError("Password are not same as above password");
                    return;
                }
                final String ephone = ePhone.getText().toString();
                Rprogressbar.setVisibility(View.VISIBLE);
                fAuth.createUserWithEmailAndPassword(eemail, epassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {

                                        Toast.makeText(getApplicationContext(), "plz verify your email", Toast.LENGTH_LONG).show();
                                        UserId = fAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = fstore.collection("UserDetail").document(UserId);
                                        Map<String, Object> user = new HashMap<>();
                                        user.put("UserName", ename);
                                        user.put("Email", eemail);
                                        user.put("Phone", ephone);
                                        user.put("password", epassword);
                                        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: user Profile is created for " + UserId);
                                            }

                                        });
                                        Rprogressbar.setVisibility(View.GONE);
                                        Toast.makeText(Registration.this, "Registration is successful ", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Error" + task.getException().toString(), Toast.LENGTH_LONG).show();
                                    }
                                }
                            });

                        } else {
                            //checking for the error and catching it if there is any
                            Toast.makeText(getApplicationContext(), "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            Rprogressbar.setVisibility(View.GONE);

                        }
                    }
                });

            }
        });
        old_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Registration.this, LoginActivity.class));
                finish();
            }
        });

        Create_Request();
    }

    //the function is to count if the email string contain more then one '@'.
    private int Char_Count(String Incount, char tocount) {
        char[] strArray = Incount.toCharArray();
        int count = 0;
        for (char c : strArray) {
            if (c == tocount) {
                count++;
            }
        }
        return count;
    }

    private void Create_Request() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            final String UserId= fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fstore.collection("UserDetail").document(UserId);
                            Map<String, Object> user = new HashMap<>();
                            String ename ,eemail,ephone= "",epassword="Google User";
                            GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                            assert googleSignInAccount != null;
                            ename= googleSignInAccount.getDisplayName();
                            eemail = googleSignInAccount.getEmail();

                            user.put("UserName", ename);
                            user.put("Email", eemail);
                            user.put("Phone", ephone);
                            user.put("password", epassword);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + UserId);
                                }

                            });

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            Toast.makeText(Registration.this, "Welcome", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "failed  to sign in", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }
}