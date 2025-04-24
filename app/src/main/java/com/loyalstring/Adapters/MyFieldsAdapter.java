package com.loyalstring.Adapters;

import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFieldsAdapter extends RecyclerView.Adapter<MyFieldsAdapter.ViewHolder> {
    private List<String> myFields;
    private List<String> excelHeadings;
    private Map<String, String> selectedMappings;
    private Map<String, String> savedMappings;
    private String mapname;

    public MyFieldsAdapter(List<String> myFields, List<String> excelHeadings) {
        this.myFields = myFields;
        this.excelHeadings = excelHeadings;
        this.selectedMappings = new HashMap<>();
        this.savedMappings = new HashMap<>();
        this.mapname = "";
    }

    public void updateSavedMappings(Map<String, String> savedMappings) {
        this.savedMappings = savedMappings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_field, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String myField = myFields.get(position);
        holder.textViewFieldName.setText(myField);
        if(mapname.equalsIgnoreCase("custom")){
            holder.heading.setText("Choose heading"); // Set hint for "Custom"
            holder.heading.setOnClickListener(v -> showHeadingListDialog(holder.heading, myField));
        } else {
            if (savedMappings != null && !savedMappings.isEmpty()) {
                if (savedMappings.containsKey(myField)) {
                    String savedHeading = savedMappings.get(myField);
                    int savedHeadingIndex = excelHeadings.indexOf(savedHeading);
                    if (savedHeadingIndex != -1) {
                        // Set the saved heading for this field
                        holder.heading.setText(savedHeading);
                        selectedMappings.put(myField, savedHeading);
                        Log.d("check selection", "  " + myField + "  " + savedHeading);
                    }
                }
                holder.heading.setOnClickListener(v -> showHeadingListDialog(holder.heading, myField));
            } else {
                holder.heading.setText("Choose heading1"); // Clear text
                holder.heading.setOnClickListener(null); // Remove OnClickListener
            }
        }

    }


    private void showHeadingListDialog(TextView textView, String head) {
        AlertDialog.Builder builder = new AlertDialog.Builder(textView.getContext());
        builder.setTitle("Select Heading for " + head);

        // Convert excelHeadings List to String array for dialog items
        String[] headingsArray = excelHeadings.toArray(new String[0]);

        builder.setItems(headingsArray, (dialog, which) -> {
            String selectedHeading = headingsArray[which];

            // Check if the selected heading is already in selectedMappings
            if (selectedMappings.containsValue(selectedHeading)) {
                // Show toast and do not add to savedMappings
                Toast.makeText(textView.getContext(), "This heading is already selected for another field", Toast.LENGTH_SHORT).show();
                textView.setText("choose heading");
                selectedMappings.remove(head);
            } else {
                // Update the TextView with the selected heading
                textView.setText(selectedHeading);
                // Update selectedMappings accordingly
                selectedMappings.put(head, selectedHeading);
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return myFields.size();
    }

    public Map<String, String> getSelectedMappings() {
        return selectedMappings;
    }

    public void updatemapname(String selectedItem) {
        this.mapname = selectedItem;
        if(mapname.equalsIgnoreCase("custom")){
            selectedMappings.clear();
            savedMappings.clear();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFieldName, heading;
        Spinner spinnerExcelHeadings;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewFieldName = itemView.findViewById(R.id.textViewFieldName);
            spinnerExcelHeadings = itemView.findViewById(R.id.spinnerExcelHeadings);
            heading = itemView.findViewById(R.id.heading);
        }
    }
}
