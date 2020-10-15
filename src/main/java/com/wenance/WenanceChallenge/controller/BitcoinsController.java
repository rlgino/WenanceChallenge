package com.wenance.WenanceChallenge.controller;

import com.wenance.WenanceChallenge.business.BitcoinsFinder;
import com.wenance.WenanceChallenge.dto.ChallengeRequest;
import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
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

    @GetMapping(path = "/find-one", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPriceOnDate(@RequestBody ChallengeRequest bodyRequest){
        if (bodyRequest.getPattern() == null || bodyRequest.getPattern().isEmpty())
            return new ResponseEntity<>("The field patter is required", HttpStatus.BAD_REQUEST);
        if (bodyRequest.getDate() == null || bodyRequest.getDate().isEmpty())
            return new ResponseEntity<>("The field date is required", HttpStatus.BAD_REQUEST);

        try{
            final Date now = new SimpleDateFormat(bodyRequest.getPattern()).parse(bodyRequest.getDate());
            final BitcoinSnapshot result = bitcoinsFinder.findBitcoinPrice(now);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ParseException e){
            return new ResponseEntity<>("Error in data input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(path = "/find-max", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMaxPrice(@RequestBody ChallengeRequest bodyRequest){
        if (bodyRequest.getPattern().isEmpty())
            return new ResponseEntity<>("The field patter is required", HttpStatus.BAD_REQUEST);
        if (bodyRequest.getDateFrom().isEmpty() || bodyRequest.getDateTo().isEmpty())
            return new ResponseEntity<>("Some date field is required", HttpStatus.BAD_REQUEST);

        try{
            final Date from = new SimpleDateFormat(bodyRequest.getPattern()).parse(bodyRequest.getDateFrom());
            final Date to = new SimpleDateFormat(bodyRequest.getPattern()).parse(bodyRequest.getDateTo());
            final BitcoinSnapshot result = bitcoinsFinder.findBitcoinPrice(from, to);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (ParseException e){
            return new ResponseEntity<>("Error in data input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
