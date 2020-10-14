package com.wenance.WenanceChallenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CEXResponse {
    private BigDecimal lprice;
    private String curr1;
    private String curr2;

    public BigDecimal getLprice() {
        return lprice;
    }

    public void setLprice(BigDecimal lprice) {
        this.lprice = lprice;
    }

    public String getCurr1() {
        return curr1;
    }

    public void setCurr1(String curr1) {
        this.curr1 = curr1;
    }

    public String getCurr2() {
        return curr2;
    }

    public void setCurr2(String curr2) {
        this.curr2 = curr2;
    }
}
