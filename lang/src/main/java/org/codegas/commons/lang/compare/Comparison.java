package org.codegas.commons.lang.compare;

public final class Comparison {

    private Comparison() {

    }

    public static boolean areValuesClose(double value1, double value2, double threshold) {
        return Math.abs(value1 - value2) < threshold;
    }
}
