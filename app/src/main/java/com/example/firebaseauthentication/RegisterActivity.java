package com.example.firebaseauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {


    private Button registerBtn;
    private EditText userName, userEmail, userPassword, userConfirmPassword;
    private DatabaseReference rootRef;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);


        mAuth = FirebaseAuth.getInstance();

        rootRef = FirebaseDatabase.getInstance().getReference().child("Users");
        registerBtn = findViewById(R.id.registerBTN);
        loadingBar = new ProgressDialog(this);
        userName = findViewById(R.id.userNameRegisterET);
        userEmail = findViewById(R.id.userEmailRegisterET);
        userPassword = findViewById(R.id.userPasswordRegisterET);
        userConfirmPassword = findViewById(R.id.userConfirmPasswordRegisterET);


        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userName.getText().toString().trim();
                final String email = userEmail.getText().toString().trim();
                final String password = userPassword.getText().toString().trim();
                final String confirmPassword = userConfirmPassword.getText().toString().trim();


                if (TextUtils.isEmpty(name)) {
                    userName.setError("Enter Name");
                    return;
                } else if (TextUtils.isEmpty(email)) {
                    userEmail.setError("Enter Email");
                    return;
                } else if (TextUtils.isEmpty(password)) {
                    userPassword.setError("Enter Password");
                    return;

                } else if (TextUtils.isEmpty(confirmPassword)) {
                    userPassword.setError("Enter Confirm Password");
                    return;
                } else if (password.length() <= 6) {
                    userPassword.setError("Password length must 6");
                    return;
                } else if (!password.equals(confirmPassword)) {
                    userPassword.setError("Does Not Match");
                    userConfirmPassword.setError("Does Not Match");
                    return;
                } else {

                    loadingBar.setTitle("User Registration:");
                    loadingBar.setMessage("Your account is creating,please wait...");
                    loadingBar.setCanceledOnTouchOutside(false);
                    loadingBar.show();
                    allowToRegister(name, email, password);

                }
            }
        });

    }

    private void allowToRegister(final String name, final String email, final String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String currentUser = mAuth.getCurrentUser().getUid();

                    rootRef.child(currentUser).setValue(new Users(name, email, password,currentUser)).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                            if (task.isSuccessful())
                            {
                                loadingBar.dismiss();

                                Intent intent =new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }
                            else {
                                loadingBar.dismiss();
                                Toast.makeText(RegisterActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else
                {
                    loadingBar.dismiss();
                    Toast.makeText(RegisterActivity.this, "Error: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });
    }


}

