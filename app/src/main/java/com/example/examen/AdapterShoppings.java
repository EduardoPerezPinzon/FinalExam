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

public class AdapterShoppings extends ArrayAdapter<AdapterShopping> {
    Context context;
    List<AdapterShopping> shoppingList;

    public AdapterShoppings(@NonNull Context context, List<AdapterShopping> shoppingList) {
        super(context, R.layout.shopping, shoppingList);
        this.context = context;
        this.shoppingList = shoppingList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping, null, true);

        TextView name = view.findViewById(R.id.name);
        // TextView user = view.findViewById(R.id.user);
        TextView amount = view.findViewById(R.id.amount);
        TextView price = view.findViewById(R.id.price);

        name.setText(shoppingList.get(position).getName());
        // user.setText(shoppingList.get(position).getUser());
        amount.setText(shoppingList.get(position).getAmount());
        price.setText(shoppingList.get(position).getPrice());
        return view;
    }
}
