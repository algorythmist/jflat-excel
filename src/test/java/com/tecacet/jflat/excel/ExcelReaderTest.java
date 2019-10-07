package com.tecacet.jflat.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.converters.LocalDateConverter;
import com.tecacet.jflat.impl.HeaderBeanMapper;
import com.tecacet.jflat.impl.IndexBeanMapper;


public class ExcelReaderTest {

    @Test
    public void readNewFormat() throws IOException {
        test("GLD.xlsx");
    }

    @Test
    public void readOldFormat() throws IOException {
        test("GLD.xls");
    }

    @Test
    public void readWithHeaderMap() throws IOException {
        test("GLD.xlsx", new HeaderBeanMapper<>(Quote.class,
                new String[]{"Date", "Open", "Close", "Volume"},
                new String[]{"date", "open", "close", "volume"}));

    }

    private void test(String filename) throws IOException {
        test(filename, new IndexBeanMapper<>(Quote.class,
                new String[]{"date", "open", null, null, "close", "volume", null}));
    }

    private void test(String filename, BeanMapper<Quote> beanMapper) throws IOException {
        ExcelReader<Quote> reader = new ExcelReader<>(beanMapper);
        List<Quote> rows = reader.readAll(filename);
        assertEquals(134, rows.size());
        Quote firstRow = rows.get(0);
        assertEquals("Quote1 [date=2015-12-01, open=102.3, close=101.46, volume=5800200]",
                firstRow.toString());
    }
}
