# jflat-excel
JFlat extension for reading excel files

Read a file with a header:
```java
 ExcelReader<Quote> reader = ExcelReader.createWithHeaderMapping(Quote.class,
                new String[]{"Date", "Open", "Close", "Volume"},
                new String[]{"date", "open", "close", "volume"});
 List<Quote> rows = reader.readAll(filename);

```

Read a file without a header by specifying the order of the properties:
```java
ExcelReader<Quote> reader = ExcelReader.createWithIndexMapping(Quote.class,
                new String[]{"date", "open", "close", "volume"}, false);
 List<Quote> rows = reader.readAll(filename);

```