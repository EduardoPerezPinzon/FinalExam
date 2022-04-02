package com.example.examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Product extends AppCompatActivity {
    private Button buttonAddToCart;
    private Button buttonEndOrder;
    private TextView nameField;
    private TextView priceField;
    private TextView amountField;
    private TextView btnContinue;
    private AdapterProduct product;
    private String m_Text = "";

    String url = "https://proyectoconclusion.000webhostapp.com/product.php";
    String urlOrder = "https://proyectoconclusion.000webhostapp.com/orders.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        String productId = getIntent().getStringExtra("productId");
        String user = getIntent().getStringExtra("user");

        buttonAddToCart = (Button) findViewById(R.id.buttonAddToCart);
        buttonEndOrder = (Button) findViewById(R.id.buttonEndOrder);
        nameField = (TextView) findViewById(R.id.name);
        priceField = (TextView) findViewById(R.id.price);
        amountField = (TextView) findViewById(R.id.amount);
        btnContinue = (TextView) findViewById(R.id.btnContinue);

        getProduct(productId);

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Product.this);
                builder.setTitle(product.getName());
                builder.setMessage("Seleccione la cantidad que desea comprar");
                final EditText input = new EditText(Product.this);
                input.setInputType(InputType.TYPE_CLASS_NUMBER);
                builder.setView(input);

                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m_Text = input.getText().toString();
                        saveOrder(m_Text, user);
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product.this, Categories.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        buttonEndOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Product.this, Shopping.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void saveOrder(String amount, String user) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        StringRequest request = new StringRequest(Request.Method.POST, urlOrder, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(Product.this, "Producto añadido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Product.this, response, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("productId", product.getId());
                params.put("user", user);
                params.put("amount", amount);
                params.put("status", "true");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void getProduct(String productId) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    if (success.equals("1")) {
                        JSONObject object = jsonArray.getJSONObject(0);
                        String id = object.getString("id");
                        String name = object.getString("name");
                        String price = object.getString("price");
                        String amount = object.getString("amount");

                        product = new AdapterProduct(id, name, price, amount);

                        nameField.setText(name);
                        priceField.setText(price);
                        amountField.setText(amount);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Product.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", productId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}