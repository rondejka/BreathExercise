package com.ondejka.breathexercise;

public class ResultFromCloud {

    private int id;
    private long time;
    private int time1;
    private int time2;
    private int time3;
    private int time4;
    private int time_max;
    private float time_avg;
    private float time_max2;
    private float time_max3;
    private String created_at;
    private String updated_at;
    private String user;

    public ResultFromCloud(int id, long time, int time1, int time2, int time3, int time4, int time_max, float time_avg, float time_max2, float time_max3, String created_at, String updated_at, String user) {
        this.id = id;
        this.time = time;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time_max = time_max;
        this.time_avg = time_avg;
        this.time_max2 = time_max2;
        this.time_max3 = time_max3;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public long getTime() {
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

    public int getTime_max() {
        return time_max;
    }

    public float getTime_avg() {
        return time_avg;
    }

    public float getTime_max2() {
        return time_max2;
    }

    public float getTime_max3() {
        return time_max3;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getUserName() {
        return user;
    }

}
