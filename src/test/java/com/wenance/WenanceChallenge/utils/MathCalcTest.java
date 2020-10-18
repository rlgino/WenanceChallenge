package com.wenance.WenanceChallenge.utils;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;


public class MathCalcTest {
    @Test
    public void calculateDifferencePercentageTest(){
        final BigDecimal from = new BigDecimal(100);
        final BigDecimal to = new BigDecimal(100);
        final BigDecimal result = MathCalc.calculateDifferencePercentage(from, to);
        assertThat(result).isEqualTo(new BigDecimal("0.00"));
    }
}
