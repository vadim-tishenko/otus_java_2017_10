package ru.cwl.bankimport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vadim.tishenko
 * on 15.10.2017 19:43.
 * Импорт выписки по карте банка Тинькофф.
 */
public class CsvImport {
    final CSVFormat csvFormat = CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader();
    final Charset charset = Charset.forName("windows-1251");

    public List<CardOperation> readFromFile(InputStream in) {
        ArrayList<CardOperation> result = new ArrayList<>();

        try {
            CSVParser parser = CSVParser.parse(in, charset, csvFormat);
            final List<CSVRecord> records = parser.getRecords();
            for (CSVRecord r : records) {
                CardOperation o = map(r);
                result.add(o);
            }
            System.out.println(records.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private CardOperation map(CSVRecord r) {
        CardOperation o = new CardOperation();
        o.operDate = readDate(r.get(0));
        o.payDate = readDate(r.get(1));
        o.cardNumber = r.get(2);
        o.status = r.get(3);
        o.sum = readDecimal(r.get(4));
        o.currency = r.get(5);
        o.sumToPay = readDecimal(r.get(6));
        o.curPay = r.get(7);
        o.cashBack = readDecimal(r.get(8));
        o.category = r.get(9);
        o.mcc = r.get(10);
        o.description = r.get(11);
        o.bonus = readDecimal(r.get(12));
        return o;
    }

    BigDecimal readDecimal(String strNumber) {
        if (strNumber.equals("")) return BigDecimal.ZERO;
        return new BigDecimal(strNumber.replace(',', '.'));
    }

    LocalDateTime readDate(String strDate) {
        if (strDate.equals("")) {
            return null;
        }
        DateTimeFormatter f = DateTimeFormatter.ofPattern("d.MM.yyyy HH:mm:ss");
        return LocalDateTime.parse(strDate, f);
    }
}
