package com.example.examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {
    private Button buttonLogin;
    private EditText emailTxt;
    private EditText passwordTxt;

    private String dbUrl = "https://proyectoconclusion.000webhostapp.com/login.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        emailTxt = (EditText) findViewById(R.id.email);
        passwordTxt = (EditText) findViewById(R.id.password);

        final ProgressDialog progressDialog = new ProgressDialog(this);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get data from fields
                String email = emailTxt.getText().toString();
                String password = passwordTxt.getText().toString();

                progressDialog.setMessage("Cargando...");
                progressDialog.show();

                ConnectionManager.validateUserPassword(email, password, dbUrl, progressDialog, Login.this, Categories.class);
            }
        });
    }
}