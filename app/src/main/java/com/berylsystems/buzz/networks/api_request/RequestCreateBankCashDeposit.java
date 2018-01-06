package com.berylsystems.buzz.networks.api_request;

import android.content.Context;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import java.util.HashMap;
import java.util.Map;

public class RequestCreateBankCashDeposit {
    public Map bank_cash_deposit;

    public RequestCreateBankCashDeposit(Context context) {
        AppUser appUser = LocalRepositories.getAppUser(context);

        bank_cash_deposit =new HashMap<>();

        bank_cash_deposit.put("company_id", Preferences.getInstance(context).getCid());
        bank_cash_deposit.put("voucher_series",appUser.bank_cash_deposit_voucher_series);
        bank_cash_deposit.put("date",appUser.bank_cash_deposit_date);
        bank_cash_deposit.put("voucher_number",appUser.bank_cash_deposit_voucher_no);
        bank_cash_deposit.put("deposit_to_id",appUser.deposit_to_id);
        bank_cash_deposit.put("deposit_by_id",appUser.deposit_by_id);
        bank_cash_deposit.put("amount",appUser.bank_cash_deposit_amount);
        bank_cash_deposit.put("narration",appUser.bank_cash_deposit_narration);
        bank_cash_deposit.put("attachment",appUser.bank_cash_deposit_attachment);
        bank_cash_deposit.put("send_email",appUser.email_yes_no);
    }
}