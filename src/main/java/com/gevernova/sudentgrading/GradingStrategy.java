package com.gevernova.sudentgrading;


@FunctionalInterface
public interface GradingStrategy {
    String assignGrade(double average);
}

