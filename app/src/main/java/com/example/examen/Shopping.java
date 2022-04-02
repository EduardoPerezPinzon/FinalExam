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

public class Shopping extends AppCompatActivity {
    private ListView listView;
    private Button btnAccept;
    private TextView btnContinue;
    AdapterShoppings orders;

    public static ArrayList<AdapterShopping> shoppingList = new ArrayList<>();

    String url = "https://proyectoconclusion.000webhostapp.com/shopping.php";
    AdapterShopping order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        listView = (ListView) findViewById(R.id.listView);
        btnAccept = (Button) findViewById(R.id.btnAccept);
        btnContinue = (TextView) findViewById(R.id.btnContinue);

        orders = new AdapterShoppings(this, shoppingList);
        listView.setAdapter(orders);

        String user = getIntent().getStringExtra("user");
        fillOrders(user);

        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectionManager.updateAmountAndOrders(user, Shopping.this);
            }
        });

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Shopping.this, Categories.class);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void fillOrders(String user) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                shoppingList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            String name = object.getString("name");
                            String amount = object.getString("amount");
                            String price = object.getString("price");
                            String user = object.getString("user");

                            order = new AdapterShopping(name, user, amount, price);
                            shoppingList.add(order);
                            orders.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Shopping.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("user", user);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}