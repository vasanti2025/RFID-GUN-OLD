package com.loyalstring.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;
import com.loyalstring.modelclasses.Itemmodel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BillListAdaptor extends RecyclerView.Adapter<BillListAdaptor.Viewholder> {
    private Map<String, List<Itemmodel>> searchlist;
    Context context;
    private Onsend onsend;
    private  MoreOptions moreOptions;


    public BillListAdaptor(FragmentActivity activity, Map<String, List<Itemmodel>> alllsit, Onsend onsend, MoreOptions moreOptions) {
        this.context = activity;
        this.searchlist = alllsit;
        this.onsend = onsend;
        this.moreOptions = moreOptions;
    }


    public interface Onsend{
        void onsend(String itemKey, List<Itemmodel> item);
    }

    public interface MoreOptions{
        void MoreOptions(List<Itemmodel> item, View view, Context context);
    }


    @NonNull
    @Override
    public BillListAdaptor.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.billlist_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillListAdaptor.Viewholder holder, int position) {

        List<String> itemKeys = new ArrayList<>(searchlist.keySet());
        String itemKey = itemKeys.get(position);
        List<Itemmodel> item = searchlist.get(itemKey);
        Log.d("checkbillitem ", ""+item.get(0).getInvoiceNumber());
        holder.sno.setText(String.valueOf(position+1));
        long timestamp = item.get(0).getOperationTime();//System.currentTimeMillis(); // Current timestamp
        String formattedDate = convertTimestampToDate(timestamp);
        holder.bldate.setText(String.valueOf(formattedDate));
        holder.invno.setText(item.get(0).getInvoiceNumber()+" "+item.get(0).getCustomerName());
        holder.total.setText(String.valueOf(item.size()));
//        holder.sendexcel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onsend.onsend(itemKey, item);
//
//            }
//        });

        holder.moreoptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                moreOptions.MoreOptions(item, view, context);

            }
        });






    }

    @Override
    public int getItemCount() {
        return searchlist.size();
    }

    public static String convertTimestampToDate(long timestamp) {
        // Create a Date object from the timestamp
        Date date = new Date(timestamp);

        // Define the desired date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // Format the date to the desired format and return as a string
        return dateFormat.format(date);
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView sno, bldate, invno, total;
        RelativeLayout sendexcel, moreoptions;



        public Viewholder(@NonNull View itemView) {
            super(itemView);

            sno = itemView.findViewById(R.id.sno);
            bldate = itemView.findViewById(R.id.bldate);
            invno = itemView.findViewById(R.id.invnumber);
            total = itemView.findViewById(R.id.titems);
//            sendexcel = itemView.findViewById(R.id.excelbtn);
            moreoptions = itemView.findViewById(R.id.moreoptions);


        }
    }
}
