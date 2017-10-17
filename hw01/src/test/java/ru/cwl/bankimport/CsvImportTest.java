package ru.cwl.bankimport;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by vadim.tishenko
 * on 15.10.2017 20:04.
 */
public class CsvImportTest {

    @Test
    public void readDate() throws Exception {
        CsvImport ci = new CsvImport();

        assertNull(ci.readDate(""));
        assertEquals(LocalDateTime.of(2017, 10, 14,0,0,0), ci.readDate("14.10.2017 00:00:00"));
        assertEquals(LocalDateTime.of(2017, 10, 14, 10,14,19), ci.readDate("14.10.2017 10:14:19"));

    }

}