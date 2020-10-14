package com.wenance.WenanceChallenge.controller;

import com.wenance.WenanceChallenge.business.BitcoinsFinder;
import com.wenance.WenanceChallenge.dto.ChallengeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class BitcoinsController {
    @Autowired
    private BitcoinsFinder bitcoinsFinder;

    @GetMapping(path = "/", produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPrinceOnDate(@RequestBody ChallengeRequest bodyRequest){
        try{
            final Date now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse("2020-10-14 18:02:34.563");
            final BigDecimal result = bitcoinsFinder.findBitcoinPrice(now);
            return new ResponseEntity<>("The bitcoin price this moment was: " + result.toString(), HttpStatus.OK);
        } catch (ParseException e){
            return new ResponseEntity<>("Error in data input: ", HttpStatus.BAD_REQUEST);
        }

    }
}
