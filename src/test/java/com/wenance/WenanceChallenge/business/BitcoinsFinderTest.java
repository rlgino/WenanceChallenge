package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.repository.BitcoinsRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BitcoinsFinderTest {
    @Mock
    BitcoinsRepository repository;

    @InjectMocks
    BitcoinsFinder finder;

    @Test
    public void findBitcoinPriceTest_ShouldPass() throws ParseException {
        // Setup
        final Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2020-10-15 16:18:53.561");

        final BitcoinSnapshot snapshot1 = new BitcoinSnapshot();
        snapshot1.setCreationDateTime(new Date());
        snapshot1.setPrice(new BigDecimal(1));
        final BitcoinSnapshot snapshot2 = new BitcoinSnapshot();
        snapshot2.setCreationDateTime(date);
        snapshot2.setPrice(new BigDecimal(3));
        final BitcoinSnapshot snapshot3 = new BitcoinSnapshot();
        snapshot3.setCreationDateTime(new Date());
        snapshot3.setPrice(new BigDecimal(2));
        final List<BitcoinSnapshot> snapshotsList = Arrays.asList(snapshot1, snapshot2, snapshot3);

        when(repository.findAll()).thenReturn(snapshotsList);

        // Execute
        final Optional<BitcoinSnapshot> bitcoinPrice = finder.findBitcoinPrice(date);

        // Verify
        assertThat(bitcoinPrice.isPresent()).isTrue();
        assertThat(bitcoinPrice.get().getPrice()).isEqualTo(new BigDecimal(3));
    }
}
