package com.dasset.wallet.ui.activity.presenter;

import android.app.Activity;
import android.content.Context;
import android.os.Message;

import com.dasset.wallet.R;
import com.dasset.wallet.base.handler.ActivityHandler;
import com.dasset.wallet.components.utils.BundleUtil;
import com.dasset.wallet.components.utils.MessageUtil;
import com.dasset.wallet.components.utils.ThreadPoolUtil;
import com.dasset.wallet.constant.Constant;
import com.dasset.wallet.core.ecc.Account;
import com.dasset.wallet.core.exception.PasswordException;
import com.dasset.wallet.ecc.AccountStorageFactory;
import com.dasset.wallet.ui.BasePresenterImplement;
import com.dasset.wallet.ui.activity.CreateAccountResultActivity;
import com.dasset.wallet.ui.activity.contract.CreateAccountResultContract;

import java.io.IOException;

public class CreateAccountResultPresenter extends BasePresenterImplement implements CreateAccountResultContract.Presenter {

    private CreateAccountResultContract.View view;
    private CreateAccountResultHandler createAccountResultHandler;
    private Account account;

    private class CreateAccountResultHandler extends ActivityHandler<CreateAccountResultActivity> {

        public CreateAccountResultHandler(CreateAccountResultActivity activity) {
            super(activity);
        }

        @Override
        protected void handleMessage(CreateAccountResultActivity activity, Message msg) {
            if (activity != null) {
                switch (msg.what) {
                    case Constant.StateCode.EXPORT_ACCOUNT_SUCCESS:
                        activity.hideLoadingPromptDialog();
                        activity.showPromptDialog(msg.obj.toString(), false, false, Constant.RequestCode.DIALOG_PROMPT_EXPORT_ACCOUNT_SUCCESS);
                        break;
                    case Constant.StateCode.EXPORT_ACCOUNT_FAILED:
                        activity.hideLoadingPromptDialog();
                        activity.showPromptDialog(msg.obj.toString(), false, false, Constant.RequestCode.DIALOG_PROMPT_EXPORT_ACCOUNT_FAILED);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public Account getAccount() {
        return account;
    }

    public CreateAccountResultPresenter(Context context, CreateAccountResultContract.View view) {
        this.context = context;
        this.view = view;
    }

    @Override
    public void initialize() {
        super.initialize();
        createAccountResultHandler = new CreateAccountResultHandler((CreateAccountResultActivity) view);
        account = BundleUtil.getInstance().getParcelableData((Activity) context, Constant.BundleKey.WALLET_ACCOUNT);
    }

    @Override
    public void exportAccount() {
        ThreadPoolUtil.execute(new Runnable() {
            @Override
            public void run() {
                if (account != null) {
                    try {
                        AccountStorageFactory.getInstance().exportAccount(account.getAddress(), account.getPassword());
                        createAccountResultHandler.sendMessage(MessageUtil.getMessage(Constant.StateCode.EXPORT_ACCOUNT_SUCCESS, String.format("备份文件已存储在：%s", AccountStorageFactory.getInstance().getBackupFileDirectory().getAbsolutePath())));
                    } catch (PasswordException | IOException e) {
                        createAccountResultHandler.sendMessage(MessageUtil.getErrorMessage(Constant.StateCode.EXPORT_ACCOUNT_FAILED, e, context.getString(R.string.dialog_prompt_unknow_error)));
                    }
                } else {
                    createAccountResultHandler.sendMessage(MessageUtil.getMessage(Constant.StateCode.EXPORT_ACCOUNT_FAILED, context.getString(R.string.dialog_prompt_account_info_error)));
                }
            }
        });
    }
}
