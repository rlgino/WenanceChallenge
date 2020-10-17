package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.repository.BitcoinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BitcoinsFinder {
    @Autowired
    BitcoinsRepository repository;

    /**
     * This method find the bitcoin price in a selected date
     * @param date selected
     * @return the BitcoinSnapshot in option because maybe not exists
     */
    public Optional<BitcoinSnapshot> findBitcoinPrice(final Date date) {
        final Iterable<BitcoinSnapshot> bitcoinData = this.repository.findAll();
        Stream<BitcoinSnapshot> mainStream = StreamSupport.stream(bitcoinData.spliterator(), false);

        return mainStream
                .filter(snapshot -> snapshot.getCreationDateTime().getTime() == date.getTime())
                .findAny();
    }

    /**
     * Find the maximum price of the bitcoin between 2 dates
     * @param dateFrom
     * @param dateTo
     * @return the bitcoinSnapshot in the first date of the array
     */
    public Optional<BitcoinSnapshot> findMaxBitcoinPrice(final Date dateFrom, final Date dateTo){
        final Iterable<BitcoinSnapshot> bitcoinData = this.repository.findAll();
        Stream<BitcoinSnapshot> mainStream = StreamSupport.stream(bitcoinData.spliterator(), false);

        return mainStream
                .filter(snapshot -> snapshot.getCreationDateTime().getTime() > dateFrom.getTime() && snapshot.getCreationDateTime().getTime() < dateTo.getTime())
                .max(Comparator.comparing(BitcoinSnapshot::getPrice));
    }

    /**
     * Find the average price between two dates
     * @param dateFrom
     * @param dateTo
     * @return the average price, but it don't exist, the result is null
     */
    public BigDecimal findBitcoinAveragePrice(final Date dateFrom, final Date dateTo){
        final Iterable<BitcoinSnapshot> bitcoinData = this.repository.findAll();

        final long count = StreamSupport.stream(bitcoinData.spliterator(), false)
                            .filter(snapshot -> snapshot.getCreationDateTime().getTime() > dateFrom.getTime() && snapshot.getCreationDateTime().getTime() < dateTo.getTime())
                            .count();
        if (count == 0)
            return null;

        Stream<BitcoinSnapshot> mainStream = StreamSupport.stream(bitcoinData.spliterator(), false);


        final BigDecimal accumulator = mainStream
                .filter(snapshot -> snapshot.getCreationDateTime().getTime() > dateFrom.getTime() && snapshot.getCreationDateTime().getTime() < dateTo.getTime())
                .map(BitcoinSnapshot::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return accumulator.divide(new BigDecimal(count), 2, RoundingMode.DOWN);
    }

}
