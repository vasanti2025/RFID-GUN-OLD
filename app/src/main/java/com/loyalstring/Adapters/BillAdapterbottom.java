package com.loyalstring.Adapters;

import static com.loyalstring.Activities.BillViewactivity.decimalFormat;

import android.content.Context;
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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class BillAdapterbottom extends RecyclerView.Adapter<BillAdapterbottom.Viewholder> {

    private Map<String, Itemmodel> searchlist;
    Context context;
    private Removebottomitem removebottomitem;
    private  Editbottomitem editbottomitem;
    Itemdialog itemdialog = new Itemdialog();

    interfaces.ItemUpdateListener listener;
    String type = "";
    public BillAdapterbottom(FragmentActivity activity, TreeMap<String, Itemmodel> searchitems, Removebottomitem removeitem, interfaces.ItemUpdateListener listener) {
        this.context = activity;
        this.searchlist = searchitems;
        this.removebottomitem = removeitem;
        this.listener = listener;
    }

    public void updatebilltype(String order) {
        this.type = order;
    }

    public interface Removebottomitem{
        void onRemovebottomitem(Itemmodel i, int position, String itemKey, String type);
    }

    public  interface Editbottomitem{

        void onEditbottomitem(Itemmodel i, int position, String itemKey);
    }

    @NonNull
    @Override
    public BillAdapterbottom.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View

                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill_layout, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        List<String> itemKeys = new ArrayList<>(searchlist.keySet());
        String itemKey = itemKeys.get(position);
        Itemmodel item = searchlist.get(itemKey);



            holder.sno.setText(String.valueOf(position+1));
            holder.product.setText(String.valueOf(item.getGrossWt()));
            holder.barcode.setText(String.valueOf(item.getNetWt()));
            holder.gwt.setText(decimalFormat.format(item.getStoneAmount()));
            holder.itemcode.setText(decimalFormat.format(item.getMakingPer()));
            holder.netwt.setText(decimalFormat.format(item.getFixedWastage()));

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemdialog.showItemDetails1(holder.getAdapterPosition(), context, item, type, listener);
                }
            });
            holder.remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    removebottomitem.onRemovebottomitem(item, position, itemKey, "remove");
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    removebottomitem.onRemovebottomitem(item, position, itemKey, "edit");
                    return true;
                }
            });




    }

    @Override
    public int getItemCount() {
        return searchlist.size();
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
