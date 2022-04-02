package com.example.examen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class AdapterUserHistories extends ArrayAdapter<AdapterUserHistory> {
    Context context;
    List<AdapterUserHistory> userHistoryList;

    public AdapterUserHistories(@NonNull Context context, List<AdapterUserHistory> userHistoryList) {
        super(context, R.layout.historyuser, userHistoryList);
        this.context = context;
        this.userHistoryList = userHistoryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historyuser, null, true);
        TextView txtDate = view.findViewById(R.id.date);

        DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        String formattedDate = formatter.format(userHistoryList.get(position).getDate());

        txtDate.setText(Integer.toString(position + 1) + " -    Fecha: " + formattedDate);
        return view;
    }
}
