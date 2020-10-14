package com.wenance.WenanceChallenge.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BitcoinsController {
    @GetMapping(path = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<Object> getPrinceOnDate(){
        return new ResponseEntity<>("The price is U$D 10.00", HttpStatus.OK);
    }
}
