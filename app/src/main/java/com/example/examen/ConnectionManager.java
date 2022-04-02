package com.example.examen;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager {
    public static void addData(String url, ProgressDialog progressDialog, Context page, Class nextPage, Map<String, String> params) {
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Toast.makeText(page, "Registrado correctamente", Toast.LENGTH_SHORT).show();
                    ((Activity) page).startActivity(new Intent(((Activity) page), nextPage));
                } else {
                    Toast.makeText(page, response, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(page, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(page);
        requestQueue.add(request);
    }

    public static void closeOrders(String user, Context page) {
        String url = "https://proyectoconclusion.000webhostapp.com/closeorders.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    Intent intent = new Intent(page, Finalize.class);
                    intent.putExtra("user", user);
                    ((Activity) page).startActivity(intent);
                } else {
                    Toast.makeText(page, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(page, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(page);
        requestQueue.add(request);
    }

    public static void updateAmountAndOrders(String user, Context page) {
        String url = "https://proyectoconclusion.000webhostapp.com/updateamounts.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equalsIgnoreCase("success")) {
                    closeOrders(user, page);
                } else {
                    Toast.makeText(page, response, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error", error.toString());
                Toast.makeText(page, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue = Volley.newRequestQueue(page);
        requestQueue.add(request);
    }

    public static void validateUserPassword(String email, String password, String dbUrl, ProgressDialog progressDialog, Context page, Class nextPage) {
        StringRequest request = new StringRequest(Request.Method.POST, dbUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                if (response.equalsIgnoreCase("success")) {

                    Intent intent = new Intent(page, nextPage);
                    intent.putExtra("user", email);
                    ((Activity) page).startActivity(intent);

                    // ((Activity) page).startActivity(new Intent(((Activity) page), nextPage));
                } else
                Toast.makeText(page, response, Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(page, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(page);
        requestQueue.add(request);
    }
}
