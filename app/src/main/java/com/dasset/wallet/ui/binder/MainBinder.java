package com.dasset.wallet.ui.binder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import com.dasset.wallet.R;
import com.dasset.wallet.base.sticky.binder.BaseViewBinder;
import com.dasset.wallet.model.AccountInfo;
import com.dasset.wallet.ui.holder.AddAccountHolder;
import com.dasset.wallet.ui.holder.MainHolder;

public class MainBinder extends BaseViewBinder {

    private Context context;
    private RecyclerView recyclerView;

    public MainBinder(Context context, RecyclerView recyclerView) {
        this.context = context;
        this.recyclerView = recyclerView;
    }

    @Override
    public void bind(RecyclerView.ViewHolder viewHolder, Object object, int position, boolean checkable) {
        if (viewHolder instanceof MainHolder) {
            MainHolder mainHolder = (MainHolder) viewHolder;
            AccountInfo accountInfo = (AccountInfo) object;
            mainHolder.tvAddress.setText(accountInfo.getAddress());
            mainHolder.tvSerialNumber.setText(String.format("账户%s", accountInfo.getSerialNumber()));
        } else if (viewHolder instanceof AddAccountHolder) {
            //TODO
        }
    }

    @Override
    public RecyclerView.ViewHolder getViewHolder(int type) {
        return new MainHolder(LayoutInflater.from(context).inflate(R.layout.holder_account, recyclerView, false));
    }
}
