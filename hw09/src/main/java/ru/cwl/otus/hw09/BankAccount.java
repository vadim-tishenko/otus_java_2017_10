package ru.cwl.otus.hw09;

/**
 * Created by vadim.tishenko
 * on 03.01.2018 14:06.
 */
public class BankAccount extends DataSet {

    String account;
    int amount;

    public BankAccount() {
    }

    public BankAccount(String account, int amount) {
        this.account = account;
        this.amount = amount;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BankAccount{" +
                "account='" + account + '\'' +
                ", amount=" + amount +
                ", id=" + id +
                '}';
    }
}
