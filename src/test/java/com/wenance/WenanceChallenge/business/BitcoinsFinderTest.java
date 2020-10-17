package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.repository.BitcoinsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BitcoinsFinderTest {
    private final String pattern = "yyyy-MM-dd HH:mm:ss.SSS";
    private final String mockDate1 = "2020-10-15 16:18:53.561";
    private final String mockDate2 = "2020-10-15 16:19:53.561";
    private final String mockDate3 = "2020-10-15 16:20:53.561";

    @Mock
    BitcoinsRepository repository;

    @InjectMocks
    BitcoinsFinder finder;

    @BeforeEach
    public void setup() throws ParseException {
        final Date date1 = new SimpleDateFormat(pattern).parse(mockDate1);
        final BitcoinSnapshot snapshot1 = new BitcoinSnapshot();
        snapshot1.setCreationDateTime(date1);
        snapshot1.setPrice(new BigDecimal(1));

        final Date date2 = new SimpleDateFormat(pattern).parse(mockDate2);
        final BitcoinSnapshot snapshot2 = new BitcoinSnapshot();
        snapshot2.setCreationDateTime(date2);
        snapshot2.setPrice(new BigDecimal(3));

        final Date date3 = new SimpleDateFormat(pattern).parse(mockDate3);
        final BitcoinSnapshot snapshot3 = new BitcoinSnapshot();
        snapshot3.setCreationDateTime(date3);
        snapshot3.setPrice(new BigDecimal(2));

        final List<BitcoinSnapshot> snapshotsList = Arrays.asList(snapshot1, snapshot2, snapshot3);

        when(repository.findAll()).thenReturn(snapshotsList);
    }

    @Test
    public void findBitcoinPriceTest_ShouldFind() throws ParseException {
        // Setup
        final Date date = new SimpleDateFormat(pattern).parse(mockDate2);

        // Execute
        final Optional<BitcoinSnapshot> bitcoinPrice = finder.findBitcoinPrice(date);

        // Verify
        assertThat(bitcoinPrice.isPresent()).isTrue();
        assertThat(bitcoinPrice.get().getPrice()).isEqualTo(new BigDecimal(3));
    }

    @Test
    public void findBitcoinPriceTest_ShouldnotFind() {
        // Execute
        final Optional<BitcoinSnapshot> bitcoinPrice = finder.findBitcoinPrice(new Date());

        // Verify
        assertThat(bitcoinPrice.isPresent()).isFalse();
    }

    @Test
    public void findBitcoinMaxPriceTest_ShouldFind() throws ParseException {
        // Setup
        final Date dateFrom = new SimpleDateFormat(pattern).parse("2020-10-15 16:18:00.561");
        final Date dateTo = new SimpleDateFormat(pattern).parse("2020-10-15 16:21:00.561");

        // Execute
        final Optional<BitcoinSnapshot> result = finder.findMaxBitcoinPrice(dateFrom, dateTo);

        // Verify
        assertThat(result.isPresent()).isTrue();
        final BitcoinSnapshot bitcoinSnapshot = result.get();
        assertThat(bitcoinSnapshot.getPrice()).isEqualTo(new BigDecimal("3"));
    }

    @Test
    public void findBitcoinMaxPriceTest_ShouldBeNull() throws ParseException {
        // Setup
        final Date dateFrom = new SimpleDateFormat(pattern).parse("2020-10-15 16:18:00.561");
        final Date dateTo = new SimpleDateFormat(pattern).parse("2020-10-15 16:18:00.561");

        // Execute
        final Optional<BitcoinSnapshot> result = finder.findMaxBitcoinPrice(dateFrom, dateTo);

        // Verify
        assertThat(result.isPresent()).isFalse();
    }

    @Test
    public void findBitcoinAveragePriceTest_ShouldFind() throws ParseException {
        // Setup
        final Date dateFrom = new SimpleDateFormat(pattern).parse("2020-10-15 16:18:00.561");
        final Date dateTo = new SimpleDateFormat(pattern).parse("2020-10-15 16:21:00.561");

        // Execute
        final BigDecimal bitcoinAveragePrice = finder.findBitcoinAveragePrice(dateFrom, dateTo);

        // Verify
        assertThat(bitcoinAveragePrice).isEqualTo(new BigDecimal("2.00"));
    }

    @Test
    public void findBitcoinAveragePriceTest_ShouldBeNull() throws ParseException {
        // Setup
        final Date dateFrom = new SimpleDateFormat(pattern).parse("2020-10-15 16:18:00.561");
        final Date dateTo = new SimpleDateFormat(pattern).parse("2020-10-15 16:18:00.561");

        // Execute
        final BigDecimal bitcoinAveragePrice = finder.findBitcoinAveragePrice(dateFrom, dateTo);

        // Verify
        assertThat(bitcoinAveragePrice).isNull();
    }
}
