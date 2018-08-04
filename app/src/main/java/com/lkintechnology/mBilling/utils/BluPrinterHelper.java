package com.lkintechnology.mBilling.utils;

import android.content.Context;
import android.widget.Toast;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.purchase_return.PurchaseReturnVoucherDetailsData;
import com.lkintechnology.mBilling.networks.api_response.purchasevoucher.PurchaseVoucherDetailsData;
import com.lkintechnology.mBilling.networks.api_response.sale_return.SaleReturnVoucherDetailsData;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.SaleVoucherDetailsData;

import java.io.IOException;

import static com.lkintechnology.mBilling.fragments.dashboard.DashboardAccountFragment.m_AemPrinter;

/**
 * Created by abc on 7/5/2018.
 */

public class BluPrinterHelper {
    private static int nFontSize, nTextAlign, nScaleTimesWidth,
            nScaleTimesHeight, nFontStyle, nLineHeight = 32, nRightSpace;

    public static void saleVoucherReceipt(Context context, SaleVoucherDetailsData dataList) {
        AppUser appUser;
        String encoding = "US-ASCII";
        String[] tax_detailsArray;
        appUser = LocalRepositories.getAppUser(context);
        if (m_AemPrinter == null) {
            Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String line, data;
            line = "------------------------------------------------";
            data = "INVOICE\n";
            m_AemPrinter.writeWithFormatOnly(new Formatter().bold().width().get(), Formatter.centerAlign());
            m_AemPrinter.POS_S_TextOutThreeInch(data, encoding, 0, 1, 0, nFontSize, nFontStyle);

            data = appUser.company_name + "\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            data = appUser.address+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.city + "(" + appUser.company_state + ")" + ",PIN NO "+appUser.companyzipcode+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.comapny_phone_number+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            if (appUser.gst!=null && !appUser.gst.equals("")){
                data = "GSTIN: "+appUser.gst+"\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
                // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            }
            m_AemPrinter.setLineFeed(1);
            data = "Invoice Details\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            data = spaceString(dataList.getAttributes().getAccount_master(), "Date: " + dataList.getAttributes().getDate() + "\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = spaceString("Bill No: " + dataList.getAttributes().getVoucher_series().getVoucher_number(), "Time: " + dataList.getAttributes().getTime()+"\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            tax_detailsArray = new String[dataList.getAttributes().getVoucher_items().size()];
            if (dataList.getAttributes().getVoucher_items().size() != 0) {
                data = "ITEM NAME/" + "      " + "Qty" + "        " + "Price" + "        " + "Net Amt" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "HSN Code\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                int total_quantity = 0;
                Double igst_tax = 0.0, cgst_tax = 0.0, sgst_tax = 0.0;
                for (int i = 0; i < dataList.getAttributes().getVoucher_items().size(); i++) {
                    Double taxable_amount = 0.0, igst = 0.0, cgst = 0.0, sgst = 0.0, total_tax = 0.0;
                    Double amount = 0.0, item_wise = 0.0;
                    int tax_catg = 0;
                    if (dataList.getAttributes().getVoucher_items().get(i).getTax_category() != null) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Exempt") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("ZeroRated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Zero Rated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Nil Rated")) {
                            tax_catg = 0;
                        } else {
                            String[] arr = dataList.getAttributes().getVoucher_items().get(i).getTax_category().split(" ");
                            String[] arr1 = arr[1].split("%");
                            tax_catg = Integer.valueOf(arr1[0]);
                        }
                    }
                    if (dataList.getAttributes().getSale_type().equals("I/GST-ItemWise") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                        amount = dataList.getAttributes().getVoucher_items().get(i).getRate_item() * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                        taxable_amount = amount;
                    } else {
                        amount = (dataList.getAttributes().getVoucher_items().get(i).getRate_item() / ((100 + tax_catg)) * 100);
                        taxable_amount = amount * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                    }

                    igst = dataList.getAttributes().getVoucher_items().get(i).getTotal_amount() - taxable_amount;
                    cgst = igst / 2;
                    sgst = igst / 2;
                    total_tax = igst;
                    igst_tax = igst_tax + total_tax;
                    cgst_tax = cgst_tax + cgst;
                    sgst_tax = sgst_tax + sgst;

                    if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                        data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", igst));
                        tax_detailsArray[i] = data;
                    } else if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                        data = spaceString6(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", cgst),
                                String.format("%.2f", sgst)
                                /*String.format("%.2f",total_tax)*/ + "\n");
                        tax_detailsArray[i] = data;
                    }

                    if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.")) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        }
                    } else {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        }
                    }

                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());

                    total_quantity = total_quantity + dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

                data = spaceString("Net Sale Qty", String.valueOf(total_quantity) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                data = spaceString("Gross Amount", String.valueOf(dataList.getAttributes().getItems_amount()) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
               /* if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                    data = spaceString("IGST Tax",String.format("%.2f",igst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                    data = spaceString("CGST Tax",String.format("%.2f",cgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    data = spaceString("SGST Tax",String.format("%.2f",sgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }*/
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getVoucher_bill_sundries() != null) {
                if (dataList.getAttributes().getVoucher_bill_sundries().size() != 0) {
                    data = "Bill Sundry\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                    m_AemPrinter.setLineFeed(1);
                    for (int i = 0; i < dataList.getAttributes().getVoucher_bill_sundries().size(); i++) {
                        if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_type().equals("Additive")) {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)              " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        } else {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)         " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        }
                        m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    }
                    m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                }
            }
            data = spaceString2("Final Bill Amount", String.valueOf(dataList.getAttributes().getTotal_amount() + "\n"));
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "           " + "Taxable" + "     " + "GST(%)" + "          " + "IGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "           " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i];
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "         " + "Taxable" + "   " + "GST(%)" + "     " + "CGST" + "     " + "SGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "         " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i] + "\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            m_AemPrinter.setLineFeed(1);

            data = "Terms And Conditions:-\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            data = "1. All the prices are inclusive of GST at applicable rates.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "2. No exchange is allowed for bags and accessories.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "3. No Warranty, No Exchange, And No Return.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "4. Exchange till 7 days with bill.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            m_AemPrinter.setLineFeed(1);
            data = "Powered by m-Billing\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
            m_AemPrinter.setLineFeed(2);



        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
        }
    }

    public static void purchaseReturnVoucherReceipt(Context context, PurchaseReturnVoucherDetailsData dataList) {
        AppUser appUser;
        String encoding = "US-ASCII";
        String[] tax_detailsArray;
        appUser = LocalRepositories.getAppUser(context);
        if (m_AemPrinter == null) {
            Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String line, data;
            line = "------------------------------------------------";
            data = "INVOICE\n";
            m_AemPrinter.writeWithFormatOnly(new Formatter().bold().width().get(), Formatter.centerAlign());
            m_AemPrinter.POS_S_TextOutThreeInch(data, encoding, 0, 1, 0, nFontSize, nFontStyle);

            data = appUser.company_name + "\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            data = appUser.address+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.city + "(" + appUser.company_state + ")" + ",PIN NO "+appUser.companyzipcode+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.comapny_phone_number+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            if (appUser.gst!=null && !appUser.gst.equals("")){
                data = "GSTIN: "+appUser.gst+"\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
                // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            }
            m_AemPrinter.setLineFeed(1);
            data = "Invoice Details\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            data = spaceString(dataList.getAttributes().getAccount_master(), "Date: " + dataList.getAttributes().getDate() + "\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = spaceString("Bill No: " + dataList.getAttributes().getVoucher_number(), "Time: " + dataList.getAttributes().getTime()+"\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            tax_detailsArray = new String[dataList.getAttributes().getVoucher_items().size()];
            if (dataList.getAttributes().getVoucher_items().size() != 0) {
                data = "ITEM NAME/" + "      " + "Qty" + "        " + "Price" + "        " + "Net Amt" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "HSN Code\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                int total_quantity = 0;
                Double igst_tax = 0.0, cgst_tax = 0.0, sgst_tax = 0.0;
                for (int i = 0; i < dataList.getAttributes().getVoucher_items().size(); i++) {
                    Double taxable_amount = 0.0, igst = 0.0, cgst = 0.0, sgst = 0.0, total_tax = 0.0;
                    Double amount = 0.0, item_wise = 0.0;
                    int tax_catg = 0;
                    if (dataList.getAttributes().getVoucher_items().get(i).getTax_category() != null) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Exempt") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("ZeroRated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Zero Rated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Nil Rated")) {
                            tax_catg = 0;
                        } else {
                            String[] arr = dataList.getAttributes().getVoucher_items().get(i).getTax_category().split(" ");
                            String[] arr1 = arr[1].split("%");
                            tax_catg = Integer.valueOf(arr1[0]);
                        }
                    }
                    if (dataList.getAttributes().getSale_type().equals("I/GST-ItemWise") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                        amount = dataList.getAttributes().getVoucher_items().get(i).getRate_item() * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                        taxable_amount = amount;
                    } else {
                        amount = (dataList.getAttributes().getVoucher_items().get(i).getRate_item() / ((100 + tax_catg)) * 100);
                        taxable_amount = amount * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                    }

                    igst = dataList.getAttributes().getVoucher_items().get(i).getTotal_amount() - taxable_amount;
                    cgst = igst / 2;
                    sgst = igst / 2;
                    total_tax = igst;
                    igst_tax = igst_tax + total_tax;
                    cgst_tax = cgst_tax + cgst;
                    sgst_tax = sgst_tax + sgst;

                    if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                        data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", igst));
                        tax_detailsArray[i] = data;
                    } else if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                        data = spaceString6(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", cgst),
                                String.format("%.2f", sgst)
                                /*String.format("%.2f",total_tax)*/ + "\n");
                        tax_detailsArray[i] = data;
                    }

                    if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.")) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        }
                    } else {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        }
                    }

                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());

                    total_quantity = total_quantity + dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

                data = spaceString("Net Sale Qty", String.valueOf(total_quantity) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                data = spaceString("Gross Amount", String.valueOf(dataList.getAttributes().getItems_amount()) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
               /* if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                    data = spaceString("IGST Tax",String.format("%.2f",igst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                    data = spaceString("CGST Tax",String.format("%.2f",cgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    data = spaceString("SGST Tax",String.format("%.2f",sgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }*/
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getVoucher_bill_sundries() != null) {
                if (dataList.getAttributes().getVoucher_bill_sundries().size() != 0) {
                    data = "Bill Sundry\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                    m_AemPrinter.setLineFeed(1);
                    for (int i = 0; i < dataList.getAttributes().getVoucher_bill_sundries().size(); i++) {
                        if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_type().equals("Additive")) {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)              " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        } else {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)         " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        }
                        m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    }
                    m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                }
            }
            data = spaceString2("Final Bill Amount", String.valueOf(dataList.getAttributes().getTotal_amount() + "\n"));
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "           " + "Taxable" + "     " + "GST(%)" + "          " + "IGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "           " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i];
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "         " + "Taxable" + "   " + "GST(%)" + "     " + "CGST" + "     " + "SGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "         " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i] + "\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            m_AemPrinter.setLineFeed(1);

            data = "Terms And Conditions:-\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            data = "1. All the prices are inclusive of GST at applicable rates.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "2. No exchange is allowed for bags and accessories.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "3. No Warranty, No Exchange, And No Return.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "4. Exchange till 7 days with bill.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            m_AemPrinter.setLineFeed(1);
            data = "Powered by m-Billing\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
            m_AemPrinter.setLineFeed(2);

        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
        }
    }

    public static void purchaseVoucherReceipt(Context context, PurchaseVoucherDetailsData dataList) {
        AppUser appUser;
        String encoding = "US-ASCII";
        String[] tax_detailsArray;
        appUser = LocalRepositories.getAppUser(context);
        if (m_AemPrinter == null) {
            Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String line, data;
            line = "------------------------------------------------";
            data = "INVOICE\n";
            m_AemPrinter.writeWithFormatOnly(new Formatter().bold().width().get(), Formatter.centerAlign());
            m_AemPrinter.POS_S_TextOutThreeInch(data, encoding, 0, 1, 0, nFontSize, nFontStyle);

            data = appUser.company_name + "\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            data = appUser.address+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.city + "(" + appUser.company_state + ")" + ",PIN NO "+appUser.companyzipcode+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.comapny_phone_number+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            if (appUser.gst!=null && !appUser.gst.equals("")){
                data = "GSTIN: "+appUser.gst+"\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
                // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            }
            m_AemPrinter.setLineFeed(1);
            data = "Invoice Details\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            data = spaceString(dataList.getAttributes().getAccount_master(), "Date: " + dataList.getAttributes().getDate() + "\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = spaceString("Bill No: " + dataList.getAttributes().getVoucher_number(), "Time: " + dataList.getAttributes().getTime()+"\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            tax_detailsArray = new String[dataList.getAttributes().getVoucher_items().size()];
            if (dataList.getAttributes().getVoucher_items().size() != 0) {
                data = "ITEM NAME/" + "      " + "Qty" + "        " + "Price" + "        " + "Net Amt" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "HSN Code\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                int total_quantity = 0;
                Double igst_tax = 0.0, cgst_tax = 0.0, sgst_tax = 0.0;
                for (int i = 0; i < dataList.getAttributes().getVoucher_items().size(); i++) {
                    Double taxable_amount = 0.0, igst = 0.0, cgst = 0.0, sgst = 0.0, total_tax = 0.0;
                    Double amount = 0.0, item_wise = 0.0;
                    int tax_catg = 0;
                    if (dataList.getAttributes().getVoucher_items().get(i).getTax_category() != null) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Exempt") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("ZeroRated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Zero Rated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Nil Rated")) {
                            tax_catg = 0;
                        } else {
                            String[] arr = dataList.getAttributes().getVoucher_items().get(i).getTax_category().split(" ");
                            String[] arr1 = arr[1].split("%");
                            tax_catg = Integer.valueOf(arr1[0]);
                        }
                    }
                    if (dataList.getAttributes().getPurchase_type().equals("I/GST-ItemWise") || dataList.getAttributes().getPurchase_type().equals("L/GST-ItemWise")) {
                        amount = dataList.getAttributes().getVoucher_items().get(i).getRate_item() * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                        taxable_amount = amount;
                    } else {
                        amount = (dataList.getAttributes().getVoucher_items().get(i).getRate_item() / ((100 + tax_catg)) * 100);
                        taxable_amount = amount * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                    }

                    igst = dataList.getAttributes().getVoucher_items().get(i).getTotal_amount() - taxable_amount;
                    cgst = igst / 2;
                    sgst = igst / 2;
                    total_tax = igst;
                    igst_tax = igst_tax + total_tax;
                    cgst_tax = cgst_tax + cgst;
                    sgst_tax = sgst_tax + sgst;

                    if (dataList.getAttributes().getPurchase_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("I/GST-ItemWise")) {
                        data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", igst));
                        tax_detailsArray[i] = data;
                    } else if (dataList.getAttributes().getPurchase_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("L/GST-ItemWise")) {
                        data = spaceString6(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", cgst),
                                String.format("%.2f", sgst)
                                /*String.format("%.2f",total_tax)*/ + "\n");
                        tax_detailsArray[i] = data;
                    }

                    if (dataList.getAttributes().getPurchase_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("L/GST-TaxIncl.")) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        }
                    } else {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        }
                    }

                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());

                    total_quantity = total_quantity + dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

                data = spaceString("Net Sale Qty", String.valueOf(total_quantity) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                data = spaceString("Gross Amount", String.valueOf(dataList.getAttributes().getItems_amount()) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
               /* if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                    data = spaceString("IGST Tax",String.format("%.2f",igst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                    data = spaceString("CGST Tax",String.format("%.2f",cgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    data = spaceString("SGST Tax",String.format("%.2f",sgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }*/
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getVoucher_bill_sundries() != null) {
                if (dataList.getAttributes().getVoucher_bill_sundries().size() != 0) {
                    data = "Bill Sundry\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                    m_AemPrinter.setLineFeed(1);
                    for (int i = 0; i < dataList.getAttributes().getVoucher_bill_sundries().size(); i++) {
                        if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_type().equals("Additive")) {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)              " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        } else {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)         " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        }
                        m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    }
                    m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                }
            }
            data = spaceString2("Final Bill Amount", String.valueOf(dataList.getAttributes().getTotal_amount() + "\n"));
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            if (dataList.getAttributes().getPurchase_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("I/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "           " + "Taxable" + "     " + "GST(%)" + "          " + "IGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "           " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i];
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getPurchase_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("L/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "         " + "Taxable" + "   " + "GST(%)" + "     " + "CGST" + "     " + "SGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "         " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i] + "\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            m_AemPrinter.setLineFeed(1);

            data = "Terms And Conditions:-\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            data = "1. All the prices are inclusive of GST at applicable rates.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "2. No exchange is allowed for bags and accessories.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "3. No Warranty, No Exchange, And No Return.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "4. Exchange till 7 days with bill.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            m_AemPrinter.setLineFeed(1);
            data = "Powered by m-Billing\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
            m_AemPrinter.setLineFeed(2);

        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
        }
    }
    public static void saleReturnVoucherReceipt(Context context, SaleReturnVoucherDetailsData dataList) {
        AppUser appUser;
        String encoding = "US-ASCII";
        String[] tax_detailsArray;
        appUser = LocalRepositories.getAppUser(context);
        if (m_AemPrinter == null) {
            Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String line, data;
            line = "------------------------------------------------";
            data = "INVOICE\n";
            m_AemPrinter.writeWithFormatOnly(new Formatter().bold().width().get(), Formatter.centerAlign());
            m_AemPrinter.POS_S_TextOutThreeInch(data, encoding, 0, 1, 0, nFontSize, nFontStyle);

            data = appUser.company_name + "\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            data = appUser.address+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.city + "(" + appUser.company_state + ")" + ",PIN NO "+appUser.companyzipcode+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            data = appUser.comapny_phone_number+"\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            if (appUser.gst!=null && !appUser.gst.equals("")){
                data = "GSTIN: "+appUser.gst+"\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
                // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            }
            m_AemPrinter.setLineFeed(1);
            data = "Invoice Details\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            data = spaceString(dataList.getAttributes().getAccount_master(), "Date: " + dataList.getAttributes().getDate() + "\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = spaceString("Bill No: " + dataList.getAttributes().getVoucher_number(), "Time: " + dataList.getAttributes().getTime()+"\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            tax_detailsArray = new String[dataList.getAttributes().getVoucher_items().size()];
            if (dataList.getAttributes().getVoucher_items().size() != 0) {
                data = "ITEM NAME/" + "      " + "Qty" + "        " + "Price" + "        " + "Net Amt" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "HSN Code\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                int total_quantity = 0;
                Double igst_tax = 0.0, cgst_tax = 0.0, sgst_tax = 0.0;
                for (int i = 0; i < dataList.getAttributes().getVoucher_items().size(); i++) {
                    Double taxable_amount = 0.0, igst = 0.0, cgst = 0.0, sgst = 0.0, total_tax = 0.0;
                    Double amount = 0.0, item_wise = 0.0;
                    int tax_catg = 0;
                    if (dataList.getAttributes().getVoucher_items().get(i).getTax_category() != null) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Exempt") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("ZeroRated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Zero Rated") ||
                                dataList.getAttributes().getVoucher_items().get(i).getTax_category().equals("Nil Rated")) {
                            tax_catg = 0;
                        } else {
                            String[] arr = dataList.getAttributes().getVoucher_items().get(i).getTax_category().split(" ");
                            String[] arr1 = arr[1].split("%");
                            tax_catg = Integer.valueOf(arr1[0]);
                        }
                    }
                    if (dataList.getAttributes().getPurchase_type().equals("I/GST-ItemWise") || dataList.getAttributes().getPurchase_type().equals("L/GST-ItemWise")) {
                        amount = dataList.getAttributes().getVoucher_items().get(i).getRate_item() * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                        taxable_amount = amount;
                    } else {
                        amount = (dataList.getAttributes().getVoucher_items().get(i).getRate_item() / ((100 + tax_catg)) * 100);
                        taxable_amount = amount * dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                    }

                    igst = dataList.getAttributes().getVoucher_items().get(i).getTotal_amount() - taxable_amount;
                    cgst = igst / 2;
                    sgst = igst / 2;
                    total_tax = igst;
                    igst_tax = igst_tax + total_tax;
                    cgst_tax = cgst_tax + cgst;
                    sgst_tax = sgst_tax + sgst;

                    if (dataList.getAttributes().getPurchase_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("I/GST-ItemWise")) {
                        data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", igst));
                        tax_detailsArray[i] = data;
                    } else if (dataList.getAttributes().getPurchase_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("L/GST-ItemWise")) {
                        data = spaceString6(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                String.format("%.2f", taxable_amount),
                                String.valueOf(tax_catg + "%"),
                                String.format("%.2f", cgst),
                                String.format("%.2f", sgst)
                                /*String.format("%.2f",total_tax)*/ + "\n");
                        tax_detailsArray[i] = data;
                    }

                    if (dataList.getAttributes().getPurchase_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("L/GST-TaxIncl.")) {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f",/*amount*/dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()) + "\n");
                        }
                    } else {
                        if (dataList.getAttributes().getVoucher_items().get(i).getHsn_number() != null) {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem() + "/" + dataList.getAttributes().getVoucher_items().get(i).getHsn_number(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        } else {
                            data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                                    String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getRate_item()),
                                    String.format("%.2f", dataList.getAttributes().getVoucher_items().get(i).getPrice_after_discount()) + "\n");
                        }
                    }

                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());

                    total_quantity = total_quantity + dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

                data = spaceString("Net Sale Qty", String.valueOf(total_quantity) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                data = spaceString("Gross Amount", String.valueOf(dataList.getAttributes().getItems_amount()) + "\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
               /* if (dataList.getAttributes().getSale_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("I/GST-ItemWise")) {
                    data = spaceString("IGST Tax",String.format("%.2f",igst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                if (dataList.getAttributes().getSale_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getSale_type().equals("L/GST-ItemWise")) {
                    data = spaceString("CGST Tax",String.format("%.2f",cgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    data = spaceString("SGST Tax",String.format("%.2f",sgst_tax)+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }*/
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getVoucher_bill_sundries() != null) {
                if (dataList.getAttributes().getVoucher_bill_sundries().size() != 0) {
                    data = "Bill Sundry\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                    m_AemPrinter.setLineFeed(1);
                    for (int i = 0; i < dataList.getAttributes().getVoucher_bill_sundries().size(); i++) {
                        if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_type().equals("Additive")) {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Add :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)              " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        } else {
                            if (dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage().equals(0.0)) {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() + "(Rs.)",
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");

                            } else {
                                data = spaceString("Less :" + dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature() +
                                                "(%)         " + dataList.getAttributes().getVoucher_bill_sundries().get(i).getPercentage(),
                                        dataList.getAttributes().getVoucher_bill_sundries().get(i).getAmount() + "\n");
                            }
                        }
                        m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    }
                    m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                }
            }
            data = spaceString2("Final Bill Amount", String.valueOf(dataList.getAttributes().getTotal_amount() + "\n"));
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            if (dataList.getAttributes().getPurchase_type().equals("I/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("I/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "           " + "Taxable" + "     " + "GST(%)" + "          " + "IGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "           " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i];
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getPurchase_type().equals("L/GST-TaxIncl.") || dataList.getAttributes().getPurchase_type().equals("L/GST-ItemWise")) {
                data = "Tax Detail\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                data = "ITEM" + "         " + "Taxable" + "   " + "GST(%)" + "     " + "CGST" + "     " + "SGST" + "\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                data = "NAME" + "         " + "Amount\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                for (int i = 0; i < tax_detailsArray.length; i++) {
                    data = tax_detailsArray[i] + "\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            m_AemPrinter.setLineFeed(1);

            data = "Terms And Conditions:-\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            data = "1. All the prices are inclusive of GST at applicable rates.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "2. No exchange is allowed for bags and accessories.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "3. No Warranty, No Exchange, And No Return.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            data = "4. Exchange till 7 days with bill.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.leftAlign());
            m_AemPrinter.setLineFeed(1);
            data = "Powered by m-Billing\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().small().get(), Formatter.rightAlign());
            m_AemPrinter.setLineFeed(2);

        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
        }
    }


    public static String spaceString(String str1, String str2) {
        int strLength1 = 0, strLength2 = 0, maxLen = 48;
        String NewString = "";
        char ch;
        strLength1 = str1.length();
        strLength2 = str2.length();
        NewString = str1;

        for (int i = strLength1; i < maxLen - strLength2; i++) {

            NewString = NewString + ' ';

        }
        return NewString + str2;

    }

    public static String spaceString2(String str1, String str2) {
        int strLength1 = 0, strLength2 = 0, maxLen = 48;
        String NewString = "";
        char ch;
        strLength1 = str1.length();
        strLength2 = str2.length();
        NewString = str1;

        for (int i = strLength1; i < maxLen - strLength2; i++) {

            NewString = NewString + ' ';

        }
        return NewString + str2;

    }

    public static String spaceString4(String str1, String str2, String str3, String str4) {
        int strLength1 = 0, strLength2 = 0, strLength3 = 0, strLength4 = 0, maxLen1 = 19, maxLen2 = 33, maxLen3 = 48;
        String NewString = "";
        char ch;
        strLength1 = str1.length();
        strLength2 = str2.length();
        strLength3 = str3.length();
        strLength4 = str4.length();
        NewString = str1;

        for (int i = strLength1; i < maxLen1 - strLength2; i++) {
            NewString = NewString + ' ';
        }
        NewString = NewString + str2;
        for (int i = NewString.length(); i < maxLen2 - strLength3; i++) {
            NewString = NewString + ' ';
        }
        NewString = NewString + str3;
        for (int i = NewString.length(); i < maxLen3 - strLength4; i++) {
            NewString = NewString + ' ';
        }

        return NewString + str4;

    }

    public static String spaceString6(String str1, String str2, String str3, String str4, String str5) {
        int strLength1 = 0, strLength2 = 0, strLength3 = 0, strLength4 = 0, strLength5 = 0, strLength6 = 0, maxLen1 = 19, maxLen2 = 28, maxLen3 = 38, maxLen4 = 48;
        String NewString = "";
        strLength1 = str1.length();
        strLength2 = str2.length();
        strLength3 = str3.length();
        strLength4 = str4.length();
        strLength5 = str5.length();
        NewString = str1;

        for (int i = strLength1; i < maxLen1 - strLength2; i++) {
            NewString = NewString + ' ';
        }
        NewString = NewString + str2;
        for (int i = NewString.length(); i < maxLen2 - strLength3; i++) {
            NewString = NewString + ' ';
        }
        NewString = NewString + str3;
        for (int i = NewString.length(); i < maxLen3 - strLength4; i++) {
            NewString = NewString + ' ';
        }

        NewString = NewString + str4;
        for (int i = NewString.length(); i < maxLen4 - strLength5; i++) {
            NewString = NewString + ' ';
        }

        return NewString + str5;

    }

}
