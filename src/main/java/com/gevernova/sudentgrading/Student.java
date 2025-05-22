package com.gevernova.sudentgrading;
import java.util.List;

public class Student {
    private String name;
    private String id;
    private List<Integer> marks;

    public Student(String name, String id, List<Integer> marks) throws InvalidMarkException, EmptyMarkListException {
        if (marks == null || marks.isEmpty()) throw new EmptyMarkListException("Marks list is empty");
        if (marks.stream().anyMatch(m -> m < 0 || m > 100)) throw new InvalidMarkException("Marks must be between 0 and 100");
        this.name = name;
        this.id = id;
        this.marks = marks;
    }

    public String getName() { return name; }
    public String getId() { return id; }
    public List<Integer> getMarks() { return marks; }

    public double getAverage() {
        return marks.stream().mapToInt(i -> i).average().orElse(0);
    }
}

