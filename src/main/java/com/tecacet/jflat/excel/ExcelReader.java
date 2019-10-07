package com.tecacet.jflat.excel;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Stream;
import com.tecacet.jflat.BeanMapper;
import com.tecacet.jflat.FlatFileReader;
import com.tecacet.jflat.FlatFileReaderCallback;
import com.tecacet.jflat.RowRecord;
import com.tecacet.jflat.converters.LocalDateConverter;
import com.tecacet.jflat.converters.LocalDateTimeConverter;
import com.tecacet.jflat.impl.AbstractFlatFileReader;
import com.tecacet.jflat.impl.GenericBeanMapper;
import com.tecacet.jflat.impl.HeaderBeanMapper;

public class ExcelReader<T> extends AbstractFlatFileReader<T> {

    private final ExcelParser parser = new ExcelParser(true); //TODO: support without header
    private final BeanMapper<T> beanMapper;

    public ExcelReader(BeanMapper<T> beanMapper) {
        this.beanMapper = beanMapper;
        this.registerConverter(LocalDate.class, new LocalDateConverter(CellMapper.DEFAULT_DATETIME_FORMAT));
        this.registerConverter(LocalDateTime.class, new LocalDateTimeConverter(CellMapper.DEFAULT_DATETIME_FORMAT));
    }

    @Override
    public void read(InputStream is, FlatFileReaderCallback<T> callback) throws IOException {
        Iterable<RowRecord> records = parser.parse(is);
        for (RowRecord record : records) {
            T bean = beanMapper.apply(record);
            callback.accept(record, bean);
        }
    }

    @Override
    public Stream<T> readAsStream(Reader reader) throws UnsupportedOperationException {
        //TODO: is it possible?
        throw new UnsupportedOperationException("Method not supported for excel");
    }

    @Override
    public <S> FlatFileReader<T> registerConverter(String property, Function<String, S> converter) {
        if (beanMapper instanceof GenericBeanMapper) {
            ((GenericBeanMapper) beanMapper).registerConverter(property, converter);
        }
        return this;
    }

    public static <T> ExcelReader<T> createWithHeaderMapping(Class<T> type,
                                                             String[] header,
                                                             String[] properties) {
        return new ExcelReader<>(new HeaderBeanMapper<>(type, header, properties));
    }

}
