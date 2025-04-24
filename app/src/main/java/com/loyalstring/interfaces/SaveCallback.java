package com.loyalstring.interfaces;

import com.loyalstring.modelclasses.Itemmodel;

import java.util.List;

public interface SaveCallback {

    void onSaveSuccess();

    void onSaveFailure(List<Itemmodel> failedItems);



}
