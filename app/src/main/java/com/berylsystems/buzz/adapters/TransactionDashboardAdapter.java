package com.berylsystems.buzz.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.purchase.CreatePurchaseActivity;
import com.berylsystems.buzz.activities.company.purchase_return.CreatePurchaseReturnActivity;
import com.berylsystems.buzz.activities.company.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.sale.GetSaleListActivity;
import com.berylsystems.buzz.activities.company.sale_return.CreateSaleReturnActivity;
import com.berylsystems.buzz.activities.company.transection.bankcasedeposit.BankCaseDepositListActivity;
import com.berylsystems.buzz.activities.company.transection.bankcasedeposit.CreateBankCaseDepositActivity;
import com.berylsystems.buzz.activities.company.transection.bankcasewithdraw.BankCaseWithdrawActivity;
import com.berylsystems.buzz.activities.company.transection.bankcasewithdraw.CreateBankCaseWithdrawActivity;
import com.berylsystems.buzz.activities.company.transection.creditnotewoitem.CreateCreditNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transection.debitnotewoitem.CreateDebitNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transection.expence.CreateExpenceActivity;
import com.berylsystems.buzz.activities.company.transection.income.CreateIncomeActivity;
import com.berylsystems.buzz.activities.company.transection.journalvoucher.CreateJournalVoucherActivity;
import com.berylsystems.buzz.activities.company.transection.payment.CreatePaymentActivity;
import com.berylsystems.buzz.activities.company.transection.receiptvoucher.CreateReceiptVoucherActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;
import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.networks.api_response.purchase_return.CreatePurchaseReturnResponse;
import com.berylsystems.buzz.utils.LocalRepositories;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionDashboardAdapter extends RecyclerView.Adapter<TransactionDashboardAdapter.ViewHolder> {

    private String[] data;
    private Context context;
    int[] images;
    AppUser appUser;

    public TransactionDashboardAdapter(Context context, String[] data, int[] images) {
        this.data = data;
        this.context = context;
        this.images = images;
    }

    @Override
    public TransactionDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_generic_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionDashboardAdapter.ViewHolder viewHolder, int i) {
        appUser= LocalRepositories.getAppUser(context);
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* if(i==0){
                    Dialog dialogbal = new Dialog(context);
                    dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
                    dialogbal.setContentView(R.layout.dialog_operation);
                    dialogbal.setCancelable(true);
                    LinearLayout add=(LinearLayout)dialogbal.findViewById(R.id.add_layout);
                    LinearLayout modify=(LinearLayout)dialogbal.findViewById(R.id.modify_layout);
                    add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(context,CreateSaleActivity.class));
                        }
                    });
                    modify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            context.startActivity(new Intent(context,GetSaleListActivity.class));
                        }
                    });*/
                //operationDialog(CreateSaleActivity.class,CreateSaleActivity.class);

                if (i == 0) {
                    appUser.mListMapForItemSale.clear();
                    appUser.mListMapForBillSale.clear();
                    LocalRepositories.saveAppUser(context,appUser);
                    context.startActivity(new Intent(context, CreateSaleActivity.class));

                }
                if (i == 1) {
                    context.startActivity(new Intent(context, CreatePurchaseActivity.class));
                }
                if (i == 2) {
                    context.startActivity(new Intent(context, CreateSaleReturnActivity.class));
                }
                if (i == 3) {
                    context.startActivity(new Intent(context, CreatePurchaseReturnActivity.class));
                }
                if (i == 4) {
                    Intent j=new Intent(context,CreatePaymentActivity.class);
                    j.putExtra("fromPayment",false);
                    context.startActivity(j);
                }
                if (i == 5) {
                    Intent j=new Intent(context,CreateReceiptVoucherActivity.class);
                    j.putExtra("fromReceipt",false);
                    context.startActivity(j);
                }
                if (i == 6) {
                    Intent j=new Intent(context,CreateBankCaseDepositActivity.class);
                    j.putExtra("fromBankCashDeposit",false);
                    context.startActivity(j);
                }
                if (i == 7) {
                    Intent j=new Intent(context,CreateBankCaseWithdrawActivity.class);
                    j.putExtra("fromBankCashWithdraw",false);
                    context.startActivity(j);
                }
                if (i == 8) {
                    Intent j=new Intent(context,CreateIncomeActivity.class);
                    j.putExtra("fromIncome",false);
                    context.startActivity(j);
                   // context.startActivity(new Intent(context, CreateIncomeActivity.class));

                }
                if (i == 9) {
                    Intent j=new Intent(context,CreateExpenceActivity.class);
                    j.putExtra("fromExpense",false);
                    context.startActivity(j);
                }
                if (i == 10) {
                    Intent j=new Intent(context,CreateJournalVoucherActivity.class);
                    j.putExtra("fromJournalVoucher",false);
                    context.startActivity(j);
                }
                if (i == 11) {
                    Intent j=new Intent(context,CreateDebitNoteWoItemActivity.class);
                    j.putExtra("fromDebitNote",false);
                    context.startActivity(j);
                }
                if (i == 12) {
                    Intent j=new Intent(context,CreateCreditNoteWoItemActivity.class);
                    j.putExtra("fromCreditNote",false);
                    context.startActivity(j);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.title)
        TextView mTitleText;
        @Bind(R.id.imageicon)
        ImageView mImage;
        @Bind(R.id.grid_layout)
        LinearLayout mGridLayout;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, itemView);

        }
    }

    public void operationDialog(final Class<?> ActivityToadd, final Class<?> Activitytomodify) {
        Dialog dialogbal = new Dialog(context);
        dialogbal.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialogbal.setContentView(R.layout.dialog_operation);
        dialogbal.setCancelable(true);
        LinearLayout add = (LinearLayout) dialogbal.findViewById(R.id.add_layout);
        LinearLayout modify = (LinearLayout) dialogbal.findViewById(R.id.modify_layout);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ActivityToadd.getClass()));
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, Activitytomodify.getClass()));
            }
        });
        dialogbal.show();
    }
}