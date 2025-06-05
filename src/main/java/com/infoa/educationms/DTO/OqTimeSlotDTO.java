package com.infoa.educationms.DTO;

import java.time.LocalTime;

public class OqTimeSlotDTO {
    private int day;
    private LocalTime startTime;
    private LocalTime endTime;

    public OqTimeSlotDTO() {}

    public OqTimeSlotDTO(int day, LocalTime startTime, LocalTime endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDay() { return day;}
    public void setDay(int day) {this.day = day;}
    public LocalTime getStartTime() {return startTime;}
    public void setStartTime(LocalTime startTime) {this.startTime = startTime;}
    public LocalTime getEndTime() {return endTime;}
    public void setEndTime(LocalTime endTime) {this.endTime = endTime;}
}