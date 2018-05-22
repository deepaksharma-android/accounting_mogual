package com.lkintechnology.mBilling.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lkintechnology.mBilling.R;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.account.ExpandableAccountListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.accountgroup.AccountGroupListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.billsundry.BillSundryListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item_group.ItemGroupListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentre.MaterialCentreListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.materialcentregroup.MaterialCentreGroupListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.purchasetype.PurchaseTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.saletype.SaleTypeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.taxcategory.TaxCategoryeListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.unit.UnitListActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.unitconversion.UnitConversionListActivity;
import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.utils.LocalRepositories;
import com.lkintechnology.mBilling.utils.ParameterConstant;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MasterDashboardAdapter extends RecyclerView.Adapter<MasterDashboardAdapter.ViewHolder> {
    private String[] data;
    private Context context;
    int[] images;
    int[] color;

    public MasterDashboardAdapter(Context context, String[] data, int[] images, int[] color) {
        this.data = data;
        this.context = context;
        this.images=images;
        this.color=color;
    }

    @Override
    public MasterDashboardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_generic_grid_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MasterDashboardAdapter.ViewHolder viewHolder, int i) {
        viewHolder.mImage.setImageResource(images[i]);
        viewHolder.mTitleText.setText(data[i]);
        viewHolder.mView.setBackgroundColor(color[i]);
        viewHolder.mGridLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(i==0){
                    AppUser appUser= LocalRepositories.getAppUser(context);
                    appUser.account_master_group="";
                    ExpandableAccountListActivity.isDirectForAccount=true;
                    LocalRepositories.saveAppUser(context,appUser);
                    context.startActivity(new Intent(context, ExpandableAccountListActivity.class));
                }
                if(i==1){
                    AccountGroupListActivity.isDirectForAccountGroup=true;
                    Intent intent=new Intent(context,AccountGroupListActivity.class);
                    intent.putExtra("frommaster",true);
                    context.startActivity(intent);
                }
                if(i==2){
                    ExpandableItemListActivity.isDirectForItem=true;
                    context.startActivity(new Intent(context, ExpandableItemListActivity.class));
                }
                if(i==3){
                    ItemGroupListActivity.isDirectForItemGroup=true;
                    context.startActivity(new Intent(context, ItemGroupListActivity.class));
                }
                if(i==4){
                    MaterialCentreListActivity.isDirectForMaterialCentre=true;
                    context.startActivity(new Intent(context, MaterialCentreListActivity.class));
                }
                if(i==5){
                    MaterialCentreGroupListActivity.isDirectForMaterialCentreGroup=true;
                    context.startActivity(new Intent(context, MaterialCentreGroupListActivity.class));
                }
                if(i==6){
                    UnitListActivity.isDirectForUnitList=true;
                    context.startActivity(new Intent(context, UnitListActivity.class));
                }
                if(i==7){
                    context.startActivity(new Intent(context, UnitConversionListActivity.class));
                }
                if(i==8){
                    BillSundryListActivity.isDirectForBill=true;
                    context.startActivity(new Intent(context, BillSundryListActivity.class));
                }
                if(i==9){
                    ParameterConstant.checkForPurchaseTypeList=0;
                    PurchaseTypeListActivity.isDirectForPurchaseTypeList=true;
                    context.startActivity(new Intent(context, PurchaseTypeListActivity.class));
                }
                if(i==10){
                    SaleTypeListActivity.isDirectForSaleType=true;
                    context.startActivity(new Intent(context, SaleTypeListActivity.class));
                }
                if(i==11){
                    TaxCategoryeListActivity.isDirectForTaxCategoryList=true;
                    context.startActivity(new Intent(context, TaxCategoryeListActivity.class));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
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
}