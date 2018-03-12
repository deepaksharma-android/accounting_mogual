package com.lkintechnology.mBilling.networks.api_response.journalvoucher;

import java.util.ArrayList;

/**
 * Created by abc on 3/12/2018.
 */

public class DetailsJournalItem {
    public ArrayList<DetailsJournalItemData> data;

    public ArrayList<DetailsJournalItemData> getData() {
        return data;
    }

    public void setData(ArrayList<DetailsJournalItemData> data) {
        this.data = data;
    }
}
