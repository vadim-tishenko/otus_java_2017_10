package ru.cwl.app;

import ru.cwl.bankimport.CardOperation;
import ru.cwl.bankimport.CsvImport;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 15.10.2017 14:01.
 *
 * импорт операций по банковской карте.
 */
public class Main {

    public static void main(String[] args) {

        InputStream data = Main.class.getResourceAsStream("/operations.csv");

        CsvImport ci = new CsvImport();
        List<CardOperation> result = ci.readFromFile(data);
        calculateStatictic(result);

    }

    private static void calculateStatictic(List<CardOperation> result) {
        BigDecimal bonus = BigDecimal.ZERO;
        BigDecimal popolnenie = BigDecimal.ZERO;
        BigDecimal trata = BigDecimal.ZERO;
        for (CardOperation co : result) {
            BigDecimal sum = co.getSum();
            if (!"OK".equals(co.getStatus())) continue;
            switch (sum.compareTo(BigDecimal.ZERO)) {
                case -1:
                    trata = trata.subtract(sum);
                    break;
                case 0:
                    break;
                case 1:
                    popolnenie = popolnenie.add(sum);
                    break;
            }
            bonus = bonus.add(co.getBomus());

        }

        System.out.printf("пополнения: %s, траты: %s, баланс: %s, бонус: %s\n", popolnenie, trata,
                popolnenie.subtract(trata),bonus);
    }

}

