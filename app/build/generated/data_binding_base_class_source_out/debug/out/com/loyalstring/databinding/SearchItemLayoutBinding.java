// Generated by view binder compiler. Do not edit!
package com.loyalstring.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.loyalstring.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class SearchItemLayoutBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView barcode;

  @NonNull
  public final TextView itemcode;

  @NonNull
  public final TextView percent;

  @NonNull
  public final ProgressBar progressBar;

  @NonNull
  public final TextView sno;

  private SearchItemLayoutBinding(@NonNull LinearLayout rootView, @NonNull TextView barcode,
      @NonNull TextView itemcode, @NonNull TextView percent, @NonNull ProgressBar progressBar,
      @NonNull TextView sno) {
    this.rootView = rootView;
    this.barcode = barcode;
    this.itemcode = itemcode;
    this.percent = percent;
    this.progressBar = progressBar;
    this.sno = sno;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static SearchItemLayoutBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static SearchItemLayoutBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.search_item_layout, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static SearchItemLayoutBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.barcode;
      TextView barcode = ViewBindings.findChildViewById(rootView, id);
      if (barcode == null) {
        break missingId;
      }

      id = R.id.itemcode;
      TextView itemcode = ViewBindings.findChildViewById(rootView, id);
      if (itemcode == null) {
        break missingId;
      }

      id = R.id.percent;
      TextView percent = ViewBindings.findChildViewById(rootView, id);
      if (percent == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = ViewBindings.findChildViewById(rootView, id);
      if (progressBar == null) {
        break missingId;
      }

      id = R.id.sno;
      TextView sno = ViewBindings.findChildViewById(rootView, id);
      if (sno == null) {
        break missingId;
      }

      return new SearchItemLayoutBinding((LinearLayout) rootView, barcode, itemcode, percent,
          progressBar, sno);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
