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

public class AdapterOrders extends ArrayAdapter<AdapterOrder> {
    Context context;
    List<AdapterOrder> orderList;

    public AdapterOrders(@NonNull Context context, List<AdapterOrder> orderList) {
        super(context, R.layout.category, orderList);
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order, null, true);
        TextView id = view.findViewById(R.id.id);
        TextView productId = view.findViewById(R.id.productId);
        TextView user = view.findViewById(R.id.user);
        TextView status = view.findViewById(R.id.status);
        TextView amount = view.findViewById(R.id.amount);

        id.setText(orderList.get(position).getId());
        productId.setText(orderList.get(position).getProductId());
        user.setText(orderList.get(position).getUser());
        status.setText(Boolean.toString(orderList.get(position).getStatus()));
        amount.setText(orderList.get(position).getAmount());
        return view;
    }
}
