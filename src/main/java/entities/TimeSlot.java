package entities;

import java.time.LocalTime;

public class TimeSlot {
    private int timeSlotId;
    private int day;
    private LocalTime startTime;
    private LocalTime endTime;

    public TimeSlot() {}

    public TimeSlot(int timeSlotId, int day, LocalTime startTime, LocalTime endTime) {
        this.timeSlotId = timeSlotId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String toString() {
        return "TimeSlot{" +
                "timeSlotId=" + timeSlotId +
                ", day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

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
