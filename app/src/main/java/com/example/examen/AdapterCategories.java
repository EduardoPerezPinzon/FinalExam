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
import java.util.List;

public class AdapterCategories extends ArrayAdapter<AdapterCategory> {
    Context context;
    List<AdapterCategory> categoryList;

    public AdapterCategories(@NonNull Context context, List<AdapterCategory> categoryList) {
        super(context, R.layout.category, categoryList);
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category, null, true);
        TextView name = view.findViewById(R.id.name);
        ImageView imageView = (ImageView) view.findViewById(R.id.logo);
        name.setText(categoryList.get(position).getName());

        String imageUrl = categoryList.get(position).getImageUrl();
        new DownloadImageTask(imageView).execute(imageUrl);
        return view;
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
