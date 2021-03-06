package com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.app.ConnectivityReceiver;
import com.lkintechnology.mBilling.activities.company.navigations.dashboard.MasterDashboardActivity;
import com.lkintechnology.mBilling.adapters.AccountExpandableListAdapter;
import com.lkintechnology.mBilling.adapters.AutoCompleteAdapter;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.events.EventSelectBankCaseDeposit;
import com.lkintechnology.mBilling.networks.ApiCallsService;
import com.lkintechnology.mBilling.networks.api_response.account.DeleteAccountResponse;
import com.lkintechnology.mBilling.networks.api_response.account.GetAccountResponse;
import com.lkintechnology.mBilling.utils.Cv;
import com.lkintechnology.mBilling.utils.EventAccountChildClicked;
import com.lkintechnology.mBilling.utils.EventClickForAutoCompleteTextView;
import com.lkintechnology.mBilling.utils.EventDeleteAccount;
import com.lkintechnology.mBilling.utils.EventEditAccount;
import com.lkintechnology.mBilling.utils.EventSelectAccountPurchase;
import com.lkintechnology.mBilling.utils.Helpers;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;
import com.lkintechnology.mBilling.utils.TypefaceCache;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ExpandableAccountListActivity extends AppCompatActivity {
    @Bind(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @Bind(R.id.lvExp)
    ExpandableListView expListView;
    @Bind(R.id.floating_button)
    FloatingActionButton mFloatingButton;
    @Bind(R.id.search_view)
    AutoCompleteTextView autoCompleteTextView;
    @Bind(R.id.top_layout)
    RelativeLayout mOverlayLayout;
    @Bind(R.id.error_layout)
    LinearLayout error_layout;
    AccountExpandableListAdapter listAdapter;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    HashMap<Integer, List<String>> listDataChildId;
    HashMap<Integer, List<String>> listDataChildMobile;
    HashMap<Integer, List<String>> listDataChildState;
    HashMap<String, List<String>> listDataChildEmail;
    List<String> mobile;
    ProgressDialog mProgressDialog;
    AppUser appUser;
    Snackbar snackbar;
    List<String> name;
    List<String> id;
    public static Boolean isDirectForAccount = true;
    private ArrayList<Map<String, String>> mPeopleList;
    Map<String, String> NamePhoneType;

    private SimpleAdapter mAdapter;

    List<String> nameList;
    List<String> mobileList;
    List<String> stateList;
    List<String> idList;
    int mypos;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_expandabl_list);
        ButterKnife.bind(this);
     /*   if (isFirstTime()) {
            mOverlayLayout.setVisibility(View.INVISIBLE);
        }*/
        initActionbar();
        mFloatingButton.bringToFront();
        appUser = LocalRepositories.getAppUser(this);
        appUser.account_name="";
        appUser.account_mobile_number="";
        appUser.account_email="";
        appUser.account_amount_receivable = "";
        appUser.account_amount_payable = "";
        appUser.account_address = "";
        appUser.account_pinCode = "";
        appUser.account_city = "";
        appUser.account_state = "";
        appUser.account_gst = "";
        appUser.account_type_of_dealer = "";
        appUser.account_aadhaar = "";
        appUser.account_pan = "";
        appUser.account_credit_limit = "";
        appUser.account_credit_sale = "";
        appUser.account_credit_purchase = "";
        LocalRepositories.saveAppUser(this, appUser);

    }

    private boolean isFirstTime() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        //SharedPreferences preferences1=getP
        boolean ranBefore = preferences.getBoolean("RanBefore", false);
        if (!ranBefore) {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("RanBefore", true);
            editor.commit();
            mOverlayLayout.setVisibility(View.VISIBLE);
            mOverlayLayout.setOnTouchListener(new View.OnTouchListener(){

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    mOverlayLayout.setVisibility(View.INVISIBLE);
                    return false;
                }

            });


        }
        return ranBefore;

    }

    private void initActionbar() {
        ActionBar actionBar = getSupportActionBar();
        View viewActionBar = getLayoutInflater().inflate(R.layout.action_bar_tittle_text_layout, null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#067bc9")));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(viewActionBar, params);
        TextView actionbarTitle = (TextView) viewActionBar.findViewById(R.id.actionbar_textview);
        actionbarTitle.setText("ACCOUNT LIST");
        actionbarTitle.setTypeface(TypefaceCache.get(getAssets(), 3));
        actionbarTitle.setTextSize(16);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (isDirectForAccount) {
                    Intent intent = new Intent(this, MasterDashboardActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    finish();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (isDirectForAccount) {
            Intent intent = new Intent(this, MasterDashboardActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } else {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
        /*appUser.account_master_group="";
        LocalRepositories.saveAppUser(this,appUser);*/
        Boolean isConnected = ConnectivityReceiver.isConnected();
        if (isConnected) {
            mProgressDialog = new ProgressDialog(ExpandableAccountListActivity.this);
            mProgressDialog.setMessage("Info...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setCancelable(true);
            mProgressDialog.show();
            LocalRepositories.saveAppUser(getApplicationContext(), appUser);
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT);


        } else {
            snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Boolean isConnected = ConnectivityReceiver.isConnected();
                            if (isConnected) {
                                snackbar.dismiss();
                            }
                        }
                    });
            snackbar.show();
        }


    }

    @Override
    protected void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    public void add(View v) {
        Intent intent = new Intent(getApplicationContext(), AccountDetailsActivity.class);
        intent.putExtra("fromaccountlist", false);
        startActivity(intent);
    }


    @Subscribe
    public void getAccount(GetAccountResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            mPeopleList = new ArrayList<Map<String, String>>();
            nameList = new ArrayList();
            mobileList = new ArrayList();

            idList = new ArrayList();

            listDataHeader = new ArrayList<>();
            listDataChild = new HashMap<String, List<String>>();
            listDataChildMobile = new HashMap<Integer, List<String>>();
            listDataChildState = new HashMap<Integer, List<String>>();
            listDataChildId = new HashMap<Integer, List<String>>();
            if (response.getOrdered_accounts().size() == 0) {
                //Snackbar.make(coordinatorLayout, "No Account Found!!", Snackbar.LENGTH_LONG).show();
                expListView.setVisibility(View.GONE);
                error_layout.setVisibility(View.VISIBLE);
            }else {
                expListView.setVisibility(View.VISIBLE);
                error_layout.setVisibility(View.GONE);
            }
            for (int i = 0; i < response.getOrdered_accounts().size(); i++) {

                if(response.getOrdered_accounts().get(i).getGroup_name().equals("Sundry Debtors")){
                    mypos=i;
                }
                listDataHeader.add(response.getOrdered_accounts().get(i).getGroup_name());

                name = new ArrayList<>();
                mobile = new ArrayList<>();
                stateList = new ArrayList();
                id = new ArrayList<>();
                for (int j = 0; j < response.getOrdered_accounts().get(i).getData().size(); j++) {
                    NamePhoneType = new HashMap<String, String>();
                    name.add(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getName()
                            + "," + String.valueOf(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getUndefined())
                            +","+response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getAmount()
                            +","+response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getEmail());
                    id.add(response.getOrdered_accounts().get(i).getData().get(j).getId());
                    mobile.add(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getMobile_number());
                    NamePhoneType.put("Name", response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getName());
                    if(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getEmail()!=null){
                        NamePhoneType.put("Email",response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getEmail());
                    }else {
                        NamePhoneType.put("Email","");
                    }
                    if(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getMobile_number()!=null) {
                        NamePhoneType.put("Phone", response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getMobile_number());
                    }
                    else{
                        NamePhoneType.put("Phone","");
                    }
                    if (response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getState()!=null){
                        NamePhoneType.put("State",response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getState());
                    }else {
                        NamePhoneType.put("State","");
                    }
                    nameList.add(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getName());
                    mobileList.add(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getMobile_number());
                    stateList.add(response.getOrdered_accounts().get(i).getData().get(j).getAttributes().getState());
                    idList.add(response.getOrdered_accounts().get(i).getData().get(j).getId());
                    mPeopleList.add(NamePhoneType);
                }

                Timber.i("MYNAMETYPE"+mPeopleList);
                listDataChild.put(listDataHeader.get(i), name);
                listDataChildId.put(i, id);
                listDataChildMobile.put(i, mobile);
                listDataChildState.put(i, stateList);
            }
           /* if(!ParameterConstant.forPaymentSettlement.equals(""))
            for(int k=0;k<listDataHeader.size();k++){
                if(listDataHeader.get(k).equals("Sundry Debtors")){
                    listDataChild.remove("Sundry Debtors");
                    listDataHeader.remove(k);
                    listDataChildId.remove(k);
                    listDataChildMobile.remove(k);
                    listDataChildState.remove(k);
                    break;
                }
            }*/

            listAdapter = new AccountExpandableListAdapter(this, listDataHeader, listDataChild);

            // setting list adapter
            expListView.setAdapter(listAdapter);
            for (int i = 0; i < listAdapter.getGroupCount(); i++) {
                expListView.expandGroup(i);
            }
            if(ParameterConstant.forPaymentSettlement!=null) {
                if (!ParameterConstant.forPaymentSettlement.equals("")) {
                    expListView.collapseGroup(mypos);
                    expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                            // Doing nothing
                            return true;
                        }
                    });
                }
            }



            autoCompleteTextView();

        } else {
            //   startActivity(new Intent(getApplicationContext(), MasterDashboardActivity.class));
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }


    @Subscribe
    public void deletegroup(EventDeleteAccount pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.delete_account_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        new AlertDialog.Builder(ExpandableAccountListActivity.this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete this account ?")
                .setPositiveButton(R.string.btn_ok, (dialogInterface, i) -> {
                    Boolean isConnected = ConnectivityReceiver.isConnected();
                    if (isConnected) {
                        mProgressDialog = new ProgressDialog(ExpandableAccountListActivity.this);
                        mProgressDialog.setMessage("Info...");
                        mProgressDialog.setIndeterminate(false);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.show();
                        ApiCallsService.action(getApplicationContext(), Cv.ACTION_DELETE_ACCOUNT);
                    } else {
                        snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Boolean isConnected = ConnectivityReceiver.isConnected();
                                        if (isConnected) {
                                            snackbar.dismiss();
                                        }
                                    }
                                });
                        snackbar.show();
                    }

                })
                .setNegativeButton(R.string.btn_cancel, null)
                .show();


    }

    @Subscribe
    public void deletegroupresponse(DeleteAccountResponse response) {
        mProgressDialog.dismiss();
        if (response.getStatus() == 200) {
            ApiCallsService.action(getApplicationContext(), Cv.ACTION_GET_ACCOUNT);
            Snackbar
                    .make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            //Snackbar.make(coordinatorLayout, response.getMessage(), Snackbar.LENGTH_LONG).show();
            Helpers.dialogMessage(this,response.getMessage());
        }
    }

    @Subscribe
    public void editgroup(EventEditAccount pos) {
        String id = pos.getPosition();
        String[] arr = id.split(",");
        String groupid = arr[0];
        String childid = arr[1];
        String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
        appUser.edit_account_id = arrid;
        LocalRepositories.saveAppUser(this, appUser);
        Intent intent = new Intent(getApplicationContext(), AccountDetailsActivity.class);
        intent.putExtra("fromaccountlist", true);
        intent.putExtra("bool",true);
        startActivity(intent);

    }

    @Subscribe
    public void clickEvent(EventAccountChildClicked pos) {
        Intent intent = getIntent();
        Boolean bool = intent.getBooleanExtra("bool", false);
        if (!isDirectForAccount && bool) {

            autoCompleteTextView();
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String group=listDataHeader.get(Integer.parseInt(groupid));
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            String mobile = listDataChildMobile.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String state = listDataChildState.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            ParameterConstant.forAccountIntentName=name;
            ParameterConstant.forAccountIntentId=arrid;
            ParameterConstant.forAccountIntentBool=true;
            ParameterConstant.forAccountIntentGroupId=group;
            ParameterConstant.forAccountIntentMobile=mobile;
            ParameterConstant.forAccountIntentState=state;
            LocalRepositories.saveAppUser(this,appUser);
            finish();
        }
           /* Intent intentForward = null;
            if (ParameterConstant.checkStartActivityResultForAccount == 0) {
                intentForward = new Intent(getApplicationContext(), CreateSaleActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 1) {
                intentForward = new Intent(getApplicationContext(), CreateReceiptVoucherActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 2) {
                intentForward = new Intent(getApplicationContext(), CreatePurchaseActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 3) {
                intentForward = new Intent(getApplicationContext(), CreatePaymentActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 4) {
                intentForward = new Intent(getApplicationContext(), CreateBankCaseDepositActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 5) {
                intentForward = new Intent(getApplicationContext(), CreateBankCaseWithdrawActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 6) {
                intentForward = new Intent(getApplicationContext(), CreateIncomeActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 7) {
                intentForward = new Intent(getApplicationContext(), CreateExpenceActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 8) {
                intentForward = new Intent(getApplicationContext(), CreateSaleReturnActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 9) {
                intentForward = new Intent(getApplicationContext(), CreatePurchaseReturnActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 10) {
                intentForward = new Intent(getApplicationContext(), CreateJournalVoucherActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 11) {
                intentForward = new Intent(getApplicationContext(), CreateDebitNoteWoItemActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 12) {
                    intentForward.putExtra("fromCreditNote",true);
                    intentForward = new Intent(getApplicationContext(), CreateCreditNoteWoActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 13) {
                intentForward = new Intent(getApplicationContext(), AccountingInSaleActivity.class);
            } else if (ParameterConstant.checkStartActivityResultForAccount == 14) {
                intentForward = new Intent(getApplicationContext(), AccountingInPurchaseActivity.class);
            }*/
          /*  intentForward.putExtra("bool", true);
            intentForward.putExtra("name", name);
            intentForward.putExtra("id", arrid);
            intentForward.putExtra("mobile", mobile);
            intentForward.putExtra("group",group);*/


            //setResult(Activity.RESULT_OK, intentForward);
            //startActivity(intentForward);

         else if (!isDirectForAccount) {

            //Toast.makeText(ExpandableAccountListActivity.this, "with in else", Toast.LENGTH_SHORT).show();
            autoCompleteTextView();
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String group=listDataHeader.get(Integer.parseInt(groupid));
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            String mobile = listDataChildMobile.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String state = listDataChildState.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            Intent returnIntent = new Intent();
            returnIntent.putExtra("bool", false);
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("id", arrid);
            returnIntent.putExtra("mobile", mobile);
            returnIntent.putExtra("state", state);
            returnIntent.putExtra("group",group);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }

    @Subscribe
    public void clickEvent(EventSelectBankCaseDeposit pos) {
        if (!isDirectForAccount) {
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("id", arrid);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


    @Subscribe
    public void clickEventPurchase(EventSelectAccountPurchase pos) {
        if (!isDirectForAccount) {

            autoCompleteTextView();
            String id = pos.getPosition();
            String[] arr = id.split(",");
            String groupid = arr[0];
            String childid = arr[1];
            String arrid = listDataChildId.get(Integer.parseInt(groupid)).get(Integer.parseInt(childid));
            String name = listDataChild.get(listDataHeader.get(Integer.parseInt(groupid))).get(Integer.parseInt(childid));
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", name);
            returnIntent.putExtra("id", arrid);
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
    }


    @Subscribe
    public void timout(String msg) {
        snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);
        snackbar.show();
        mProgressDialog.dismiss();
    }

    private void autoCompleteTextView() {
        autoCompleteTextView.setThreshold(1);
        AutoCompleteAdapter mAdapter2 = new AutoCompleteAdapter(this, R.layout.account_row, R.id.ccontName,R.id.ccontNo, mPeopleList);
        autoCompleteTextView.setAdapter(mAdapter2);
        /* AutoCompleteAdapter mAdapter2 = new AutoCompleteAdapter(this, R.id.ccontName, R.id.ccontNo ,mPeopleList);
        autoCompleteTextView.setAdapter(mAdapter);*/
/*        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, nameList);
        autoCompleteTextView.setAdapter(adapter);*/
        /*autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                autoCompleteTextView.setText("");
                if (!isDirectForAccount) {
                    Intent returnIntent = new Intent();
                    Map<String, String> map = (Map<String, String>) adapterView.getItemAtPosition(i);
                    String name = map.get("Name");
                    String number = map.get("Phone");
                    returnIntent.putExtra("name", name);
                    returnIntent.putExtra("mobile", number);
                    String id = idList.get(getPositionOfItem(name));
                    returnIntent.putExtra("id", id);
                    //Toast.makeText(ExpandableAccountListActivity.this, "" + id, Toast.LENGTH_SHORT).show();
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                }
            }
        });*/
       /* if (isDirectForAccount){
            autoCompleteTextView.setVisibility(View.GONE);
        }*/
    }

    private int getPositionOfItem(String category) {
        for (int i = 0; i < this.nameList.size(); i++) {
            if (this.nameList.get(i) == category)
                return i;
        }

        return -1;
    }

    @Subscribe
    public void clickEventForAutoCompleteTextView(EventClickForAutoCompleteTextView pos) {
        String data = pos.getPosition();
        autoCompleteTextView.setText("");
        if (!isDirectForAccount) {
            ParameterConstant.handleAutoCompleteTextView = 1;
            Intent returnIntent = new Intent();
            for (int i = 0; i < mPeopleList.size(); i++) {
                Map<String, String> map = mPeopleList.get(i);
                String name = map.get("Name");
                String phone = map.get("Phone");
                String email = map.get("Email");
                String state = map.get("State");
                if (data.equals(name)) {
                    String id = idList.get(getPositionOfItem(name));
                    ParameterConstant.name = name;
                    ParameterConstant.mobile = phone;
                    ParameterConstant.email = email;
                    ParameterConstant.state = state;
                    ParameterConstant.id = id;
                } else if (data.equals(phone)) {
                    String id = idList.get(getPositionOfItem(name));
                    ParameterConstant.name = name;
                    ParameterConstant.mobile = phone;
                    ParameterConstant.email = email;
                    ParameterConstant.id = id;
                    ParameterConstant.state = state;
                }
                setResult(Activity.RESULT_OK, returnIntent);
                finish();
            }
        }

    }

}