package com.loyalstring.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.modelclasses.jjjcustomermodel;

import java.util.List;

public class UserDatumAdapter extends RecyclerView.Adapter<UserDatumAdapter.ViewHolder> {
    private List<jjjcustomermodel.UserDatum> userList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(jjjcustomermodel.UserDatum userDatum);
    }

    public UserDatumAdapter(List<jjjcustomermodel.UserDatum> userList, OnItemClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        jjjcustomermodel.UserDatum user = userList.get(position);
        holder.bind(user, listener);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(android.R.id.text1);
        }

        void bind(final jjjcustomermodel.UserDatum userDatum, final OnItemClickListener listener) {
            textView.setText(userDatum.getFull_name());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(userDatum);
                }
            });
        }
    }
}