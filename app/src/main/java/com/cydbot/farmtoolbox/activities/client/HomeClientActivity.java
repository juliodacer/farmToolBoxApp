package com.cydbot.farmtoolbox.activities.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cydbot.farmtoolbox.MainActivity;
import com.cydbot.farmtoolbox.R;
import com.google.firebase.auth.FirebaseAuth;

public class HomeClientActivity extends AppCompatActivity {

    private Button btnSignOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_client);

        btnSignOut = findViewById(R.id.btn_signOut);
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOutGoogle();
            }
        });
    }

    public void signOutGoogle(){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(HomeClientActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}