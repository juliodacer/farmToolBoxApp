package com.cydbot.farmtoolbox;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    private boolean passwordShowding = false;
    private boolean confirmPasswordShowding = false;
    private EditText etName, etSurname, etEmail, etPhoneNumber, etPassword, etConfirmPassword;
    private ImageView ivPasswordIcon, ivConfirmPasswordIcon;
    private AppCompatButton registerBtn;
    private TextView goToSignInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etName = findViewById(R.id.et_nameRegister);
        etSurname = findViewById(R.id.et_surnameRegister);
        etEmail = findViewById(R.id.et_emailRegister);
        etPhoneNumber = findViewById(R.id.et_phoneNumberRegister);
        etPassword = findViewById(R.id.et_passwordRegister);
        etConfirmPassword = findViewById(R.id.et_confirmPasswordRegister);
        ivPasswordIcon = findViewById(R.id.iv_passwordShowRegister);
        ivConfirmPasswordIcon = findViewById(R.id.iv_confirmPasswordShowRegister);
        registerBtn = findViewById(R.id.btn_registerAccount);
        goToSignInBtn = findViewById(R.id.btn_goToSignIn);

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
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivPasswordIcon.setImageResource(R.drawable.ic_password_show);
                }

                //Mover el cursor al final del texto
                etPassword.setSelection(etPassword.length());
            }
        });

        ivConfirmPasswordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //verificar si la contraseña es visible o no
                if (confirmPasswordShowding) {
                    confirmPasswordShowding = false;
                    etConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivConfirmPasswordIcon.setImageResource(R.drawable.ic_password_show);
                } else {
                    confirmPasswordShowding = true;
                    etConfirmPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivConfirmPasswordIcon.setImageResource(R.drawable.ic_password_hide);
                }

                //Mover el cursor al final del texto
                etConfirmPassword.setSelection(etConfirmPassword.length());
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent();
            }
        });

        goToSignInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}