package com.loyalstring.fsupporters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.loyalstring.R;
import com.loyalstring.interfaces.interfaces;
import com.loyalstring.modelclasses.Itemmodel;

import java.io.File;

public class Itemdialog {


    public void showItemDetails(int position, Context context, Itemmodel item, String type) {
        // Retrieve the item details based on the clicked position
//        List<String> itemKeys = new ArrayList<>(bottomlist.keySet());
//        String itemKey = itemKeys.get(position);
//        Itemmodel item = bottomlist.get(itemKey);

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_item_details, null);

        // Get references to the views in the custom layout
        ImageView itemImage = dialogView.findViewById(R.id.item_image);
        TextView itemDetails = dialogView.findViewById(R.id.item_details);

        EditText grossWtEdit = dialogView.findViewById(R.id.edit_grosswt);
        EditText stoneWtEdit = dialogView.findViewById(R.id.edit_stonewt);
        EditText netWtEdit = dialogView.findViewById(R.id.edit_netwt);
        EditText notesEdit = dialogView.findViewById(R.id.edit_notes);


        // Set the item details
        itemDetails.setText(
                "Date: " + checknull(item.getDiamondMetal()) + "\n" +
                "Category: " + checknull(item.getCategory()) + "\n" +
                        "Product: " + checknull(item.getProduct()) + "\n" +
                        "from: " + checknull(item.getDiamondClarity()) + "\n" +
                        "to: " + checknull(item.getDiamondColor()) + "\n" +
                        "Purity: " + checknull(item.getPurity()) + "\n" +
                        "Barcode: " + checknull(item.getBarCode()) + "\n" +
                        "Itemcode: " + checknull(item.getItemCode()) + "\n" +
                        "Box: " + checknull(item.getBox()) + "\n" +
                        "Pieces: " + checknull(item.getPcs()) + "\n" +
                        "Gross weight: " + item.getGrossWt() + "\n" +
                        "Stone weight: " + item.getStoneWt() + "\n" +
                        "Net weight: " + item.getNetWt());

        grossWtEdit.setText(String.valueOf(item.getGrossWt()));
        stoneWtEdit.setText(String.valueOf(item.getStoneWt()));
        netWtEdit.setText(String.valueOf(item.getNetWt()));
        notesEdit.setText(checknull(item.getDescription())); // Assuming you have a method to get the notes

        // Show or hide the edit fields based on the type
        if ("order".equals(type)) {
            grossWtEdit.setVisibility(View.VISIBLE);
            stoneWtEdit.setVisibility(View.VISIBLE);
            netWtEdit.setVisibility(View.VISIBLE);
            notesEdit.setVisibility(View.VISIBLE);
        } else {
            grossWtEdit.setVisibility(View.GONE);
            stoneWtEdit.setVisibility(View.GONE);
            netWtEdit.setVisibility(View.GONE);
            notesEdit.setVisibility(View.GONE);
        }



        String basePath = getExternalStoragePath(context);
        // Load the image into the ImageView
        // Here you need to replace `item.getImageUrl()` with the actual method to get the image URL or resource ID

        String onlineimage = item.getImageUrl();
        String iname = item.getItemCode();


        String imageUrl = item.getItemCode() + ".jpg"; // Assuming you have a method to get the image URL
        Log.e("loadimage", "" + imageUrl);

        if (onlineimage != null && !onlineimage.isEmpty()) {
            Glide.with(context)
                    .load(onlineimage)
                    .placeholder(R.drawable.logo) // Optional: a placeholder image while loading
                    .error(R.drawable.logo) // Optional: an error image if loading fails
                    .into(itemImage);
        }
        else {

            if (item.getItemCode() != null && !item.getItemCode().isEmpty()) {

                File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Loyalstring files/images/" + imageUrl);


                if (checkIfFileExists(imageFile)) {
                    Toast.makeText(context, "image found", Toast.LENGTH_SHORT).show();
//            Log.e("loadimage", "File exists: " + imageFile.getAbsolutePath());
                    Glide.with(context)
                            .load(imageFile)
                            .placeholder(R.drawable.logo) // Optional: a placeholder image while loading
                            .error(R.drawable.logo) // Optional: an error image if loading fails
                            .into(itemImage);
                } else {
                    Toast.makeText(context, "image not found", Toast.LENGTH_SHORT).show();
//            Log.e("loadimage", "File does not exist: " + imagePath);
                    itemImage.setImageResource(R.drawable.logo); // Set a default image if file does not exist
                }
            } else {
                itemImage.setImageResource(R.drawable.logo);
            }
        }
        // Build and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Item Details")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog if needed
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private String checknull(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    private static String getExternalStoragePath(Context context) {
        File[] externalStorageVolumes =
                ContextCompat.getExternalFilesDirs(context, null);
        File primaryExternalStorage = externalStorageVolumes[0];
        return primaryExternalStorage.getAbsolutePath();
    }

    private boolean checkIfFileExists(File file) {
        if (file.exists()) {
            Log.d("FileCheck", "File exists: " + file.getAbsolutePath());
            return true;
        } else {
            Log.d("FileCheck", "File does not exist: " + file.getAbsolutePath());
            return false;
        }
    }


    public void showItemDetails1(int position, Context context, Itemmodel item, String type , interfaces.ItemUpdateListener listener) {
        // Retrieve the item details based on the clicked position
//        List<String> itemKeys = new ArrayList<>(bottomlist.keySet());
//        String itemKey = itemKeys.get(position);
//        Itemmodel item = bottomlist.get(itemKey);

        // Inflate the custom layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_item_details, null);

        // Get references to the views in the custom layout
        ImageView itemImage = dialogView.findViewById(R.id.item_image);
        TextView itemDetails = dialogView.findViewById(R.id.item_details);

        EditText grossWtEdit = dialogView.findViewById(R.id.edit_grosswt);
        EditText stoneWtEdit = dialogView.findViewById(R.id.edit_stonewt);
        EditText netWtEdit = dialogView.findViewById(R.id.edit_netwt);
        EditText notesEdit = dialogView.findViewById(R.id.edit_notes);


        // Set the item details
        itemDetails.setText(
                "Category: " + checknull(item.getCategory()) + "\n" +
                        "Product: " + checknull(item.getProduct()) + "\n" +
                        "Purity: " + checknull(item.getPurity()) + "\n" +
                        "Barcode: " + checknull(item.getBarCode()) + "\n" +
                        "Itemcode: " + checknull(item.getItemCode()) + "\n" +
                        "Box: " + checknull(item.getBox()) + "\n" +
                        "Pieces: " + checknull(item.getPcs()) + "\n" +
                        "Gross weight: " + item.getGrossWt() + "\n" +
                        "Stone weight: " + item.getStoneWt() + "\n" +
                        "Net weight: " + item.getNetWt()+"\n" +
                        "Remark: " + item.getDescription());

        grossWtEdit.setText(String.valueOf(item.getGrossWt()));
        stoneWtEdit.setText(String.valueOf(item.getStoneWt()));
        netWtEdit.setText(String.valueOf(item.getNetWt()));
        notesEdit.setText(checknull(item.getDescription())); // Assuming you have a method to get the notes

        // Show or hide the edit fields based on the type
        if ("order".equals(type)) {
            grossWtEdit.setVisibility(View.VISIBLE);
            stoneWtEdit.setVisibility(View.VISIBLE);
            netWtEdit.setVisibility(View.VISIBLE);
            notesEdit.setVisibility(View.VISIBLE);
        } else {
            grossWtEdit.setVisibility(View.GONE);
            stoneWtEdit.setVisibility(View.GONE);
            netWtEdit.setVisibility(View.GONE);
            notesEdit.setVisibility(View.VISIBLE);
        }



        String basePath = getExternalStoragePath(context);
        // Load the image into the ImageView
        // Here you need to replace `item.getImageUrl()` with the actual method to get the image URL or resource ID

        String onlineimage = item.getImageUrl();
        String iname = item.getItemCode();


        String imageUrl = item.getItemCode() + ".jpg"; // Assuming you have a method to get the image URL
        Log.e("loadimage", "" + imageUrl);

        if (onlineimage != null && !onlineimage.isEmpty()) {
            Glide.with(context)
                    .load(onlineimage)
                    .placeholder(R.drawable.logo) // Optional: a placeholder image while loading
                    .error(R.drawable.logo) // Optional: an error image if loading fails
                    .into(itemImage);
        }
        else {

            if (item.getItemCode() != null && !item.getItemCode().isEmpty()) {

                File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + "/Loyalstring files/images/" + imageUrl);


                if (checkIfFileExists(imageFile)) {
                    Toast.makeText(context, "image found", Toast.LENGTH_SHORT).show();
//            Log.e("loadimage", "File exists: " + imageFile.getAbsolutePath());
                    Glide.with(context)
                            .load(imageFile)
                            .placeholder(R.drawable.logo) // Optional: a placeholder image while loading
                            .error(R.drawable.logo) // Optional: an error image if loading fails
                            .into(itemImage);
                } else {
                    Toast.makeText(context, "image not found", Toast.LENGTH_SHORT).show();
//            Log.e("loadimage", "File does not exist: " + imagePath);
                    itemImage.setImageResource(R.drawable.logo); // Set a default image if file does not exist
                }
            } else {
                itemImage.setImageResource(R.drawable.logo);
            }
        }
        // Build and show the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Item Details")
                .setView(dialogView)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the dialog if needed

                        double gwt = item.getGrossWt();
                        double swt = item.getStoneWt();
                        double nwt = item.getNetWt();
                        if ("order".equals(type)) {
                            // Save the edited values
                            item.setGrossWt(Double.parseDouble(grossWtEdit.getText().toString()));
                            item.setStoneWt(Double.parseDouble(stoneWtEdit.getText().toString()));
                            item.setNetWt(Double.parseDouble(netWtEdit.getText().toString()));
                            item.setDescription(notesEdit.getText().toString()); // Assuming you have a setter for notes

                            // Call the listener to update the item in the fragment
                            if (listener != null) {
                                listener.onItemUpdated(item, gwt, swt, nwt);
                            }
                        }else{
                            item.setDescription(notesEdit.getText().toString()); // Assuming you have a setter for notes

                            // Call the listener to update the item in the fragment
                            if (listener != null) {
                                listener.onItemUpdated(item, gwt, swt, nwt);
                            }
                        }
                        // Close the dialog
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
