package com.example.examen;

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
import java.util.regex.Pattern;


public class Register extends AppCompatActivity {
    private Button btn_register;
    private EditText user;
    private EditText password;
    private EditText mail;

    private String dbUrl = "https://proyectoconclusion.000webhostapp.com/users.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btn_register = (Button) findViewById(R.id.btn_register);
        user = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);
        mail = (EditText) findViewById(R.id.mail);

        ProgressDialog progressDialog = new ProgressDialog(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userData = user.getText().toString();
                String emailData = mail.getText().toString();
                String passwordData = password.getText().toString();

                Boolean success = true;
                if (userData.isEmpty()) {
                    user.setError("El usuario no puede estar vacío");
                    success = false;
                }
                if (emailData.isEmpty()) {
                    mail.setError("El e-mail no puede estar vacío");
                    success = false;
                } else {
                    String regexPattern = "^(.+)@(\\S+)$";
                    if (!Pattern.compile(regexPattern).matcher(emailData).matches()) {
                        mail.setError("El formato de mail ingresado es incorrecto");
                        success = false;
                    }
                }
                if (passwordData.isEmpty()) {
                    password.setError("La contraseña no puede estar vacía");
                    success = false;
                }

                if (!success) return;
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", userData);
                params.put("password", passwordData);
                params.put("email", emailData);

                ConnectionManager.addData(dbUrl, progressDialog, Register.this, Login.class, params);
            }
        });
    }
}