// Generated by view binder compiler. Do not edit!
package com.loyalstring.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.loyalstring.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ProductitemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView bbarno;

  @NonNull
  public final TextView bitemno;

  @NonNull
  public final TextView bsno;

  private ProductitemBinding(@NonNull LinearLayout rootView, @NonNull TextView bbarno,
      @NonNull TextView bitemno, @NonNull TextView bsno) {
    this.rootView = rootView;
    this.bbarno = bbarno;
    this.bitemno = bitemno;
    this.bsno = bsno;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ProductitemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ProductitemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.productitem, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ProductitemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.bbarno;
      TextView bbarno = ViewBindings.findChildViewById(rootView, id);
      if (bbarno == null) {
        break missingId;
      }

      id = R.id.bitemno;
      TextView bitemno = ViewBindings.findChildViewById(rootView, id);
      if (bitemno == null) {
        break missingId;
      }

      id = R.id.bsno;
      TextView bsno = ViewBindings.findChildViewById(rootView, id);
      if (bsno == null) {
        break missingId;
      }

      return new ProductitemBinding((LinearLayout) rootView, bbarno, bitemno, bsno);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
