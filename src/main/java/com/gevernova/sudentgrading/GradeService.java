package com.gevernova.sudentgrading;

public class GradeService {
    public String getGrade(Student student, GradingStrategy strategy) {
        return strategy.assignGrade(student.getAverage());
    }
}