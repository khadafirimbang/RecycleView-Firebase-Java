package com.example.recycleviewwithfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    Button btnSubmit, btnView;
    EditText name, email, age;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSubmit = findViewById(R.id.btnSubmit);
        btnView = findViewById(R.id.btnView);
        name = findViewById(R.id.etName);
        email = findViewById(R.id.etEmail);
        age = findViewById(R.id.etAge);
        databaseUsers = FirebaseDatabase.getInstance().getReference();

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(name.getText().toString())) {
                    name.setError("Name is required!");
                    return;
                }
                if(TextUtils.isEmpty(email.getText().toString())) {
                    email.setError("Email is required!");
                    return;
                }
                if(TextUtils.isEmpty(age.getText().toString())) {
                    age.setError("Age is required!");
                    return;
                }

                InsertData();

            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserList.class));
                finish();
            }
        });


    }

    private void InsertData() {
        String userName = name.getText().toString();
        String userEmail = email.getText().toString();
        String userAge = age.getText().toString();
        String id = databaseUsers.push().getKey();

        User user = new User(userName, userEmail, userAge);
        databaseUsers.child("users").child(id).setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful()) {
                            Toast.makeText(MainActivity.this, "User Added Successfully!", Toast.LENGTH_SHORT).show();
                            name.setText("");
                            email.setText("");
                            age.setText("");
                        }

                    }
                });
    }

}