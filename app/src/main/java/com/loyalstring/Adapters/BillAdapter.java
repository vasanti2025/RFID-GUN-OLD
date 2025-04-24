package com.loyalstring.Adapters;

import static com.loyalstring.Activities.BillViewactivity.decimalFormat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;
import com.loyalstring.fsupporters.Itemdialog;
import com.loyalstring.interfaces.interfaces;
import com.loyalstring.modelclasses.Itemmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.Viewholder> {

    private TreeMap<String, Itemmodel> searchlist;
    Context context;
    private Removeitem removeitem;
    Itemdialog itemdialog = new Itemdialog();

    interfaces.ItemUpdateListener listener;
    String type = "";

    public BillAdapter(FragmentActivity activity, TreeMap<String, Itemmodel> searchitems, Removeitem removeitem, interfaces.ItemUpdateListener listener) {
        this.context = activity;
        this.searchlist = searchitems;
        this.removeitem = removeitem;
        this.listener = listener;
    }

    public void updatebilltype(String order) {
        this.type = order;
    }

    public interface Removeitem {
        void onRemoveitem(Itemmodel i, int position, String itemKey);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_layout, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        List<String> itemKeys = new ArrayList<>(searchlist.keySet());
        String itemKey = itemKeys.get(position);
        Itemmodel item = searchlist.get(itemKey);


        if (item.getItemAddmode().equalsIgnoreCase("yes")) {
            holder.sno.setText(String.valueOf(position + 1));
            holder.product.setText(String.valueOf(item.getProduct()));
            holder.barcode.setText(String.valueOf(item.getBarCode()));
            holder.gwt.setText(decimalFormat.format(item.getGrossWt()));
            holder.itemcode.setText(item.getItemCode());
            holder.netwt.setText(decimalFormat.format(item.getNetWt()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemdialog.showItemDetails1(holder.getAdapterPosition(), context, item, type, listener);
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removeitem.onRemoveitem(item, position, itemKey);
                }
            });

        } else {
            holder.itemholder.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        int count = searchlist.size();
//        for (Itemmodel item : searchlist.values()) {
//            if (item.getItemAddmode().equalsIgnoreCase("yes")) {
//                count++;
//            }
//        }
        return count;
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView sno, product, barcode, gwt, itemcode, netwt;
        ImageView status;
        RelativeLayout remove;

        RelativeLayout itemholder;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            itemholder = itemView.findViewById(R.id.bottomitemlay);
            sno = itemView.findViewById(R.id.sno);
            product = itemView.findViewById(R.id.product);
            barcode = itemView.findViewById(R.id.barcode);
            gwt = itemView.findViewById(R.id.gwt);
            itemcode = itemView.findViewById(R.id.itemcode);
            netwt = itemView.findViewById(R.id.netwt);
            remove = itemView.findViewById(R.id.itemremove);

        }
    }
}
