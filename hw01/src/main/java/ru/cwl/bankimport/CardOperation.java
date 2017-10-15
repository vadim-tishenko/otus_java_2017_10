package ru.cwl.bankimport;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by vadim.tishenko
 * on 15.10.2017 19:34.
 */
public class CardOperation {
    /*
    "Дата операции";"Дата платежа";
    "Номер карты";"Статус";
    "Сумма операции";"Валюта операции";
    "Сумма платежа";"Валюта платежа";
    "Кэшбэк";"Категория";
    "MCC";"Описание";
    "Бонусы (включая кэшбэк)"

     */
    LocalDateTime operDate;
    LocalDateTime payDate;
    String cardNumber;
    String status;
    BigDecimal sum;
    String currency;
    BigDecimal sumToPay;
    String curPay;
    BigDecimal cashbak;
    String category;
    String mcc;
    String description;
    BigDecimal bomus;

    public LocalDateTime getOperDate() {
        return operDate;
    }

    public void setOperDate(LocalDateTime operDate) {
        this.operDate = operDate;
    }

    public LocalDateTime getPayDate() {
        return payDate;
    }

    public void setPayDate(LocalDateTime payDate) {
        this.payDate = payDate;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getSumToPay() {
        return sumToPay;
    }

    public void setSumToPay(BigDecimal sumToPay) {
        this.sumToPay = sumToPay;
    }

    public String getCurPay() {
        return curPay;
    }

    public void setCurPay(String curPay) {
        this.curPay = curPay;
    }

    public BigDecimal getCashbak() {
        return cashbak;
    }

    public void setCashbak(BigDecimal cashbak) {
        this.cashbak = cashbak;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getBomus() {
        return bomus;
    }

    public void setBomus(BigDecimal bomus) {
        this.bomus = bomus;
    }

    @Override
    public String toString() {
        return "CardOperation{" +
                "operDate=" + operDate +
                ", payDate=" + payDate +
                ", cardNumber='" + cardNumber + '\'' +
                ", status='" + status + '\'' +
                ", sum=" + sum +
                ", currency='" + currency + '\'' +
                ", sumToPay=" + sumToPay +
                ", curPay='" + curPay + '\'' +
                ", cashbak=" + cashbak +
                ", category='" + category + '\'' +
                ", mcc='" + mcc + '\'' +
                ", description='" + description + '\'' +
                ", bomus=" + bomus +
                '}';
    }
}
