package com.lkintechnology.mBilling.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lkintechnology.mBilling.R;

import com.lkintechnology.mBilling.activities.company.FirstPageActivity;
import com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity;
import com.lkintechnology.mBilling.utils.EventDeleteItem;
import com.lkintechnology.mBilling.utils.EventEditItem;
import com.lkintechnology.mBilling.utils.EventOpenCompany;
import com.lkintechnology.mBilling.utils.EventSaleAddItem;
import com.lkintechnology.mBilling.utils.LocalRepositories;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity.expListView;
import static com.lkintechnology.mBilling.activities.company.navigations.administration.masters.item.ExpandableItemListActivity.listAdapter;

public class ItemExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    private HashMap<Integer, List<String>> listDataChildSalePriceMain;
    private int comingFromPOS;
    Double total = 0.0;
    Double sale_price_main = 0.0;
    public Dialog dialog;
    Double finalCal = 0.00;
    Double first = 0.0, second = 0.0, third = 0.0;

    public ItemExpandableListAdapter(Context context, List<String> listDataHeader,
                                     HashMap<String, List<String>> listChildData, HashMap<Integer, List<String>> listDataChildSalePriceMain, int comingFromPOS) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
        this.listDataChildSalePriceMain = listDataChildSalePriceMain;
        this.comingFromPOS = comingFromPOS;
        ExpandableItemListActivity.mChildCheckStates = new HashMap<Integer, String[]>();
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    String childName;
    int mInteger = 0;

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final int mGroupPosition = groupPosition;
        final int mChildPosition = childPosition;
        mInteger = 0;
        final String childText = (String) getChild(groupPosition, childPosition);
        String arr[] = childText.split(",");
        String name = arr[0];
        String quantity = arr[1];
        String item_id = arr[3];
        total = 0.0;
        sale_price_main = 0.0;
        if (arr[2] != null && !arr[2].equals("")) {
            sale_price_main = Double.valueOf(arr[2]);
        } else {
            sale_price_main = 0.0;
        }
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item_for_item, null);
        }
        LinearLayout old_layout = (LinearLayout) convertView.findViewById(R.id.old_layout);
        LinearLayout new_layout = (LinearLayout) convertView.findViewById(R.id.new_layout);
        LinearLayout mainLayout = (LinearLayout) convertView.findViewById(R.id.main_layout);

        if (comingFromPOS == 6 && !ExpandableItemListActivity.isDirectForItem) {
            old_layout.setVisibility(View.GONE);
            new_layout.setVisibility(View.VISIBLE);
            TextView txtListChild = (TextView) convertView.findViewById(R.id.posListItem);
            TextView mQuantity = (TextView) convertView.findViewById(R.id.quantity);
            TextView mItemAmount = (TextView) convertView.findViewById(R.id.item_amount);
            TextView mItemTotal = (TextView) convertView.findViewById(R.id.item_total);
            LinearLayout plus_minus_layout = (LinearLayout) convertView.findViewById(R.id.plus_minus_layout);
            LinearLayout add_layout = (LinearLayout) convertView.findViewById(R.id.add_layout);
            LinearLayout decrease = (LinearLayout) convertView.findViewById(R.id.decrease);
            LinearLayout increase = (LinearLayout) convertView.findViewById(R.id.increase);
            LinearLayout add_price_layout = (LinearLayout) convertView.findViewById(R.id.add_price_layout);
            if (ExpandableItemListActivity.mChildCheckStates.containsKey(mGroupPosition)) {
                String getChecked[] = ExpandableItemListActivity.mChildCheckStates.get(mGroupPosition);
                if (getChecked[mChildPosition] == null || getChecked[mChildPosition].equals("0")) {
                    mQuantity.setText("0");
                    plus_minus_layout.setVisibility(View.GONE);
                    add_layout.setVisibility(View.VISIBLE);
                } else {
                    mQuantity.setText(getChecked[mChildPosition]);
                    plus_minus_layout.setVisibility(View.VISIBLE);
                    add_layout.setVisibility(View.GONE);
                }

            } else {
                String getChecked[] = new String[getChildrenCount(mGroupPosition)];
                ExpandableItemListActivity.mChildCheckStates.put(mGroupPosition, getChecked);
                //mQuantity.setText("0");
            }
            if (ExpandableItemListActivity.mListMapForItemSale.size() > 0) {
                for (int i = 0; i < ExpandableItemListActivity.mListMapForItemSale.size(); i++) {
                    String item_id_child = ExpandableItemListActivity.mListMapForItemSale.get(i).get("item_id").toString();
                    if (item_id.equals(item_id_child)) {
                        String pos = groupPosition + "," + childPosition;
                        mQuantity.setText(ExpandableItemListActivity.mListMapForItemSale.get(i).get("quantity").toString());
                        ExpandableItemListActivity.mMapPosItem.put(pos, mQuantity.getText().toString());
                        String getChecked[] = ExpandableItemListActivity.mChildCheckStates.get(mGroupPosition);
                        getChecked[mChildPosition] = mQuantity.getText().toString();
                        ExpandableItemListActivity.mChildCheckStates.put(mGroupPosition, getChecked);
                    }
                }
            }

            txtListChild.setText(name + " (qty: " + quantity + ")");
            //mItemAmount.performClick();
            mItemAmount.setText("₹ " + sale_price_main);
            if (FirstPageActivity.fromPos2) {
                add_price_layout.setVisibility(View.VISIBLE);
                add_price_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String pos = groupPosition + "," + childPosition;
                        Double sale_price_main = Double.valueOf(listDataChildSalePriceMain.get(groupPosition).get(childPosition));
                        showpopup(groupPosition, childPosition, sale_price_main, mQuantity.getText().toString());
                    }
                });
            } else {
                add_price_layout.setVisibility(View.GONE);
            }
            mItemTotal.setText("₹ 0.0");


            decrease.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ExpandableItemListActivity.mListMapForItemSale.clear();
                    String pos = groupPosition + "," + childPosition;
                    Double sale_price_main = Double.valueOf(listDataChildSalePriceMain.get(groupPosition).get(childPosition));
                    // String pos = listDataChildId.get(mGroupPosition).get(childPosition);
                    try {
                        mInteger = Integer.parseInt(mQuantity.getText().toString());
                    } catch (Exception e) {
                        mInteger = 0;
                    }
                    if (mInteger > 0) {

                        mInteger = mInteger - 1;
                        mQuantity.setText("" + mInteger);
                        setTotal(String.valueOf(sale_price_main), false);

                        String arr = mItemTotal.getText().toString();
                        String[] arr1 = arr.split("₹ ");
                        Double total = Double.valueOf(arr1[1]);
                        Double amount = Double.valueOf(sale_price_main);
                        String s = String.valueOf(total - amount);
                        mItemTotal.setText("₹ " + s);
                        if (mInteger == 0) {
                            plus_minus_layout.setVisibility(View.GONE);
                            add_layout.setVisibility(View.VISIBLE);
                            mItemTotal.setText("₹ " + 0.0);
                            mQuantity.setText("0");
                            ExpandableItemListActivity.mMapPosItem.put(pos, "0");
                        } else {
                            ExpandableItemListActivity.mMapPosItem.put(pos, mQuantity.getText().toString());
                            System.out.println(ExpandableItemListActivity.mMapPosItem.toString());
                        }
                    }

                    String getChecked[] = ExpandableItemListActivity.mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = mQuantity.getText().toString();
                    ExpandableItemListActivity.mChildCheckStates.put(mGroupPosition, getChecked);

                }
            });
            increase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mInteger = 0;
                    ExpandableItemListActivity.mListMapForItemSale.clear();
                    String pos = groupPosition + "," + childPosition;
                    Double sale_price_main = Double.valueOf(listDataChildSalePriceMain.get(groupPosition).get(childPosition));
                    add_layout.setVisibility(View.GONE);
                    plus_minus_layout.setVisibility(View.VISIBLE);
                    try {
                        mInteger = Integer.parseInt(mQuantity.getText().toString());
                    } catch (Exception e) {
                        mInteger = 0;
                    }

                    mInteger = mInteger + 1;
                    mQuantity.setText("" + mInteger);

                    //total = sale_price_main * mInteger;
                    setTotal(String.valueOf(sale_price_main), true);

                    //Item total code
                    String arr = mItemTotal.getText().toString();
                    String[] arr1 = arr.split("₹ ");
                    Double total = Double.valueOf(arr1[1]);
                    Double amount = Double.valueOf(sale_price_main);
                    String s = String.valueOf(total + amount);
                    mItemTotal.setText("₹ " + s);

                    String getChecked[] = ExpandableItemListActivity.mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = mQuantity.getText().toString();
                    ExpandableItemListActivity.mChildCheckStates.put(mGroupPosition, getChecked);
                    if (!mQuantity.getText().toString().equals("")) {
                        ExpandableItemListActivity.mMapPosItem.put(pos, mQuantity.getText().toString());
                        System.out.println(ExpandableItemListActivity.mMapPosItem.toString());
                    }
                }
            });

            add_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ExpandableItemListActivity.mListMapForItemSale.clear();
                    String pos = groupPosition + "," + childPosition;
                    Double sale_price_main = Double.valueOf(listDataChildSalePriceMain.get(groupPosition).get(childPosition));
                    mQuantity.setText("1");
                    mItemTotal.setText("₹ " + sale_price_main);
                    setTotal(String.valueOf(sale_price_main), true);
                    String getChecked[] = ExpandableItemListActivity.mChildCheckStates.get(mGroupPosition);
                    getChecked[mChildPosition] = mQuantity.getText().toString();
                    ExpandableItemListActivity.mChildCheckStates.put(mGroupPosition, getChecked);
                    if (!mQuantity.getText().toString().equals("")) {
                        ExpandableItemListActivity.mMapPosItem.put(pos, mQuantity.getText().toString());
                        System.out.println(ExpandableItemListActivity.mMapPosItem.toString());
                    }
                    add_layout.setVisibility(View.GONE);
                    plus_minus_layout.setVisibility(View.VISIBLE);

                }
            });

            if (this._listDataHeader.size() - 1 == mGroupPosition && this._listDataChild.get(this._listDataHeader.get(groupPosition)).size() - 1 == mChildPosition) {
                ExpandableItemListActivity.mListMapForItemSale.clear();
            }

            /*mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = groupPosition + "," + childPosition;
                    EventBus.getDefault().post(new EventEditItem(id));
                }
            });*/
        } else {
            old_layout.setVisibility(View.VISIBLE);
            new_layout.setVisibility(View.GONE);

            TextView txtListChild = (TextView) convertView.findViewById(R.id.lblListItem);
            txtListChild.setText(name + " (qty: " + quantity + ")");
            LinearLayout delete = (LinearLayout) convertView.findViewById(R.id.delete_icon);
            LinearLayout edit = (LinearLayout) convertView.findViewById(R.id.edit_icon);

            // For POS

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = groupPosition + "," + childPosition;
                    EventBus.getDefault().post(new EventDeleteItem(id));
                }
            });
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String id = groupPosition + "," + childPosition;
                    EventBus.getDefault().post(new EventEditItem(id));

                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String id = groupPosition + "," + childPosition;
                    EventBus.getDefault().post(new EventSaleAddItem(id));
                }
            });
        }


        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        ImageView imageview = (ImageView) convertView.findViewById(R.id.image);

       /* if (comingFromPOS==6 &&  !ExpandableItemListActivity.isDirectForItem){
            imageview.setVisibility(View.GONE);
            expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    //  Log.d("onGroupClick:", "worked");
                    parent.expandGroup(groupPosition);
                    return true;
                }
            });
        }*/

        if (isExpanded) {
            imageview.setImageResource(R.drawable.up_arrow);
        } else {
            imageview.setImageResource(R.drawable.down_arrow);
        }

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public void setTotal(String amount, Boolean mBool) {
        Double total = 0.0;
        if (mBool) {
            total = getTotal() + Double.valueOf(amount);
        } else {
            total = getTotal() - Double.valueOf(amount);
        }
        ExpandableItemListActivity.mTotal.setText("Total : " + total);
    }

    public Double getTotal() {
        String total = ExpandableItemListActivity.mTotal.getText().toString();
        String[] arr = total.split(":");
        Double a = Double.valueOf(arr[1].trim());
        return a;
    }

    public String splitString(String total) {
        // String total = PosItemAddActivity.mSubtotal.getText().toString();
        if (total.equals("0.0")) {
            total = "₹ 0.0";
        }
        String[] arr = total.split("₹ ");
        String a = arr[1].trim();
        return a;
    }

    public void showpopup(int groupPosition, int childPosition, Double sale_price_main, String quantity) {
        dialog = new Dialog(_context);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pos2_discount);
        dialog.setCancelable(true);
        // set the custom dialog components - text, image and button
        EditText mRate = (EditText) dialog.findViewById(R.id.rate);
        EditText mDiscount = (EditText) dialog.findViewById(R.id.discount);
        EditText mDiscount_value = (EditText) dialog.findViewById(R.id.discount_value);
        TextView mTotal_amount = (TextView) dialog.findViewById(R.id.total_amount);
        LinearLayout submit = (LinearLayout) dialog.findViewById(R.id.submit);
        LinearLayout close = (LinearLayout) dialog.findViewById(R.id.close);
        TextView mDialog_title = (TextView) dialog.findViewById(R.id.dialog_title);

        mRate.setText("" + sale_price_main);
        mTotal_amount.setText("" + sale_price_main);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputManager = (InputMethodManager) _context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
                Double finalCal = 0.0;
                Double finalRate = 0.0;
                Double discount_value = 0.0;
                Double discount = 0.0;
                finalCal = Double.valueOf(mTotal_amount.getText().toString());
                finalRate = Double.valueOf(mRate.getText().toString());
                discount_value = Double.valueOf(mDiscount_value.getText().toString());
                discount = Double.valueOf(mDiscount.getText().toString());
                if (finalCal != 0) {
                   // if (finalCal != finalRate) {
                        String childText = (String) getChild(groupPosition, childPosition);
                        String arr[] = childText.split(",");
                        List<String> list = new ArrayList<>();
                        List<String> listSalePrice = new ArrayList<>();
                        ExpandableItemListActivity.listDiscountValue = new ArrayList<>();
                        listSalePrice = ExpandableItemListActivity.listDataChildSalePriceMain.get(groupPosition);
                        listSalePrice.set(childPosition, String.valueOf(finalCal));
                        listDataChildSalePriceMain.put(groupPosition, listSalePrice);
                        ExpandableItemListActivity.listDataChildSalePriceMain.put(groupPosition, listSalePrice);

                        list = ExpandableItemListActivity.listDataChild.get(_listDataHeader.get(groupPosition));
                        list.set(childPosition, arr[0] + "," + arr[1] + "," + finalCal + "," + arr[3]);
                        _listDataChild.put(_listDataHeader.get(groupPosition), list);
                        ExpandableItemListActivity.listDataChild.put(_listDataHeader.get(groupPosition), list);

                       /* Map map = new HashMap();
                            map.put("discount",discount);
                            map.put("value",discount);*/

                        if (quantity != null) {
                            if (!quantity.equals("")) {
                                Double mQuantity = Double.valueOf(quantity);
                                if (mQuantity != 0) {
                                    Double a = getTotal() - (Double.valueOf(arr[2]) * mQuantity);
                                    Double total = a + (finalCal * mQuantity);
                                    ExpandableItemListActivity.mTotal.setText("Total : " + String.format("%.2f", total));
                                }
                            }
                        }
                        notifyDataSetChanged();
                  //  }
                }

               /* if (finalCal == 0) {
                    list.set(childPosition, arr[0] + "," + arr[1] + "," + arr[2] + "," + arr[3]);
                    listSalePrice.set(childPosition, arr[2]);
                } else {
                    list.set(childPosition, arr[0] + "," + arr[1] + "," + finalCal + "," + arr[3]);
                    listSalePrice.set(childPosition, String.valueOf(finalCal));
                }
                listDataChildSalePriceMain.put(groupPosition, listSalePrice);
                ExpandableItemListActivity.listDataChildSalePriceMain.put(groupPosition, listSalePrice);
                ExpandableItemListActivity.listDataChild.put(_listDataHeader.get(groupPosition), list);
                notifyDataSetChanged();*/
                dialog.dismiss();
            }
        });

        mDiscount.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mDiscount.getText().toString().isEmpty()) {
                    if (!mDiscount.getText().toString().equals("")) {
                        Double rate = Double.valueOf(mRate.getText().toString());
                        Double discount = Double.valueOf(mDiscount.getText().toString());
                       /* Double discount_value = 0.0;
                        if (!mDiscount_value.getText().toString().isEmpty()) {
                            discount_value = Double.valueOf(mDiscount_value.getText().toString());
                        }*/

                        if (discount != 0) {
                            finalCal = rate - ((rate * discount) / 100);
                            mDiscount_value.setText(""+(rate * discount) / 100);
                        }
                        mTotal_amount.setText("" + finalCal);
                      /*  if (rate == 0) {
                            finalCal = sale_price_main;
                        } else {
                            if (discount != 0) {
                                finalCal = rate - ((rate * discount) / 100);
                            }
                            if (discount_value != 0) {
                                finalCal = rate - discount_value;
                            }
                            if (discount != 0 && discount_value != 0) {
                                finalCal = sale_price_main;
                            }
                        }*/
                    }
                } else {
                    mDiscount_value.setText("0.0");
                    if (!mRate.getText().toString().isEmpty()) {
                        if (!mRate.getText().toString().equals("")) {
                            mTotal_amount.setText(mRate.getText().toString());
                        } else {
                            mTotal_amount.setText("0.0");
                        }
                    } else {
                        mTotal_amount.setText("0.0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mDiscount_value.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mDiscount_value.getText().toString().isEmpty()) {
                    if (!mDiscount_value.getText().toString().equals("")) {
                        Double rate = Double.valueOf(mRate.getText().toString());
                       /* Double discount = 0.0;
                        if (!mDiscount.getText().toString().isEmpty()) {
                            discount = Double.valueOf(mDiscount.getText().toString());
                        }*/
                        Double discount_value = Double.valueOf(mDiscount_value.getText().toString());
                        if (discount_value != 0) {
                            finalCal = rate - discount_value;
                        }
                        mTotal_amount.setText("" + finalCal);
                       /* if (rate == 0) {
                            finalCal = sale_price_main;
                        } else {
                            if (discount != 0) {
                                finalCal = rate - ((rate * discount) / 100);
                            }
                            if (discount_value != 0) {
                                finalCal = rate - discount_value;
                            }
                            if (discount != 0 && discount_value != 0) {
                                finalCal = sale_price_main;
                            }
                        }*/
                    }
                } else {
                    mDiscount.setText("0.0");
                    if (!mRate.getText().toString().isEmpty()) {
                        if (!mRate.getText().toString().equals("")) {
                            mTotal_amount.setText(mRate.getText().toString());
                        } else {
                            mTotal_amount.setText("0.0");
                        }
                    } else {
                        mTotal_amount.setText("0.0");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mRate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!mRate.getText().toString().isEmpty()) {
                    if (!mRate.getText().toString().equals("")) {
                        mTotal_amount.setText(mRate.getText().toString());
                        mDiscount.setText("0.0");
                        mDiscount_value.setText("0.0");
                    }
                } else {
                    mTotal_amount.setText("0.0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        dialog.show();
    }
}