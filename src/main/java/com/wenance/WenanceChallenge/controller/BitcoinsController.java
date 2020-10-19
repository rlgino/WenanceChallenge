package com.wenance.WenanceChallenge.controller;

import com.wenance.WenanceChallenge.business.BitcoinsFinder;
import com.wenance.WenanceChallenge.dto.ChallengeRequest;
import com.wenance.WenanceChallenge.model.BitcoinSnapshot;
import com.wenance.WenanceChallenge.utils.MathCalc;
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
import java.util.Optional;

@RestController
public class BitcoinsController {
    @Autowired
    private BitcoinsFinder bitcoinsFinder;

    @GetMapping(path = "/find-one", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getPriceOnDate(@RequestBody ChallengeRequest bodyRequest){

        String validationError = getFindOneValidationMsg(bodyRequest);
        if (validationError != null)
            return new ResponseEntity<>(validationError, HttpStatus.BAD_REQUEST);

        try{
            final Date now = new SimpleDateFormat(bodyRequest.getPattern()).parse(bodyRequest.getDate());
            final Optional<BitcoinSnapshot> result = bitcoinsFinder.findBitcoinPrice(now);
            if (!result.isPresent())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            final BitcoinSnapshot snapshot = result.get();

            final BitcoinPriceResponse response = new BitcoinPriceResponse();
            response.setDate(snapshot.getCreationDateTime());
            response.setPrice(snapshot.getPrice());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParseException e){
            return new ResponseEntity<>("Error in data input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Validate the request of the find-one method. The fields validated are:
     * Pattern
     * Date
     * @param bodyRequest of find-one method
     * @return the message of error or null
     */
    private String getFindOneValidationMsg(ChallengeRequest bodyRequest) {
        if (bodyRequest.getPattern() == null || bodyRequest.getPattern().isEmpty())
            return "The field patter is required";
        if (bodyRequest.getDate() == null || bodyRequest.getDate().isEmpty())
            return "The field date is required";

        return null;
    }

    @GetMapping(path = "/find-detail", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMaxPrice(@RequestBody ChallengeRequest bodyRequest){
        if (bodyRequest.getPattern().isEmpty())
            return new ResponseEntity<>("The field patter is required", HttpStatus.BAD_REQUEST);
        if (bodyRequest.getDateFrom().isEmpty() || bodyRequest.getDateTo().isEmpty())
            return new ResponseEntity<>("Some date field is required", HttpStatus.BAD_REQUEST);

        try{
            final Date from = new SimpleDateFormat(bodyRequest.getPattern()).parse(bodyRequest.getDateFrom());
            final Date to = new SimpleDateFormat(bodyRequest.getPattern()).parse(bodyRequest.getDateTo());
            if (from.getTime() > to.getTime())
                return new ResponseEntity<>("The time to should be grater than time from", HttpStatus.BAD_REQUEST);

            final BigDecimal average = bitcoinsFinder.findBitcoinAveragePrice(from, to);
            if(average == null)
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            final Optional<BitcoinSnapshot> result = bitcoinsFinder.findMaxBitcoinPrice(from, to);
            if (!result.isPresent())
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);

            final BitcoinSnapshot maxPriceSnapshot = result.get();

            final BitcoinPriceDetailResponse response = new BitcoinPriceDetailResponse();
            response.setAvgPrice(average);
            response.setMaxPrice(maxPriceSnapshot.getPrice());
            response.setDifferencePercentage(MathCalc.calculateDifferencePercentage(maxPriceSnapshot.getPrice(), average));

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (ParseException e){
            return new ResponseEntity<>("Error in data input: " + e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    public class BitcoinPriceResponse {
        Date date;
        BigDecimal price;

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }
    }

    public class BitcoinPriceDetailResponse {
        BigDecimal avgPrice;
        BigDecimal maxPrice;
        BigDecimal differencePercentage;

        public BigDecimal getAvgPrice() {
            return avgPrice;
        }

        public void setAvgPrice(BigDecimal avgPrice) {
            this.avgPrice = avgPrice;
        }

        public BigDecimal getMaxPrice() {
            return maxPrice;
        }

        public void setMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
        }

        public BigDecimal getDifferencePercentage() {
            return differencePercentage;
        }

        public void setDifferencePercentage(BigDecimal differencePercentage) {
            this.differencePercentage = differencePercentage;
        }
    }
}
