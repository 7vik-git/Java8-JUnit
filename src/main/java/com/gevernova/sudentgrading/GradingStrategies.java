package com.gevernova.sudentgrading;
public class GradingStrategies {
    public static GradingStrategy basicStrategy = avg -> {
        if (avg >= 85) return "A";
        else if (avg >= 70) return "B";
        else if (avg >= 50) return "C";
        else return "Fail";
    };
}

