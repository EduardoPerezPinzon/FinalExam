package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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

public class Categories extends AppCompatActivity {
    private ListView listView;
    AdapterCategories categories;

    public static ArrayList<AdapterCategory> categoryList = new ArrayList<>();
    String url = "https://proyectoconclusion.000webhostapp.com/categories.php";
    AdapterCategory category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        listView = (ListView) findViewById(R.id.listView);
        categories = new AdapterCategories(this, categoryList);
        listView.setAdapter(categories);

        fillCategories();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String categoryId = categoryList.get(position).getId();
                String user = getIntent().getStringExtra("user");
                Intent intent = new Intent(Categories.this, Products.class);
                intent.putExtra("categoryId", categoryId);
                intent.putExtra("user", user);
                startActivity(intent);
            }
        });
    }

    private void fillCategories() {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                categoryList.clear();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    if (success.equals("1")) {
                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject object = jsonArray.getJSONObject(i);
                            String id = object.getString("id");
                            String name = object.getString("name");
                            String description = object.getString("description");
                            String imageUrl = object.getString("imageUrl");

                            category = new AdapterCategory(id, name, description, imageUrl);
                            categoryList.add(category);
                            categories.notifyDataSetChanged();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Categories.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}