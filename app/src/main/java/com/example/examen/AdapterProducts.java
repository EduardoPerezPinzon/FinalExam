package com.example.examen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class AdapterProducts extends ArrayAdapter<AdapterProduct> {
    Context context;
    List<AdapterProduct> productList;

    public AdapterProducts(@NonNull Context context, List<AdapterProduct> productList) {
        super(context, R.layout.product, productList);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product, null, true);
        TextView name = view.findViewById(R.id.name);
        TextView price = view.findViewById(R.id.price);
        TextView amount = view.findViewById(R.id.amount);
        name.setText(productList.get(position).getName());
        price.setText(productList.get(position).getPrice());
        amount.setText(productList.get(position).getAmount());
        return view;
    }
}
