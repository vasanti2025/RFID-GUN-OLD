package com.loyalstring.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;
import com.loyalstring.modelclasses.Issuemode;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class IssuetrackerAdapter extends RecyclerView.Adapter<IssuetrackerAdapter.Viewholder> {

    Context context;
    Map<String, List<Issuemode>> issueitems;

    private OnIclick onIclick;


    public IssuetrackerAdapter(FragmentActivity activity, Map<String, List<Issuemode>> groupedItems, OnIclick onIclick) {
        this.context = activity;
        this.issueitems = groupedItems;
        this.onIclick = onIclick;
    }

    public interface OnIclick{
        void onclick(List<Issuemode> item);
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_item, parent, false);

        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        List<String> itemKeys = new ArrayList<>(issueitems.keySet());
        String itemKey = itemKeys.get(position);
        List<Issuemode> item = issueitems.get(itemKey);

        String numericString = item.get(0).getIssueId().substring(2);
        String pr = item.get(0).getIssueId().substring(0, 2);

        // Step 2: Convert the numeric string to a long timestamp
        long timestamp = Long.parseLong(numericString);

        // Step 3: Create a Date object using the timestamp
        Date date = new Date(timestamp);

        // Step 4: Format the date using SimpleDateFormat
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy:HH:mm:ss");
//        sdf.setTimeZone(TimeZone.getTimeZone("GMT")); // Set timezone if needed

        // Step 5: Format the date
        String formattedDate = sdf.format(date);


        holder.issueid.setText(pr+formattedDate);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

onIclick.onclick(item);



            }
        });


    }

    @Override
    public int getItemCount() {
        return issueitems.size();
    }


    public class Viewholder extends RecyclerView.ViewHolder {

        TextView issueid;

        public Viewholder(@NonNull View itemView) {
            super(itemView);


            issueid = itemView.findViewById(R.id.issueid);


        }
    }
}
