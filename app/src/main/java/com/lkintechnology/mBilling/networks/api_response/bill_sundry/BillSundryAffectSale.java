package com.lkintechnology.mBilling.networks.api_response.bill_sundry;

public class BillSundryAffectSale {
    public String affect_sale_amount_specify_in;
    public Boolean affect_accounting;

    public Boolean getAffect_sale_amount() {
        return affect_sale_amount;
    }

    public void setAffect_sale_amount(Boolean affect_sale_amount) {
        this.affect_sale_amount = affect_sale_amount;
    }

    public String getAffect_sale_amount_specify_in() {
        return affect_sale_amount_specify_in;
    }

    public void setAffect_sale_amount_specify_in(String affect_sale_amount_specify_in) {
        this.affect_sale_amount_specify_in = affect_sale_amount_specify_in;
    }

    public Boolean getAffect_accounting() {
        return affect_accounting;
    }

    public void setAffect_accounting(Boolean affect_accounting) {
        this.affect_accounting = affect_accounting;
    }

    public Boolean getAdjust_in_party_amount() {
        return adjust_in_party_amount;
    }

    public void setAdjust_in_party_amount(Boolean adjust_in_party_amount) {
        this.adjust_in_party_amount = adjust_in_party_amount;
    }

    public Boolean getPost_over_above() {
        return post_over_above;
    }

    public void setPost_over_above(Boolean post_over_above) {
        this.post_over_above = post_over_above;
    }

    public String getAccount_head_to_post_sale_amount() {
        return account_head_to_post_sale_amount;
    }

    public void setAccount_head_to_post_sale_amount(String account_head_to_post_sale_amount) {
        this.account_head_to_post_sale_amount = account_head_to_post_sale_amount;
    }

    public String getAccount_head_to_post_party_amount() {
        return account_head_to_post_party_amount;
    }

    public void setAccount_head_to_post_party_amount(String account_head_to_post_party_amount) {
        this.account_head_to_post_party_amount = account_head_to_post_party_amount;
    }

    public String getParty_amount_specify_in() {
        return party_amount_specify_in;
    }

    public void setParty_amount_specify_in(String party_amount_specify_in) {
        this.party_amount_specify_in = party_amount_specify_in;
    }

    public Boolean affect_sale_amount;
    public Boolean adjust_in_party_amount;
    public Boolean post_over_above;
    public String account_head_to_post_sale_amount;
    public String account_head_to_post_party_amount;
    public String party_amount_specify_in;


}