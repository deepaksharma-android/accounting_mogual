package com.berylsystems.buzz.adapters;


import android.app.Activity;
import android.app.AlertDialog;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.berylsystems.buzz.R;
import com.berylsystems.buzz.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase.EditPurchaseActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.EditPurchaseReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.EditSaleActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.GetSaleListActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasedeposit.BankCaseDepositListActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasedeposit.CreateBankCaseDepositActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasewithdraw.BankCaseWithdrawActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasewithdraw.CreateBankCaseWithdrawActivity;
import com.berylsystems.buzz.activities.company.transaction.creditnotewoitem.CreateCreditNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transaction.creditnotewoitem.CreditNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transaction.debitnotewoitem.CreateDebitNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transaction.debitnotewoitem.DebitNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transaction.expence.CreateExpenceActivity;
import com.berylsystems.buzz.activities.company.transaction.expence.ExpenceActivity;
import com.berylsystems.buzz.activities.company.transaction.income.CreateIncomeActivity;
import com.berylsystems.buzz.activities.company.transaction.income.IncomeActivity;
import com.berylsystems.buzz.activities.company.transaction.journalvoucher.CreateJournalVoucherActivity;
import com.berylsystems.buzz.activities.company.transaction.journalvoucher.JournalVoucherActivity;
import com.berylsystems.buzz.activities.company.transaction.payment.CreatePaymentActivity;
import com.berylsystems.buzz.activities.company.transaction.payment.PaymentActivity;
import com.berylsystems.buzz.activities.company.transaction.receiptvoucher.CreateReceiptVoucherActivity;
import com.berylsystems.buzz.activities.company.transaction.receiptvoucher.ReceiptVoucherActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.EditSaleReturnActivity;
import com.berylsystems.buzz.activities.dashboard.MasterDashboardActivity;
import com.berylsystems.buzz.activities.dashboard.TransactionDashboardActivity;

import com.berylsystems.buzz.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.berylsystems.buzz.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.sale.CreateSaleActivity;
import com.berylsystems.buzz.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasedeposit.CreateBankCaseDepositActivity;
import com.berylsystems.buzz.activities.company.transaction.bankcasewithdraw.CreateBankCaseWithdrawActivity;
import com.berylsystems.buzz.activities.company.transaction.creditnotewoitem.CreateCreditNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transaction.debitnotewoitem.CreateDebitNoteWoItemActivity;
import com.berylsystems.buzz.activities.company.transaction.expence.CreateExpenceActivity;
import com.berylsystems.buzz.activities.company.transaction.income.CreateIncomeActivity;
import com.berylsystems.buzz.activities.company.transaction.journalvoucher.CreateJournalVoucherActivity;
import com.berylsystems.buzz.activities.company.transaction.payment.CreatePaymentActivity;
import com.berylsystems.buzz.activities.company.transaction.receiptvoucher.CreateReceiptVoucherActivity;

import com.berylsystems.buzz.entities.AppUser;
import com.berylsystems.buzz.utils.LocalRepositories;
import com.berylsystems.buzz.utils.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionDashboardAdapter extends RecyclerView.Adapter<TransactionDashboardAdapter.ViewHolder> {

    private String[] data;
    private Context context;
    int[] images;
    int[] color;
    AppUser appUser;

    public TransactionDashboardAdapter(Context context, String[] data, int[] images, int[] color) {
        this.data = data;
        this.context = context;
        this.images = images;
        this.color = color;
    }

    @Override
    public TransactionDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_generic_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionDashboardAdapter.ViewHolder viewHolder, int i) {
        appUser = LocalRepositories.getAppUser(context);
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mView.setBackgroundColor(color[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (i == 0) {
                    Preferences.getInstance(context).setVoucher_date("");
                    Preferences.getInstance(context).setVoucher_number("");
                    Preferences.getInstance(context).setStore("");
                    Preferences.getInstance(context).setParty_name("");
                    Preferences.getInstance(context).setMobile("");
                    Preferences.getInstance(context).setNarration("");
                    Preferences.getInstance(context).setSale_type_name("");
                    Preferences.getInstance(context).setCash_credit("");
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                appUser.mListMapForItemSale.clear();
                                appUser.mListMapForBillSale.clear();
                                LocalRepositories.saveAppUser(context, appUser);
                                context.startActivity(new Intent(context, CreateSaleActivity.class));

                            } else if (item == 1) {
                                context.startActivity(new Intent(context, EditSaleActivity.class));
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 1) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateReceiptVoucherActivity.class);
                                j.putExtra("fromReceipt", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, ReceiptVoucherActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 2) {
                    Preferences.getInstance(context).setVoucher_date("");
                    Preferences.getInstance(context).setVoucher_number("");
                    Preferences.getInstance(context).setStore("");
                    Preferences.getInstance(context).setParty_name("");
                    Preferences.getInstance(context).setMobile("");
                    Preferences.getInstance(context).setNarration("");
                    Preferences.getInstance(context).setCash_credit("");
                    Preferences.getInstance(context).setPurchase_type_name("");
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                appUser.mListMapForItemPurchase.clear();
                                appUser.mListMapForBillPurchase.clear();
                                LocalRepositories.saveAppUser(context, appUser);
                                context.startActivity(new Intent(context, CreatePurchaseActivity.class));

                            } else if (item == 1) {
                                context.startActivity(new Intent(context, EditPurchaseActivity.class));
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 3) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreatePaymentActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, PaymentActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 4) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateBankCaseDepositActivity.class);
                                j.putExtra("fromBankCashDeposit", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, BankCaseDepositListActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 5) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateBankCaseWithdrawActivity.class);
                                j.putExtra("fromBankCashWithdraw", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, BankCaseWithdrawActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 6) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateIncomeActivity.class);
                                j.putExtra("fromIncome", false);
                                context.startActivity(j);
                            } else if (item == 1) {
                                Intent j = new Intent(context, IncomeActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 7) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateExpenceActivity.class);
                                j.putExtra("fromExpense", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, ExpenceActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 8) {
                    Preferences.getInstance(context).setVoucher_date("");
                    Preferences.getInstance(context).setVoucher_number("");
                    Preferences.getInstance(context).setStore("");
                    Preferences.getInstance(context).setParty_name("");
                    Preferences.getInstance(context).setMobile("");
                    Preferences.getInstance(context).setNarration("");
                    Preferences.getInstance(context).setCash_credit("");
                    Preferences.getInstance(context).setSale_type_name("");
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                appUser.mListMapForItemSaleReturn.clear();
                                appUser.mListMapForBillSaleReturn.clear();
                                LocalRepositories.saveAppUser(context, appUser);
                                context.startActivity(new Intent(context, CreateSaleReturnActivity.class));

                            } else if (item == 1) {
                                context.startActivity(new Intent(context, EditSaleReturnActivity.class));
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 9) {
                    Preferences.getInstance(context).setVoucher_date("");
                    Preferences.getInstance(context).setVoucher_number("");
                    Preferences.getInstance(context).setStore("");
                    Preferences.getInstance(context).setParty_name("");
                    Preferences.getInstance(context).setMobile("");
                    Preferences.getInstance(context).setNarration("");
                    Preferences.getInstance(context).setCash_credit("");
                    Preferences.getInstance(context).setPurchase_return_type_name("");
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                appUser.mListMapForItemPurchaseReturn.clear();
                                appUser.mListMapForBillPurchaseReturn.clear();
                                LocalRepositories.saveAppUser(context, appUser);
                                context.startActivity(new Intent(context, CreatePurchaseReturnActivity.class));

                            } else if (item == 1) {
                                context.startActivity(new Intent(context, EditPurchaseReturnActivity.class));
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 10) {

                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateJournalVoucherActivity.class);
                                j.putExtra("fromJournalVoucher", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, JournalVoucherActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 11) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateDebitNoteWoItemActivity.class);
                                j.putExtra("fromDebitNote", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, DebitNoteWoItemActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
                if (i == 12) {
                    final CharSequence[] items = {"Add", "Modify"};
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setItems(items, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int item) {
                            if (item == 0) {
                                Intent j = new Intent(context, CreateCreditNoteWoItemActivity.class);
                                j.putExtra("fromCreditNote", false);
                                context.startActivity(j);

                            } else if (item == 1) {
                                Intent j = new Intent(context, CreditNoteWoItemActivity.class);
                                j.putExtra("fromPayment", false);
                                context.startActivity(j);
                            }
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
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
        @Bind(R.id.viewcolor)
        View mView;
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