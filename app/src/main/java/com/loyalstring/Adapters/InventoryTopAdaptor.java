package com.loyalstring.Adapters;

import static com.loyalstring.MainActivity.decimalFormat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;
import com.loyalstring.modelclasses.Itemmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryTopAdaptor extends RecyclerView.Adapter<InventoryTopAdaptor.Viewholder>{
    private Map<String, Itemmodel> topList;
    private Context context;
//    private OnItemClickListener onItemClickListener;
    private int lastScrollX = 0;
    private RecyclerView recyclerView;
    String viewitem;
    int matchedCounter = 0;
    int unmatchedCounter = 0;
    private  Onclickitem onclickitem;

    public InventoryTopAdaptor(HashMap<String, Itemmodel> toplist1, Context activity, String all, RecyclerView irecycler, Onclickitem onclickitem) {
        this.topList = toplist1;
        this.context = activity;
        this.recyclerView = irecycler;
        this.viewitem = all;
        this.onclickitem = onclickitem;

    }


    public interface Onclickitem{
//        void onclicl

        void onclickitem(String category, String product, Itemmodel item);
    }

    public void updatedata(HashMap<String, Itemmodel> topmatch) {
        this.topList = topmatch;
    }

    public void updateview(String st){
        this.viewitem = st;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventorycategorytop_layout, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        List<String> itemKeys = new ArrayList<>(topList.keySet());
        String itemKey = itemKeys.get(position);
        Itemmodel item = topList.get(itemKey);


//        Log.d("found itemsc", "  " + item.getBarCode() + "  " + item.getMatchQty() + "  "+itemKeys.size()+"  "+item.getAvlQty());
        if (viewitem.equalsIgnoreCase("all")) {
            // Bind data for all items
            // ...

            holder.sno.setText(String.valueOf(position + 1));
            holder.product.setText(item.getProduct());
            holder.mPieces.setText(String.valueOf(item.getTotMPcs()));
            holder.tPieces.setText(String.valueOf(item.getTotPcs()));

            holder.tqty.setText(String.valueOf(item.getAvlQty()));

            holder.mqty.setText(String.valueOf(item.getMatchQty()));

            holder.tgwt.setText(String.valueOf(decimalFormat.format(item.getTotalGwt())));
            holder.mgwt.setText(String.valueOf(decimalFormat.format(item.getMatchGwt())));
            holder.tswt.setText(String.valueOf(decimalFormat.format(item.getTotalStonewt())));
            holder.mswt.setText(String.valueOf(decimalFormat.format(item.getMatchStonewt())));
            holder.tnwt.setText(String.valueOf(decimalFormat.format(item.getTotalNwt())));
            holder.mnwt.setText(String.valueOf(decimalFormat.format(item.getMatchNwt())));
//            holder.itage.setText(String.valueOf(item.getEntryDate()));

            if (item.getAvlQty() == item.getMatchQty()) {
                holder.status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_success));
            } else {
                holder.status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_cross));
            }

        }
        else if(viewitem.equalsIgnoreCase("matched")){
            if (item.getAvlQty() == item.getMatchQty()) {
                holder.sno.setText(String.valueOf(++matchedCounter));
                holder.product.setText(item.getProduct());
                holder.mPieces.setText(item.getTotMPcs());
                holder.tPieces.setText(item.getTotPcs());

                holder.tqty.setText(String.valueOf(item.getAvlQty()));

                holder.mqty.setText(String.valueOf(item.getMatchQty()));
                holder.tgwt.setText(String.valueOf(decimalFormat.format(item.getTotalGwt())));
                holder.mgwt.setText(String.valueOf(decimalFormat.format(item.getMatchGwt())));
                holder.tswt.setText(String.valueOf(decimalFormat.format(item.getTotalStonewt())));
                holder.mswt.setText(String.valueOf(decimalFormat.format(item.getMatchStonewt())));
                holder.tnwt.setText(String.valueOf(decimalFormat.format(item.getTotalNwt())));
                holder.mnwt.setText(String.valueOf(decimalFormat.format(item.getMatchNwt())));
                holder.status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_success));
            }else{
                holder.itemholder.setVisibility(View.GONE);
            }

        }
        else if(viewitem.equalsIgnoreCase("unmatched")){
            if (item.getAvlQty() != item.getMatchQty()) {
                holder.sno.setText(String.valueOf(++unmatchedCounter));
                holder.product.setText(item.getProduct());
                holder.mPieces.setText(item.getTotMPcs());
                holder.tPieces.setText(item.getTotPcs());


                holder.tqty.setText(String.valueOf(item.getAvlQty()));
                holder.mqty.setText(String.valueOf(item.getMatchQty()));
                holder.tgwt.setText(String.valueOf(decimalFormat.format(item.getTotalGwt())));
                holder.mgwt.setText(String.valueOf(decimalFormat.format(item.getMatchGwt())));
                holder.tswt.setText(String.valueOf(decimalFormat.format(item.getTotalStonewt())));
                holder.mswt.setText(String.valueOf(decimalFormat.format(item.getMatchStonewt())));
                holder.tnwt.setText(String.valueOf(decimalFormat.format(item.getTotalNwt())));
                holder.mnwt.setText(String.valueOf(decimalFormat.format(item.getMatchNwt())));
                holder.status.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_cross));
            }else{
                holder.itemholder.setVisibility(View.GONE);
            }
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, item.getProduct(), Toast.LENGTH_SHORT).show();
                onclickitem.onclickitem(item.getCategory(), item.getProduct(), item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return topList.size();
    }



    public class Viewholder extends RecyclerView.ViewHolder {
        TextView sno, product, box, tqty, mqty, tgwt, mgwt, tswt, mswt, tnwt, mnwt,tPieces, mPieces;
        ImageView status;
        RelativeLayout itemholder;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            itemholder = itemView.findViewById(R.id.topitemlay);
            sno = itemView.findViewById(R.id.itsno);
            product = itemView.findViewById(R.id.itproduct);
            box = itemView.findViewById(R.id.itbox);
            mPieces=itemView.findViewById(R.id.itMPieces);
            tPieces=itemView.findViewById(R.id.itPieces);

            tqty = itemView.findViewById(R.id.ittqty);
            mqty = itemView.findViewById(R.id.itmqty);
            tgwt = itemView.findViewById(R.id.ittgwt);
            mgwt = itemView.findViewById(R.id.itmgwt);
            tswt = itemView.findViewById(R.id.ittstonewt);
            mswt = itemView.findViewById(R.id.itmatchstone);
            tnwt = itemView.findViewById(R.id.ittnwt);
            mnwt = itemView.findViewById(R.id.itmatchnwt);
            status = itemView.findViewById(R.id.itstatus);




        }
    }

}
