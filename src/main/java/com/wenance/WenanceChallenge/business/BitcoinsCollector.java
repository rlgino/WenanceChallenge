package com.wenance.WenanceChallenge.business;

import com.wenance.WenanceChallenge.jobs.BitcoinsCollectorJob;
import com.wenance.WenanceChallenge.model.CEXResponse;
import com.wenance.WenanceChallenge.services.CEXService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BitcoinsCollector {

    private static final Logger log = LoggerFactory.getLogger(BitcoinsCollectorJob.class);

    @Autowired
    private CEXService cexService;

    public void collectBitcoinPrice() {
        final CEXResponse currentValue = cexService.getCurrentValue();
        log.info("The value is: " + currentValue.getLprice().toString());
    }
}
