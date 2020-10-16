package com.wenance.WenanceChallenge.jobs;

import com.wenance.WenanceChallenge.business.BitcoinsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BitcoinsCollectorJob {
    private static final Logger log = LoggerFactory.getLogger(BitcoinsCollectorJob.class);

    @Autowired
    private BitcoinsCollector bitcoinsCollector;

    @Scheduled(fixedRate = 10000) // Execution to 10 seconds
    public void findBitcoinPrice(){
        bitcoinsCollector.collectBitcoinPrice();
    }
}
