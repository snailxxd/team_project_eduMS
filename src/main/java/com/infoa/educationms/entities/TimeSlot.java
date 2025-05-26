package com.infoa.educationms.entities;

import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "time_slot")
public class TimeSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_slot_id")
    private int timeSlotId;

    private int day;

    @Column(name = "start_time")
    private LocalTime startTime;

    @Column(name = "end_time")
    private LocalTime endTime;

    // constructors
    public TimeSlot() {}

    public TimeSlot(int timeSlotId, int day, LocalTime startTime, LocalTime endTime) {
        this.timeSlotId = timeSlotId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // 转换为字符串
    public String toString() {
        return "TimeSlot{" +
                "timeSlotId=" + timeSlotId +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    // getter and setter methods
    public int getTimeSlotId() {
        return timeSlotId;
    }

    public void setTimeSlotId(int timeSlotId) {
        this.timeSlotId = timeSlotId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
}
