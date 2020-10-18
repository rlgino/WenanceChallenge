package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.jobs.BitcoinsCollectorJob;
import com.wenance.WenanceChallenge.dto.CEXResponse;
import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.repository.BitcoinsRepository;
import com.wenance.WenanceChallenge.services.CEXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class BitcoinsCollector {

    private static final Logger log = LoggerFactory.getLogger(BitcoinsCollectorJob.class);

    @Autowired
    private CEXService cexService;

    @Autowired
    private BitcoinsRepository repository;

    public void collectBitcoinPrice() {
        try {
            final CEXResponse currentValue = cexService.getCurrentValue();
            BitcoinSnapshot snapshot = converResponse(currentValue);
            repository.save(snapshot);
            log.info("Price saved to date: " + snapshot.getCreationDateTime());
        }catch (Exception e){
            log.error("Error collecting btc price: " + e.getMessage());
        }
    }

    private BitcoinSnapshot converResponse(CEXResponse currentValue) {
        final BitcoinSnapshot snapshot = new BitcoinSnapshot();
        snapshot.setCreationDateTime(new Date());
        snapshot.setPrice(currentValue.getLprice());
        return snapshot;
    }
}
