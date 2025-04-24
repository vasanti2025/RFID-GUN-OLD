package com.loyalstring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.Activities.Skureport;
import com.loyalstring.R;
import com.loyalstring.modelclasses.Itemmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Skureportadpter extends RecyclerView.Adapter<Skureportadpter.ViewHolder>{

    TreeMap<String, List<Itemmodel>> skureport1 = new TreeMap<>();
    Context context;

    private List<Map.Entry<String, List<Itemmodel>>> skureportEntries;

    public Skureportadpter(Context context, TreeMap<String, List<Itemmodel>> skureport1) {

        this.context = context;
        this.skureport1 = skureport1;
        this.skureportEntries = new ArrayList<>(skureport1.entrySet());
    }

    @NonNull
    @Override
    public Skureportadpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_skureport, parent, false);


        return new  ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Skureportadpter.ViewHolder holder, int position) {

        Map.Entry<String, List<Itemmodel>> entry = skureportEntries.get(position);

        String key = entry.getKey(); // This is the key in the TreeMap
        List<Itemmodel> itemList = entry.getValue(); // This is the list of Itemmodel objects

        // Bind the data to the holder's views
//        holder.keyTextView.setText(key);

        // Assuming you have a TextView to display the number of items
//        holder.itemCountTextView.setText(String.valueOf(itemList.size()));

        // Handle other views in the ViewHolder based on the data in itemList
        // For example, you might want to display details of the first item in the list
        if (!itemList.isEmpty()) {
            Itemmodel firstItem = itemList.get(0);
            String en = firstItem.getStockKeepingUnit()+"\n"+firstItem.getItemCode()+"\n"+firstItem.getGrossWt()+"\n"+firstItem.getNetWt();
            holder.text.setText(en);
            // Bind the firstItem details to other views in the holder
//            holder.itemDetailTextView.setText(firstItem.getDetail()); // Example
        }

    }

    @Override
    public int getItemCount() {
        return skureport1.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
        }
    }


}
