package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Finalize extends AppCompatActivity {
    private TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize);

        txtResult = (TextView) findViewById(R.id.txtResult);
        String user = getIntent().getStringExtra("user");

        txtResult.setText("La orden ha sido generada en forma correcta, un mail ha sido enviado a " + user + " con la información detallada de la misma. Muchas gracias por confiar en nosotros.");
    }
}