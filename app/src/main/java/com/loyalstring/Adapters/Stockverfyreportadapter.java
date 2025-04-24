package com.loyalstring.Adapters;

import static com.loyalstring.fragments.Stockreportfragment.childscroll;
import static com.loyalstring.fragments.Stockreportfragment.userScrolling;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;
import com.loyalstring.modelclasses.svmodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Stockverfyreportadapter extends RecyclerView.Adapter<Stockverfyreportadapter.Viewholder> {
    private HashMap<String, svmodel> itemList;
    private String filter;
    private Context context;
    private Onclick onclick;
    private Onpause onpause;
    private scrolllisten scrolllisten;
    int x = 0;
    int y = 0;
    boolean isScrolling = false;



    public void updatescrolling(int scrollX, int scrollY) {
        if (!isScrolling) {
            this.x = scrollX;
            this.y = scrollY;
            notifyDataSetChanged();
        }
    }

    public void setScrolling(boolean scrolling) {
        isScrolling = scrolling;
    }




    public interface Onclick {
        void onclick(int position, String date, svmodel item);
    }


    public interface scrolllisten{
        void onScrollChanged(int scrollX, int scrollY);

    }

    public interface Onpause{
        void onpause(int position, String date, svmodel item);
    }

    public Stockverfyreportadapter(HashMap<String, svmodel> itemList, Context context, Onclick onclick, scrolllisten scrolllisten, Onpause pause ) {
        this.itemList = itemList;
        this.filter = filter;
        this.context = context;
        this.onclick = onclick;
        this.scrolllisten = scrolllisten;
        this.onpause = pause;

    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.stockverfylayout, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
//        svmodel item = itemList.get(position);

        Map.Entry<String, svmodel> entry = new ArrayList<>(itemList.entrySet()).get(position);
        String key = entry.getKey();
        svmodel item = entry.getValue();
        holder.svdate.setText(item.getDate());
        holder.svtqty.setText(String.valueOf(item.getTotalqty()));
        holder.svtwt.setText(String.valueOf(item.getTotalwt()));
        holder.svmqty.setText(String.valueOf(item.getMatchedqty()));
        holder.svmwt.setText(String.valueOf(item.getMatchedwt()));
        holder.svumqty.setText(String.valueOf(item.getUnmatchedqty()));
        holder.svumwt.setText(String.valueOf(item.getUnmatchedwt()));

        if (item.getTotalqty() == item.getMatchedqty()) {
            holder.svstatus.setText("Matched");
        } else {
            holder.svstatus.setText("Missing");
        }

        holder.mainholder.setBackgroundColor(context.getResources().getColor(R.color.underlinecolor));
        if (item.getTotalqty() > 0) {
            if (item.getTotalqty() == item.getNotscan()) {
//                holder.svholder.setBackgroundColor(context.getResources().getColor(R.color.emptystatus));
            }
            if (item.getMatchedqty() == item.getTotalqty()) {
//                holder.svholder.setBackgroundColor(context.getResources().getColor(R.color.foundstatus));
                holder.svrimage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_success));
            }
            if (item.getUnmatchedqty() == item.getTotalqty()) {
//                holder.svholder.setBackgroundColor(context.getResources().getColor(R.color.notfoundstatus));
                holder.svrimage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_cross));
            }
            if (item.getMatchedqty() > 0 && item.getUnmatchedqty() > 0) {
//                holder.svholder.setBackgroundColor(context.getResources().getColor(R.color.skyblue));
                holder.svrimage.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.i_successyellow));
            }
        } else {

        }
        holder.svrimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();
                onclick.onclick(holder.getAdapterPosition(), item.getDate(), item);
            }
        });


            holder.horizontalScrollView.scrollTo(x, y);

//        }
        holder.horizontalScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                x = scrollX;
                y = scrollY;


                    if (scrolllisten != null) {
                        scrolllisten.onScrollChanged(scrollX, scrollY);
                }
            }
        });

        holder.svstatusimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onpause.onpause(holder.getAdapterPosition(), item.getDate(), item);
            }
        });


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class Viewholder  extends RecyclerView.ViewHolder{

        public TextView svdate, svtqty, svtwt, svmqty, svmwt, svumqty, svumwt, svns, svnswt, svstatus;
        public LinearLayout svholder;

        public RelativeLayout svritemhold, mainholder;


        public HorizontalScrollView horizontalScrollView;
        public ImageView svrimage, svstatusimg1;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


            svritemhold = itemView.findViewById(R.id.svritemhold);
            svdate = itemView.findViewById(R.id.svdate);
            svtqty = itemView.findViewById(R.id.svtqty);
            svtwt = itemView.findViewById(R.id.svtwt);
            svmqty = itemView.findViewById(R.id.svmqty);
            svmwt = itemView.findViewById(R.id.svmwt);
            svumqty = itemView.findViewById(R.id.svumqty);
            svumwt = itemView.findViewById(R.id.svunwt);
            svstatus = itemView.findViewById(R.id.svstatus);
            svholder = itemView.findViewById(R.id.svholder);
            horizontalScrollView = itemView.findViewById(R.id.reportitemscroll);
            svrimage = itemView.findViewById(R.id.svstatusimg);
            mainholder = itemView.findViewById(R.id.mainholder);
            svstatusimg1= itemView.findViewById(R.id.svstatusimg1);
        }
    }
}