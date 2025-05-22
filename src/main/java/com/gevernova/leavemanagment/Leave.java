package com.gevernova.leavemanagment;
import java.time.LocalDate;

public class Leave {
    private LocalDate date;
    private String type;

    public Leave(LocalDate date, String type) {
        this.date = date;
        this.type = type;
    }

    public LocalDate getDate() { return date; }
    public String getType() { return type; }
}

