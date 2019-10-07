package com.tecacet.jflat.excel;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.FlatFileReader;
import com.tecacet.jflat.impl.HeaderBeanMapper;

public class ComplexExcelReaderTest {

    @Test
    void testComplexSpreadsheet() throws IOException {
        String[] header = {"PX Code", "Date", "Description", "Start time", "End time", "Duration"};
        String[] properties = {"identifier", "date", "description", "startTime", "endTime", "duration"};

        BeanMapper<TimeEntry> beanMapper = new HeaderBeanMapper<>(TimeEntry.class, header, properties);
        FlatFileReader<TimeEntry> reader = new ExcelReader<>(beanMapper);

        List<TimeEntry> entries = reader.readAll("Timesheet.xls");
        TimeEntry entry = entries.get(0);
        assertEquals("5018", entry.getIdentifier());
        assertEquals(LocalDate.of(2010, 4, 1), entry.getDate());
        assertEquals("Ad Hoc Meetings", entry.getDescription());
        assertEquals("08:00", entry.getStartTime().toString());
        assertEquals("09:30", entry.getEndTime().toString());
        assertEquals(1.5, entry.getDuration().doubleValue(), .001);
    }
}
