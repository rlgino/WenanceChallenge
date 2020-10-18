package com.wenance.WenanceChallenge.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathCalc {

    public static BigDecimal calculateDifferencePercentage(BigDecimal from, BigDecimal to) {
        return from
                .divide(to, 4, RoundingMode.HALF_DOWN)
                .multiply(new BigDecimal(100))
                .setScale(2, RoundingMode.UNNECESSARY)
                .subtract(new BigDecimal(100));
    }
}
