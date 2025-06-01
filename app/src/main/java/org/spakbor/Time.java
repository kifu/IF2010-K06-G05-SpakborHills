package org.spakbor;

public class Time {
    private int day; // 1-10
    private int hours; // 00-23
    private int minutes; // 00-59
    
    public Time(int day, int hours, int minutes) {
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
    }

    public void advanceMinutes(int minutesToAdd) {
        this.minutes += minutesToAdd;
        while (this.minutes >= 60) {
            this.minutes -= 60;
            this.hours++;
            if (this.hours >= 24) {
                this.hours -= 24;
                this.day++;
            }
        }
    }

    public int getMinutes() {
        return minutes;
    }

    public int getHours() {
        return hours;
    }

    public int getDay() {
        return day;
    }   

    public void setTime(int hours, int minutes) {
        this.hours = hours % 24;
        this.minutes = minutes % 60;
    }

    public void nextDay() {
        this.day++;
        this.hours = 6;
        this.minutes = 0;
    }

    public boolean isDayTime() {
        return (hours >= 6 && hours < 18);
    }

    public boolean isNightTime() {
        return !isDayTime();
    }

    @Override
    public String toString() {
        return String.format("Day %d, %02d:%02d", day, hours, minutes);
    }

    public String getPhase() {
        return isDayTime() ? "Siang" : "Malam";
    }

    public void skipTo(int hour, int minute) {
        this.hours = hour;
        this.minutes = minute;
    }
    
    public int getTotalMinutes() {
    return day * 1440 + hours * 60 + minutes;
    }
}
