package com.ondejka.breathexercise;

public class ScoreForPostToCloud {
    private long time;
    private int time1;
    private int time2;
    private int time3;
    private int time4;
    private int time_max;
    private float time_avg;
    private float time_max2;
    private float time_max3;
    private String versionapp;

    public ScoreForPostToCloud(long time, int time1, int time2, int time3, int time4, int time_max, float time_avg, float time_max2, float time_max3, String versionapp) {
        this.time = time;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time_max = time_max;
        this.time_avg = time_avg;
        this.time_max2 = time_max2;
        this.time_max3 = time_max3;
        this.versionapp = versionapp;
    }

    public long getDateTime() {
        return time;
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
        return time_max;
    }

    public float getTimeAvg() {
        return time_avg;
    }

    public float getTimeMax2() {
        return time_max2;
    }

    public float getTimeMax3() {
        return time_max3;
    }

    public String getVersionapp() {
        return versionapp;
    }

    public void setDateTime(long time) {
        this.time = time;
    }

    public void setTime1(int time1) {
        this.time1 = time1;
    }

    public void setTime2(int time2) {
        this.time2 = time2;
    }

    public void setTime3(int time3) {
        this.time3 = time3;
    }

    public void setTime4(int time4) {
        this.time4 = time4;
    }

    public void setTimeMax(int time_max) {
        this.time_max = time_max;
    }

    public void setTimeAvg(float time_avg) {
        this.time_avg = time_avg;
    }

    public void setTimeMax2(float time_max2) {
        this.time_max2 = time_max2;
    }

    public void setTimeMax3(float time_max3) {
        this.time_max3 = time_max3;
    }

    public void setVersionapp(String versionapp) {
        this.versionapp = versionapp;
    }

}

