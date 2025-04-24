package com.loyalstring.fragments;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.loyalstring.Adapters.IssuetrackerAdapter;
import com.loyalstring.R;
import com.loyalstring.database.product.EntryDatabase;
import com.loyalstring.databinding.FragmentIssuetrackerfragmentBinding;
import com.loyalstring.modelclasses.Issuemode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Issuetrackerfragment extends Fragment implements IssuetrackerAdapter.OnIclick{

    FragmentIssuetrackerfragmentBinding b;

    EntryDatabase entryDatabase;

    List<Issuemode> itemList = new ArrayList<>();
    Map<String, List<Issuemode>> groupedItems = new HashMap<>();
    IssuetrackerAdapter issuetrackerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         b = FragmentIssuetrackerfragmentBinding.inflate(inflater, container, false);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_issuetrackerfragment, container, false);


        entryDatabase = new EntryDatabase(getActivity());





        itemList  = entryDatabase.getIssueitems(getActivity());
        groupedItems = groupItemsByOperationTime();
        b.issuerecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        issuetrackerAdapter  = new IssuetrackerAdapter(getActivity(), groupedItems, this);
        b.issuerecycler.setAdapter(issuetrackerAdapter);






        return  b.getRoot();
    }

    public Map<String, List<Issuemode>> groupItemsByOperationTime() {
//        List<Issuemode> itemList = getAllItems();
        Map<String, List<Issuemode>> groupedItems = new HashMap<>();

        for (Issuemode item : itemList) {
            String operationTime = String.valueOf(item.getOperationTime()); // Assuming getOperationTime() method exists
            if (!groupedItems.containsKey(operationTime)) {
                groupedItems.put(operationTime, new ArrayList<>());
            }
            groupedItems.get(operationTime).add(item);
        }

        return groupedItems;
    }

    @Override
    public void onclick(List<Issuemode> item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Issues");



        ScrollView scrollView = new ScrollView(getActivity());
        LinearLayout linearLayout = new LinearLayout(getActivity());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(16, 16, 16, 16);

        // Inflate and add each item to the LinearLayout
        LayoutInflater inflater = LayoutInflater.from(getActivity());

        StringBuilder issueBuilder = new StringBuilder();
        String iss = "";
        for(int i = 0; i<item.size(); i ++){
            Issuemode is = item.get(i);
            issueBuilder = issueBuilder.append(is.getIssue()).append("\n");

            View itemView = inflater.inflate(R.layout.item_issue, linearLayout, false);
            TextView issueText = itemView.findViewById(R.id.issue_text);
            TextView issueText1 = itemView.findViewById(R.id.issue_index);
            issueText.setText(is.getIssue());
            issueText1.setText(String.valueOf(i+1));
            linearLayout.addView(itemView);
        }
        String issue = issueBuilder.toString();

        // Create a ScrollView and a TextView
        scrollView.addView(linearLayout);

        builder.setView(scrollView);

        // Add a close button
        builder.setPositiveButton("Close", (dialog, which) -> dialog.dismiss());

        // Show the dialog
        builder.show();

    }
}