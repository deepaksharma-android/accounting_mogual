package com.lkintechnology.mBilling.activities.company.navigations.dashboard;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.BaseActivityCompany;
import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.transaction.PurchaseVouchersItemDetailsListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.SaleVouchersItemDetailsListActivity;
import com.lkintechnology.mBilling.activities.company.transaction.bankcasedeposit.CreateBankCaseDepositActivity;
import com.lkintechnology.mBilling.activities.company.transaction.bankcasewithdraw.CreateBankCaseWithdrawActivity;
import com.lkintechnology.mBilling.activities.company.transaction.creditnotewoitem.CreateCreditNoteWoActivity;
import com.lkintechnology.mBilling.activities.company.transaction.debitnotewoitem.CreateDebitNoteWoItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.expence.CreateExpenceActivity;
import com.lkintechnology.mBilling.activities.company.transaction.income.CreateIncomeActivity;
import com.lkintechnology.mBilling.activities.company.transaction.journalvoucher.CreateJournalVoucherActivity;
import com.lkintechnology.mBilling.activities.company.transaction.payment.CreatePaymentActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.CreatePurchaseActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase.PurchaseAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.purchase_return.CreatePurchaseReturnActivity;
import com.lkintechnology.mBilling.activities.company.transaction.receiptvoucher.CreateReceiptVoucherActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale.CreateSaleActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.CreateSaleReturnActivity;
import com.lkintechnology.mBilling.activities.company.transaction.sale_return.SaleReturnAddItemActivity;
import com.lkintechnology.mBilling.activities.company.transaction.stocktransfer.CreateStockTransferActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_request.RequestCheckBarcode;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.Preferences;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TransactionDashboardActivity extends BaseActivityCompany {

    @Bind(R.id.layout_sale)
    LinearLayout sale;
    @Bind(R.id.layout_receipt)
    LinearLayout receipt;
    @Bind(R.id.layout_purchase)
    LinearLayout purchase;
    @Bind(R.id.layout_payment)
    LinearLayout payment;
    @Bind(R.id.layout_bank_cash_deposit)
    LinearLayout bankCashDeposit;
    @Bind(R.id.layout_bank_cash_withdraw)
    LinearLayout bankCashWithdraw;
    @Bind(R.id.layout_income)
    LinearLayout income;
    @Bind(R.id.layout_expense)
    LinearLayout expense;
    @Bind(R.id.layout_sale_return)
    LinearLayout saleReturn;
    @Bind(R.id.layout_purchase_return)
    LinearLayout purchaseReturn;
    @Bind(R.id.layout_journal_voucher)
    LinearLayout journalVoucher;
    @Bind(R.id.layout_debit_note)
    LinearLayout debitNote;
    @Bind(R.id.layout_credit_note)
    LinearLayout creditNote;
    @Bind(R.id.layout_stock_transfer)
    LinearLayout stockTransfer;
    public static Bitmap bitmapPhoto = null;

    Context context;
    AppUser appUser;






   /* @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.recycler_view)
    RecyclerView mRecyclerView;
    AppUser appUser;
    RecyclerView.LayoutManager layoutManager;
    TransactionDashboardAdapter mAdapter;*/

    /*int[] myImageList = new int[]{R.drawable.transaction_sale, R.drawable.transaction_reciept,
            R.drawable.transaction_purchase, R.drawable.transaction_payment,
            R.drawable.transaction_payment, R.drawable.transaction_bank_cash_deposit,
            R.drawable.transaction_bank_cash_withdrwal,R.drawable.transaction_withdrawl,
            R.drawable.transaction_expence, R.drawable.transaction_sale_return,
            R.drawable.transaction_purchase_return, R.drawable.transaction_journal_voucher,
            R.drawable.transaction_debit_note, R.drawable.transaction_credit_note};
    private String[] title={
            "Sales",
            "Receipt",
            "Purchase",
            "Payment",
            "Bank Cash Deposit",
            "Bank Cash Withdraw ",
            "Income",
            "Expense",
            "Sale Return",
            "Purchase Return",
            "Journal Voucher",
            "Debit Note W/O Item",
            "Credit Note W/O Item"
    };*/

    //int[] viewcolor = new int[]{getResources().getColor(R.color.red),getResources().getColor(R.color.blue),getResources().getColor(R.color.green),getResources().getColor(R.color.yellow),getResources().getColor(R.color.orange),getResources().getColor(R.color.red),getResources().getColor(R.color.purple),getResources().getColor(R.color.bright_pink),getResources().getColor(R.color.light_blue),getResources().getColor(R.color.grey),getResources().getColor(R.color.brown),getResources().getColor(R.color.premiumcolor),getResources().getColor(R.color.splashText1)};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_transaction);
        ButterKnife.bind(this);

        context = TransactionDashboardActivity.this;
        SaleVouchersItemDetailsListActivity.isFromTransactionSaleActivity = false;
        PurchaseVouchersItemDetailsListActivity.isFromTransactionSaleActivity = false;
        appUser = LocalRepositories.getAppUser(context);
        appUser.transport_details.clear();
        appUser.sale_item_serial_arr.clear();
        appUser.purchase_item_serail_arr.clear();
        LocalRepositories.saveAppUser(getApplicationContext(), appUser);
        LocalRepositories.saveAppUser(this, appUser);
        setAddCompany(2);
        setAppBarTitleCompany(1, "TRANSACTION");
        Preferences.getInstance(getApplicationContext()).setUpdate("");
        // Preferences.getInstance(getApplicationContext()).setStoreId("");
        appUser.receipt_received_by_name = "";
        appUser.receipt_received_from_name = "";
        appUser.receipt_received_from_email = "";
        appUser.account_name_credit_note_email = "";
        appUser.account_name_debit_email = "";
        appUser.account_name_debit_note_email = "";
        appUser.payment_paid_from_name = "";
        appUser.payment_paid_to_name = "";
        appUser.payment_paid_to_email = "";
        appUser.deposit_by_name = "";
        appUser.deposit_to_name = "";
        appUser.withdraw_by_name = "";
        appUser.withdraw_from_name = "";
        appUser.received_from_name = "";
        appUser.received_into_name = "";
        appUser.paid_from_name = "";
        appUser.paid_to_name = "";
        appUser.account_name_debit_name = "";
        appUser.account_name_credit_name = "";
        appUser.sale_partyEmail = "";
        Preferences.getInstance(context).setVoucher_name("");
        Preferences.getInstance(context).setVoucher_id("");
        RequestCheckBarcode.bollForBarcode=null;
        Preferences.getInstance(getApplicationContext()).setVoucher_date("");
        LocalRepositories.saveAppUser(this, appUser);
        Preferences.getInstance(getApplicationContext()).setReason("");
        ParameterConstant.handleAutoCompleteTextView = 0;
 		appUser.paymentSettlementList.clear();
        appUser.paymentSettlementHashMap.clear();
        ParameterConstant.name = "";
        ParameterConstant.mobile = "";
        ParameterConstant.email = "";
        ParameterConstant.id = "";
      /*  appUser = LocalRepositories.getAppUser(this);
        TypedArray ta = getResources().obtainTypedArray(R.array.rainbow);
        int[] colors = new int[ta.length()];
        for (int i = 0; i < ta.length(); i++) {
            colors[i] = ta.getColor(i, 0);
        }
        ta.recycle();
        setAddCompany(2);
        setAppBarTitleCompany(1,"TRANSACTION");*/

       /* mRecyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new TransactionDashboardAdapter(this, title, myImageList,colors);
        mRecyclerView.setAdapter(mAdapter);*/

        sale();
        receipt();
        purchase();
        payment();
        bankCashDeposit();
        bankCashWithdraw();
        income();
        expense();
        saleReturn();
        purchaseReturn();
        journalVourcher();
        debitNote();
        creditNote();
        stockTransfer();

    }


    private void sale() {
        sale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            

                CreateSaleActivity.isForEdit = false;
                appUser.serial_arr.clear();
                Preferences.getInstance(context).setVoucher_date("");
                Preferences.getInstance(context).setVoucher_number("");
                //Preferences.getInstance(context).setStore("");
                Preferences.getInstance(context).setParty_name("");
                Preferences.getInstance(context).setShipped_to("");
                Preferences.getInstance(context).setShipped_to_id("");
                Preferences.getInstance(context).setMobile("");
                Preferences.getInstance(context).setNarration("");
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                //Preferences.getInstance(context).setSale_type_name("");
                Preferences.getInstance(context).setCash_credit("");
                //final CharSequence[] items = {"Add", "Modify"};
                appUser.mListMapForItemSale.clear();
                appUser.mListMapForBillSale.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(getApplicationContext(), CreateSaleActivity.class);
                intent.putExtra("fromsalelist", false);
                intent.putExtra("fromdashboard", true);
                startActivity(intent);
                //  context.startActivity(new Intent(context, CreateSaleActivity.class));
               /* AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void receipt() {
        receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(getApplicationContext(), CreateReceiptVoucherActivity.class);
                startActivity(j);
                /*final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Intent j = new Intent(getApplicationContext(), CreateReceiptVoucherActivity.class);
                            j.putExtra("fromReceipt", false);
                            startActivity(j);

                        } else if (item == 1) {
                            Intent j = new Intent(getApplicationContext(), ReceiptVoucherActivity.class);
                            j.putExtra("fromPayment", false);
                            startActivity(j);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });
    }


    private void purchase() {
        //final CharSequence[] items = {"Add", "Modify"};
        purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              

                CreatePurchaseActivity.isForEdit = false;
                appUser.serial_arr.clear();
                Preferences.getInstance(context).setVoucher_date("");
                Preferences.getInstance(context).setVoucher_number("");
//                Preferences.getInstance(context).setStore("");
                Preferences.getInstance(context).setParty_name("");
                Preferences.getInstance(context).setShipped_to("");
                Preferences.getInstance(context).setShipped_to_id("");
                Preferences.getInstance(context).setMobile("");
                Preferences.getInstance(context).setNarration("");
                Preferences.getInstance(context).setCash_credit("");
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
//                Preferences.getInstance(context).setPurchase_type_name("");
                appUser.mListMapForItemPurchase.clear();
                appUser.mListMapForBillPurchase.clear();
                LocalRepositories.saveAppUser(context, appUser);
                Intent intent = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
                intent.putExtra("fromsalelist", false);

                startActivity(intent);
                // context.startActivity(new Intent(context, CreatePurchaseActivity.class));
               /* final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void payment() {
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreatePaymentActivity.class);
                context.startActivity(j);
               /* final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void bankCashDeposit() {
        bankCashDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateBankCaseDepositActivity.class);
                // j.putExtra("fromBankCashDeposit", false);
                context.startActivity(j);

              /*  final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void bankCashWithdraw() {
        bankCashWithdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateBankCaseWithdrawActivity.class);
                context.startActivity(j);
               /* final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void income() {
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateIncomeActivity.class);
                context.startActivity(j);
                /*final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void expense() {
        expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateExpenceActivity.class);
                context.startActivity(j);
                /*final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void saleReturn() {
        saleReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                

                CreateSaleReturnActivity.isForEdit=false;
                Preferences.getInstance(context).setVoucher_date("");
                Preferences.getInstance(context).setVoucher_number("");
                //Preferences.getInstance(context).setStore("");
                Preferences.getInstance(context).setParty_name("");
                Preferences.getInstance(context).setShipped_to("");
                Preferences.getInstance(context).setShipped_to_id("");
                Preferences.getInstance(context).setMobile("");
                Preferences.getInstance(context).setNarration("");
                Preferences.getInstance(context).setCash_credit("");
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                //Preferences.getInstance(context).setSale_type_name("");

                appUser.mListMapForItemSaleReturn.clear();
                appUser.mListMapForBillSaleReturn.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(getApplicationContext(), CreateSaleReturnActivity.class);
                intent.putExtra("fromsalelist", false);
                intent.putExtra("fromdashboard", true);
                startActivity(intent);
                //context.startActivity(new Intent(context, CreateSaleReturnActivity.class));

              /*  final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void purchaseReturn() {
        purchaseReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               

                Preferences.getInstance(context).setVoucher_date("");
                Preferences.getInstance(context).setVoucher_number("");
//                Preferences.getInstance(context).setStore("");
                Preferences.getInstance(context).setParty_name("");
                Preferences.getInstance(context).setShipped_to("");
                Preferences.getInstance(context).setShipped_to_id("");
                Preferences.getInstance(context).setMobile("");
                Preferences.getInstance(context).setNarration("");
                Preferences.getInstance(context).setCash_credit("");
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
//                Preferences.getInstance(context).setPurchase_return_type_name("");

                appUser.mListMapForItemPurchaseReturn.clear();
                appUser.mListMapForBillPurchaseReturn.clear();
                LocalRepositories.saveAppUser(getApplicationContext(), appUser);
                Intent intent = new Intent(getApplicationContext(), CreatePurchaseReturnActivity.class);
                intent.putExtra("fromsalelist", false);
                intent.putExtra("fromdashboard", true);
                startActivity(intent);
                //  context.startActivity(new Intent(context, CreatePurchaseReturnActivity.class));
               /* final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void journalVourcher() {
        journalVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateJournalVoucherActivity.class);
                context.startActivity(j);
                /*final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void debitNote() {
        debitNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateDebitNoteWoItemActivity.class);
                context.startActivity(j);
               /* final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
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
                dialog.show();*/
            }
        });
    }


    private void creditNote() {
        creditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Preferences.getInstance(context).setAttachment("");
                Preferences.getInstance(context).setUrlAttachment("");
                Intent j = new Intent(context, CreateCreditNoteWoActivity.class);
                context.startActivity(j);
                /*final CharSequence[] items = {"Add", "Modify"};
                AlertDialog.Builder builder = new AlertDialog.Builder(TransactionDashboardActivity.this);
                builder.setItems(items, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            Intent j = new Intent(context, CreateCreditNoteWoActivity.class);
                            j.putExtra("fromCreditNote", false);
                            context.startActivity(j);

                        } else if (item == 1) {
                            Intent j = new Intent(context, CreditNoteWoItemListActivity.class);
                            j.putExtra("fromPayment", false);
                            context.startActivity(j);
                        }
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();*/
            }
        });
    }

    private void stockTransfer() {
        stockTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appUser.serial_arr.clear();
                Preferences.getInstance(context).setVoucher_date("");
                Preferences.getInstance(context).setVoucher_number("");
                Preferences.getInstance(context).setStore("");
                Preferences.getInstance(context).setStore_to("");
                Preferences.getInstance(context).setMobile("");
                Preferences.getInstance(context).setNarration("");
                Preferences.getInstance(context).setCash_credit("");
//                Preferences.getInstance(context).setPurchase_type_name("");
                appUser.mListMapForItemPurchase.clear();
                appUser.mListMapForBillPurchase.clear();
                LocalRepositories.saveAppUser(context, appUser);
                Intent intent = new Intent(getApplicationContext(), CreateStockTransferActivity.class);
                intent.putExtra("fromstocklist", false);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, FirstPageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, FirstPageActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
