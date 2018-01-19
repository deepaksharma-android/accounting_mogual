package com.lkintechnology.mBilling.networks.api_response.companydashboardinfo;

public class CompanyDetailsAttributes {
    public int id;
    public Double cash_in_hand;
    public Double bank_account;
    public Double customer;
    public Double supplier;
    public Double stock_in_hand;
    public Double sales;
    public Double profit_loss;

    public Double getProfit_loss() {
        return profit_loss;
    }

    public void setProfit_loss(Double profit_loss) {
        this.profit_loss = profit_loss;
    }

    public Double getSales() {
        return sales;
    }

    public void setSales(Double sales) {
        this.sales = sales;
    }

    public Double getExpenses() {
        return expenses;
    }

    public void setExpenses(Double expenses) {
        this.expenses = expenses;
    }

    public Double expenses;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getCash_in_hand() {
        return cash_in_hand;
    }

    public void setCash_in_hand(Double cash_in_hand) {
        this.cash_in_hand = cash_in_hand;
    }

    public Double getBank_account() {
        return bank_account;
    }

    public void setBank_account(Double bank_account) {
        this.bank_account = bank_account;
    }

    public Double getCustomer() {
        return customer;
    }

    public void setCustomer(Double customer) {
        this.customer = customer;
    }

    public Double getSupplier() {
        return supplier;
    }

    public void setSupplier(Double supplier) {
        this.supplier = supplier;
    }

    public Double getStock_in_hand() {
        return stock_in_hand;
    }

    public void setStock_in_hand(Double stock_in_hand) {
        this.stock_in_hand = stock_in_hand;
    }
}