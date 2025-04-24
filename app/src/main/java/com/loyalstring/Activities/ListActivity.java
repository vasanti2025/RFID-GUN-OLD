package com.loyalstring.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.WindowManager;

import com.loyalstring.Adapters.Listadapter;
import com.loyalstring.R;
import com.loyalstring.databinding.ActivityListBinding;
import com.loyalstring.fsupporters.MyApplication;
import com.loyalstring.modelclasses.Itemmodel;

import java.util.HashMap;
import java.util.Map;

public class ListActivity extends AppCompatActivity implements Listadapter.EditClickListener, Listadapter.DeleteClickListener {

    ActivityListBinding b;
    Listadapter listadapter;
    private Map<String, Itemmodel> searchlist = new HashMap<>();

    private Map<String, Itemmodel> alllsit = new HashMap<>();
    MyApplication myapp;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityListBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        b.listrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true));
        myapp = (MyApplication) getApplicationContext();

        listadapter = new Listadapter(searchlist, ListActivity.this, ListActivity.this, ListActivity.this);
        b.listrecycler.setAdapter(listadapter);

        if (!myapp.isCountMatch()) {
            // Show progress dialog
            ProgressDialog progressDialog = new ProgressDialog(ListActivity.this);
            progressDialog.setMessage("Loading data ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Runnable onCountMatched = new Runnable() {
                @Override
                public void run() {
                    // Dismiss the progress dialog
                    progressDialog.dismiss();
                    alllsit = myapp.getInventoryMap();
                    searchlist.putAll(alllsit);
                    listadapter.notifyDataSetChanged();
//                    additems();
                }
            };
            new Thread(new Runnable() {
                @Override
                public void run() {
                    // Check for count match
                    while (!myapp.isCountMatch()) {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    ListActivity.this.runOnUiThread(onCountMatched);
                }
            }).start();
        } else {
            alllsit = myapp.getInventoryMap();
            searchlist.putAll(alllsit);
            listadapter.notifyDataSetChanged();
//            additems();
        }



        b.searchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                if (charSequence.toString().isEmpty()) {
                    searchlist.clear();
                    searchlist.putAll(alllsit);
                } else {
                    searchlist.clear();
                    for (Map.Entry<String, Itemmodel> entry : alllsit.entrySet()) {
                        Itemmodel m = entry.getValue();
                        if (m.getProduct().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || m.getBarCode().toLowerCase().contains(charSequence.toString().toLowerCase())
                                || m.getItemCode().toLowerCase().contains(charSequence.toString().toLowerCase())) {
                            searchlist.put(entry.getKey(), m);
                        }
                    }
                }

                listadapter.notifyDataSetChanged();


            }

            @Override
            public void afterTextChanged(Editable editable) {

//                pastcode = editable.toString();

            }
        });


    }

    @Override
    public void onEditClick(int position, String tidValue, Itemmodel item, Map<String, Itemmodel> itemList) {

        Intent go = new Intent(ListActivity.this, Uploadimages.class);
        go.putExtra("itemModel", item);
        startActivity(go);



    }

    @Override
    public void onDeleteClick(int position, String tidValue, Itemmodel item, Map<String, Itemmodel> itemList) {

    }
}