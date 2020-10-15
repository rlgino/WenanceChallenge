package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.repository.BitcoinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class BitcoinsFinder {
    @Autowired
    BitcoinsRepository repository;

    public BigDecimal findBitcoinPrice(final Date date){
        final Iterator<BitcoinSnapshot> bitcoinsData = this.repository.findAll().iterator();
        Stream<BitcoinSnapshot> targetStream = StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(bitcoinsData, Spliterator.ORDERED),
                false);
        final Optional<BitcoinSnapshot> result = targetStream.filter(snapshot -> snapshot.getCreationDateTime().getTime() == date.getTime()).findAny();

        if (result.isPresent())
            return result.get().getPrice();;
        return BigDecimal.ZERO;
    }

    public BigDecimal findBitcoinPrice(Date from, Date to){
        return BigDecimal.ZERO;
    }
}
