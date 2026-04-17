package org.example;

public enum Workload {
    VERY_HIGH(1.6), HIGH(1.4), ELEVATED(1.2), NORMAL(1);

    private final double coefficient;

    Workload(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getCoefficient() {
        return coefficient;
    }
}
