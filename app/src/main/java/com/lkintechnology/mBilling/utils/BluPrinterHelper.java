package com.lkintechnology.mBilling.utils;

import android.content.Context;
import android.widget.Toast;

import com.lkintechnology.mBilling.entities.AppUser;
import com.lkintechnology.mBilling.networks.api_response.salevoucher.SaleVoucherDetailsData;

import java.io.IOException;

import static com.lkintechnology.mBilling.fragments.dashboard.DashboardAccountFragment.m_AemPrinter;

/**
 * Created by abc on 7/5/2018.
 */

public class BluPrinterHelper {

    private static int nFontSize, nTextAlign, nScaleTimesWidth,
            nScaleTimesHeight, nFontStyle, nLineHeight = 32, nRightSpace;

    public static void forGSTReceipt(Context context , SaleVoucherDetailsData dataList) {
        AppUser appUser;
        String encoding = "US-ASCII";
        appUser = LocalRepositories.getAppUser(context);

        if (m_AemPrinter == null) {
            Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            String line, data;
            //line = "________________________________________________";
            line = "------------------------------------------------";
            data = "TAX INVOICE\n";
            m_AemPrinter.writeWithFormatOnly(new Formatter().bold().width().get(), Formatter.centerAlign());
            m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            // m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());


            data = appUser.company_name + "\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);

            data = "sector 29\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);

            data = "faridabad " + "(" + appUser.company_state + ")" + ",PIN NO 121008\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            //m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);

            data = "9711575953\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            //m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);

            data = "GSTIN:\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
            // m_AemPrinter.POS_S_TextOutThreeInch(data,encoding,0,1,0,nFontSize, nFontStyle);
            m_AemPrinter.setLineFeed(1);
            data = "Invoice Details\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());

            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            data = spaceString(dataList.getAttributes().getAccount_master(),"Date: "+dataList.getAttributes().getDate()+"\n");
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = spaceString("Bill No: "+dataList.getAttributes().getVoucher_number(),"Time: 10:55:02 AM\n");
            // m_AemPrinter.setFontType(AEMPrinter.FONT_001);
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            //m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().get(), Formatter.leftAlign());
            m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

            if (dataList.getAttributes().getVoucher_items().size()!=0){
                data = "ITEM NAME"+"        "+"Qty"+"        "+"Price"+"        "+"Net Amt"+"\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.centerAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                int total_quantity = 0;
                Double total_amount = 0.0;
                for (int i=0;i<dataList.getAttributes().getVoucher_items().size();i++){
                    data = spaceString4(dataList.getAttributes().getVoucher_items().get(i).getItem(),
                            String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getQuantity()),
                            String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getTotal_amount()),
                            String.valueOf(dataList.getAttributes().getVoucher_items().get(i).getTotal_amount())+"\n");
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());

                    total_quantity = total_quantity + dataList.getAttributes().getVoucher_items().get(i).getQuantity();
                    total_amount = total_amount + dataList.getAttributes().getVoucher_items().get(i).getTotal_amount();
                }
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());

                data = spaceString("Net Sale Qty",String.valueOf(total_quantity)+"\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                data = spaceString("Gross Amount",String.valueOf(total_amount)+"\n");
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                data = spaceString2("Final Bill Amount",String.valueOf(dataList.getAttributes().getItems_amount()+"\n"));
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
                m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
            }

            if (dataList.getAttributes().getVoucher_bill_sundries().size()!=0){
                data = "Bill Sundry\n";
                m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().underlined().get(), Formatter.leftAlign());
                m_AemPrinter.setLineFeed(1);
                for (int i=0;i<dataList.getAttributes().getVoucher_bill_sundries().size();i++){
                    data = "Add :"+dataList.getAttributes().getVoucher_bill_sundries().get(i).getBill_sundry_nature()+"\n";
                    m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
                    m_AemPrinter.writeWithFormat(line.getBytes(), new Formatter().normal().get(), Formatter.centerAlign());
                }
            }

            data = "Terms And Conditions:-\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().bold().width().get(), Formatter.leftAlign());
            data = "1. All the prices are inclusive of GST at applicable rates.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = "2. No exchange is allowed for bags and accessories.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = "3. No Warranty, No Exchange, And No Return.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            data = "4. Exchange till 7 days with bill.\n";
            m_AemPrinter.writeWithFormat(data.getBytes(), new Formatter().normal().get(), Formatter.leftAlign());
            m_AemPrinter.setLineFeed(2);

        } catch (IOException e) {
            if (e.getMessage().contains("socket closed"))
                Toast.makeText(context, "Printer not connected", Toast.LENGTH_SHORT).show();
        }
    }

    public static String spaceString(String str1 , String str2) {
        int strLength1 = 0,strLength2 = 0, maxLen = 48;
        String NewString = "";
        char ch;
        strLength1 = str1.length();
        strLength2 = str2.length();
        NewString = str1;

        for (int i = strLength1; i <maxLen-strLength2; i++) {

            NewString = NewString + ' ';

        }
        return NewString +str2;

    }

    public static String spaceString4(String str1 , String str2, String str3, String str4) {
        int strLength1 = 0,strLength2 = 0,strLength3 = 0,strLength4 = 0, maxLen1 = 16,maxLen2 = 32,maxLen3 = 48;
        String NewString = "";
        char ch;
        strLength1 = str1.length();
        strLength2 = str2.length();
        strLength3 = str3.length();
        strLength4 = str4.length();
        NewString = str1;

        for (int i = strLength1; i <maxLen1-strLength2; i++) {
            NewString = NewString + ' ';
        }
        NewString = NewString + str2;
        for (int i = NewString.length(); i <maxLen2-strLength3; i++) {
            NewString = NewString + ' ';
        }
        NewString = NewString + str3;
        for (int i = NewString.length(); i <maxLen3-strLength4; i++) {
            NewString = NewString + ' ';
        }

        return NewString +str4;

    }

    public static String spaceString2(String str1 , String str2) {
        int strLength1 = 0,strLength2 = 0, maxLen = 48;
        String NewString = "";
        char ch;
        strLength1 = str1.length();
        strLength2 = str2.length();
        NewString = str1;

        for (int i = strLength1; i <maxLen-strLength2; i++) {

            NewString = NewString + ' ';

        }
        return NewString +str2;

    }
}
