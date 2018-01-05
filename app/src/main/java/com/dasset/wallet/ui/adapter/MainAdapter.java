package com.dasset.wallet.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dasset.wallet.R;
import com.dasset.wallet.base.sticky.adapter.FixedStickyHeaderAdapter;
import com.dasset.wallet.base.sticky.binder.BaseViewBinder;
import com.dasset.wallet.components.utils.ViewUtil;
import com.dasset.wallet.model.AccountInfo;
import com.dasset.wallet.ui.holder.MainHolder;

public class MainAdapter extends FixedStickyHeaderAdapter<AccountInfo, MainHolder> {

    public MainAdapter(Context ctx, BaseViewBinder binder, boolean groupable) {
        super(ctx, binder, groupable);
    }

    @Override
    protected void onBindHeaderOrFooter(RecyclerView.ViewHolder holder, Object object) {
        super.onBindHeaderOrFooter(holder, object);
        FixedStickyView view = (FixedStickyView) object;
        if (view.fixedStickyViewType == R.layout.holder_add_account) {
            ViewUtil.getInstance().findViewAttachOnclick(holder.itemView, R.id.llAddAccount, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEventClickListener != null) {
                        onEventClickListener.onnEventClick();
                    }
                }
            });
        }
    }
}
