package com.loyalstring.fsupporters;

import com.rscja.barcode.BarcodeDecoder;
import com.rscja.barcode.BarcodeFactory;
import com.rscja.deviceapi.RFIDWithUHFUART;

public class ReaderSingleton {
    private static RFIDWithUHFUART mReader1;
    private static BarcodeDecoder barcodeDecoder1 = BarcodeFactory.getInstance().getBarcodeDecoder();


    public static RFIDWithUHFUART getReader() {
        return mReader1;
    }

    public static BarcodeDecoder getbarcode(){
        return barcodeDecoder1;
    }

    public static void setReader(RFIDWithUHFUART reader) {
        mReader1 = reader;
    }

    public static void setbarcode(BarcodeDecoder bar){
        barcodeDecoder1 = bar;
    }
}