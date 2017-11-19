package com.berylsystems.buzz.networks.api_response.bill_sundry;

public class BillSundryAffectPurchase {
    public String party_amount_specify_in;
    public String account_head_to_post_purchase_amount;

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

    public String getAccount_head_to_post_purchase_amount() {
        return account_head_to_post_purchase_amount;
    }

    public void setAccount_head_to_post_purchase_amount(String account_head_to_post_purchase_amount) {
        this.account_head_to_post_purchase_amount = account_head_to_post_purchase_amount;
    }

    public String getAffect_purchase_amount_specify_in() {
        return affect_purchase_amount_specify_in;
    }

    public void setAffect_purchase_amount_specify_in(String affect_purchase_amount_specify_in) {
        this.affect_purchase_amount_specify_in = affect_purchase_amount_specify_in;
    }

    public Boolean getAffect_accounting() {
        return affect_accounting;
    }

    public void setAffect_accounting(Boolean affect_accounting) {
        this.affect_accounting = affect_accounting;
    }

    public Boolean getAffect_purchase_amount() {
        return affect_purchase_amount;
    }

    public void setAffect_purchase_amount(Boolean affect_purchase_amount) {
        this.affect_purchase_amount = affect_purchase_amount;
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

    public String account_head_to_post_party_amount;
    public String affect_purchase_amount_specify_in;
    public Boolean affect_accounting;
    public Boolean affect_purchase_amount;
    public Boolean adjust_in_party_amount;
    public Boolean post_over_above;
}