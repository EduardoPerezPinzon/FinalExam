package com.example.examen;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Products extends AppCompatActivity {
    private ListView listView;
    private TextView txtTitle;
    private Button btnBack;
    AdapterProducts products;

    public static ArrayList<AdapterProduct> productList = new ArrayList<>();
    String url = "https://proyectoconclusion.000webhostapp.com/products.php";
    AdapterProduct product;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listView = (ListView) findViewById(R.id.listView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnBack = (Button) findViewById(R.id.btnBack);

        products = new AdapterProducts(this, productList);
        listView.setAdapter(products);

        String categoryId = getIntent().getStringExtra("categoryId");
        String user = getIntent().getStringExtra("user");
        fillProducts(categoryId);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String productId = productList.get(position).getId();
                Intent intent = new Intent(Products.this, Product.class);
                intent.putExtra("productId", productId);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Products.this, Categories.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void fillProducts(String categoryId) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                productList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String price = object.getString("price");
                            String amount = object.getString("amount");

                            product = new AdapterProduct(id, name, price, amount);
                            productList.add(product);
                            products.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Products.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("categoryId", categoryId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}