package com.cydbot.farmtoolbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ImageView ivPasswordIcon;
    private TextView tvgotoSignUpBtn;
    private boolean passwordShowding = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = (EditText) findViewById(R.id.et_emailLogin);
        etPassword = (EditText) findViewById(R.id.et_passwordLogin);
        ivPasswordIcon = findViewById(R.id.iv_passwordShow);
        tvgotoSignUpBtn = findViewById(R.id.btn_goToSignUp);
        ivPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verificar si la contraseña es visible o no
                if (passwordShowding) {
                    passwordShowding = false;
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivPasswordIcon.setImageResource(R.drawable.ic_password_hide);
                } else {
                    passwordShowding = true;
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                    ivPasswordIcon.setImageResource(R.drawable.ic_password_show);
                }

                //Mover el cursor al final del texto
                etPassword.setSelection(etPassword.length());
            }
        });

        tvgotoSignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}