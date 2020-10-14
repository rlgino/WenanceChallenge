package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.repository.BitcoinsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
public class BitcoinsFinder {
    @Autowired
    BitcoinsRepository repository;

    public BigDecimal findBitcoinPrice(Date date){
        final List<BitcoinSnapshot> result = this.repository.findAll();
        if (!result.isEmpty())
            return result.get(0).getPrice();
        System.out.println("No hay registros");
        return BigDecimal.ZERO;
    }

    public BigDecimal findBitcoinPrice(Date from, Date to){
        return BigDecimal.ZERO;
    }
}
