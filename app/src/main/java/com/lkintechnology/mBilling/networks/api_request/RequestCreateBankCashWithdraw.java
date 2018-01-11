package com.lkintechnology.mBilling.networks.api_request;

import android.content.Context;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateBankCashWithdraw {

    public Map bank_cash_withdraw;

    public RequestCreateBankCashWithdraw(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        bank_cash_withdraw = new HashMap<>();

        bank_cash_withdraw.put("company_id", Preferences.getInstance(context).getCid());
        bank_cash_withdraw.put("voucher_series", appUser.bank_cash_withdraw_voucher_series);
        bank_cash_withdraw.put("date", appUser.bank_cash_withdraw_date);
        bank_cash_withdraw.put("voucher_number", appUser.bank_cash_withdraw_voucher_no);
        bank_cash_withdraw.put("withdraw_from_id", appUser.withdraw_from_id);
        bank_cash_withdraw.put("withdraw_by_id", appUser.withdraw_by_id);
        bank_cash_withdraw.put("amount", appUser.bank_cash_withdraw_amount);
        bank_cash_withdraw.put("narration", appUser.bank_cash_withdraw_narration);
        bank_cash_withdraw.put("attachment", appUser.bank_cash_withdraw_attachment);
    }
}