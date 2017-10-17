package ru.cwl.app;

import ru.cwl.bankimport.CardOperation;
import ru.cwl.bankimport.CsvImport;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 15.10.2017 14:01.
 */
public class Main {
    public static void main(String[] args) {

        InputStream data = Main.class.getResourceAsStream("/operations.csv");

        CsvImport ci = new CsvImport();
        List<CardOperation> result = ci.readFromFile(data);
        calculateStatistic(result);


    }

    private static void calculateStatistic(List<CardOperation> result) {
        BigDecimal bonus = BigDecimal.ZERO;
        BigDecimal refill = BigDecimal.ZERO;
        BigDecimal expenditure = BigDecimal.ZERO;
        for (CardOperation co : result) {
            BigDecimal sum = co.getSum();
            if (!"OK".equals(co.getStatus())) continue;
            switch (sum.compareTo(BigDecimal.ZERO)) {
                case -1:
                    expenditure = expenditure.subtract(sum);
                    break;
                case 0:
                    break;
                case 1:
                    refill = refill.add(sum);
                    break;
            }
            bonus = bonus.add(co.getBonus());

        }

        System.out.printf("пополнения: %s, траты: %s, баланс: %s, bonus: %s\n", refill, expenditure
                , refill.subtract(expenditure), bonus);
    }


}

