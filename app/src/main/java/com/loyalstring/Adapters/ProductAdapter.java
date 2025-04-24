package com.loyalstring.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.loyalstring.R;
import com.loyalstring.modelclasses.Itemmodel;
import com.rscja.barcode.BarcodeDecoder;
import com.rscja.deviceapi.entity.BarcodeEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.Viewholder> {
    private List<Itemmodel> mitemlist = new ArrayList<>();
    private BarcodeDecoder mbarcodereader;
    private Context context;


    public ProductAdapter(List<Itemmodel> itemList, BarcodeDecoder barcodeDecoder, Context context) {
        this.mitemlist = itemList;
        this.mbarcodereader = barcodeDecoder;
        this.context = context;
    }

    @NonNull
    @Override
    public ProductAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.productitem, parent, false);
        return new Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.Viewholder holder, int position) {
        Itemmodel item = mitemlist.get(position);
        holder.bsno.setText(String.valueOf(holder.getAdapterPosition() + 1));
        holder.bbarno.setText(item.getBarCode());
        holder.bitemno.setText(item.getItemCode());

        holder.bbarno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Itemmodel item = mitemlist.get(holder.getAdapterPosition());
                if (item.getBarCode().matches("")) {
                    showEditDialogf(holder.bbarno, item, "br");
                }
            }
        });

        holder.bitemno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Itemmodel item = mitemlist.get(holder.getAdapterPosition());
                if (item.getItemCode().matches("")) {
                    showEditDialogf(holder.bitemno, item, "it");
                }

            }
        });
        holder.bbarno.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Itemmodel item = mitemlist.get(holder.getAdapterPosition());
                if (!item.getBarCode().matches("")) {
                    showEditDialogf(holder.bbarno, item, "br");
                }
                return true;
            }
        });
        holder.bitemno.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                Itemmodel item = mitemlist.get(holder.getAdapterPosition());
                if (!item.getItemCode().matches("")) {
                    showEditDialogf(holder.bitemno, item, "it");
                }
                return true;
            }
        });


    }

    @Override
    public int getItemCount() {
        return mitemlist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        public TextView bsno;
        public TextView bbarno;
        public TextView bitemno;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            bsno = itemView.findViewById(R.id.bsno);
            bbarno = itemView.findViewById(R.id.bbarno);
            bitemno = itemView.findViewById(R.id.bitemno);

        }

    }


    private void showEditDialogf(TextView bbarno, Itemmodel item, String s) {
        Log.e("check barcode",""+ mbarcodereader);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Tag already exists");

// Set a custom layout for the dialog
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_layout, null);
        builder.setView(dialogView);

// Find the EditText in the dialog layout
        EditText editText = dialogView.findViewById(R.id.editText);
        TextView textView = dialogView.findViewById(R.id.dialogtext);
        ImageView scanner = dialogView.findViewById(R.id.bscanner);

        scanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mbarcodereader.startScan();
//                    showtoast("okay");
            }
        });

        mbarcodereader.setDecodeCallback(new BarcodeDecoder.DecodeCallback() {
            @Override
            public void onDecodeComplete(BarcodeEntity barcodeEntity) {
                Log.e("TAG", "BarcodeDecoder==========================:" + barcodeEntity.getResultCode());
                if (barcodeEntity.getResultCode() == BarcodeDecoder.DECODE_SUCCESS) {
                    editText.setText(barcodeEntity.getBarcodeData());
                    Log.e("TAG", "data==========================:" + barcodeEntity.getBarcodeData());
                } else {
                    editText.setText("");
//                        showtoast("Failed to read bar code");
                }
            }
        });

        if (s.matches("br")) {
            textView.setText("Enter Barcode No");
            mbarcodereader.startScan();
        } else {
            textView.setText("Enter Itemcode No");
            scanner.setVisibility(View.GONE);
            editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        }


        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (editText.getText().toString().matches("")) {
//                        showtoast("Please enter or scan bar code");
                    return;
                }
                if (s.matches("br")) {
                    bbarno.setText(editText.getText().toString());
                    item.setBarCode(editText.getText().toString());

                    /*for (int j = 0; j < onlinelist.size(); j++) {
                        DataItem a = onlinelist.get(j);
                        if (a.getBarcodeNumber() != null) {
                            if (onlinelist.get(j).getBarcodeNumber().toString().toLowerCase(Locale.ROOT).matches(editText.getText().toString().toLowerCase(Locale.ROOT))) {
                                if (a.getItemCode() != null) {
                                    item.setItemCode(a.getItemCode());
                                }
                                item.setCategory(a.getCategoryName());
                                item.setProduct(a.getProductName());
                                item.setPurity(a.getPurity());
                                item.setBox(a.getTblBox().getBoxName());
                                item.setGrossWeight(a.getGrosswt());
                                item.setNetWeight(a.getNetWt());
                                item.setStoneweight(Double.parseDouble(a.getStoneWeight()));


                            }
                        }

                    }*/
//                        ebarcode.setText(editText.getText().toString());
                } else {
                    bbarno.setText(editText.getText().toString());
                    item.setItemCode(editText.getText().toString());

                    /*for (int j = 0; j < onlinelist.size(); j++) {
                        DataItem a = onlinelist.get(j);
                        if (a.getItemCode() != null) {
                            if (onlinelist.get(j).getItemCode().toString().toLowerCase(Locale.ROOT).matches(editText.getText().toString().toLowerCase(Locale.ROOT))) {
                                if (a.getBarcodeNumber() != null) {
                                    item.setBarcodeNumber(a.getBarcodeNumber());
                                }
                                item.setCategory(a.getCategoryName());
                                item.setProduct(a.getProductName());
                                item.setPurity(a.getPurity());
                                item.setBox(a.getTblBox().getBoxName());
                                item.setGrossWeight(a.getGrosswt());
                                item.setNetWeight(a.getNetWt());
                                item.setStoneweight(Double.parseDouble(a.getStoneWeight()));


                            }
                        }

                    }*/


//                        eitemcode.setText(editText.getText().toString());
                }

            }
        });
        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });


        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.setCanceledOnTouchOutside(false);
    }
}
