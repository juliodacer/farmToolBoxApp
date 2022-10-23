package com.cydbot.farmtoolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnIAmFarmer;
    Button btnIAmSupplier;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        final SharedPreferences.Editor editor = preferences.edit();

        btnIAmFarmer = findViewById(R.id.IAmFarmerBtn);
        btnIAmSupplier = findViewById(R.id.IAmSupplierBtn);
        btnIAmFarmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user", "farmer");
                editor.apply();
                goToLogin();
            }
        });

        btnIAmSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString("user", "supplier");
                editor.apply();
                goToLogin();
            }
        });

    }

    private void goToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}