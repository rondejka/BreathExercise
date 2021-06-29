package com.ondejka.breathexercise;

public class DemoData {
    private String userName;
    private String dateTime;
    private int time1;
    private int time2;
    private int time3;
    private int time4;
    private int timeMax;
    private float timeAvg;
    private float timeMax2;
    private float timeMax3;

    public DemoData(String userName, String dateTime, int time1, int time2, int time3, int time4, int timeMax, float timeAvg, float timeMax2, float timeMax3) {
        this.userName = userName;
        this.dateTime = dateTime;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.timeMax = timeMax;
        this.timeAvg = timeAvg;
        this.timeMax2 = timeMax2;
        this.timeMax3 = timeMax3;
    }

    public String getUserName() {
        return userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public int getTime1() {
        return time1;
    }

    public int getTime2() {
        return time2;
    }

    public int getTime3() {
        return time3;
    }

    public int getTime4() {
        return time4;
    }

    public int getTimeMax() {
        return timeMax;
    }

    public float getTimeAvg() {
        return timeAvg;
    }

    public float getTimeMax2() {
        return timeMax2;
    }

    public float getTimeMax3() {
        return timeMax3;
    }
}
