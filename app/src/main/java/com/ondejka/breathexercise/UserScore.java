package com.ondejka.breathexercise;

import java.io.Serializable;
import java.util.Date;

public class UserScore implements Serializable {
    private int id;
    private String userName;
    private long dateTime;
    private int time1;
    private int time2;
    private int time3;
    private int time4;
    private int time5;
    private int time6;
    private int time7;
    private int time8;
    private int time9;
    private int time10;
    private int timeMax;
    private float timeAvg;
    private float timeMax2;
    private float timeMax3;
    private boolean synchro;
    private int trophy1;
    private int trophy2;
    private int trophy3;
    private int trophy4;

    public UserScore() {
    }

    public UserScore(int id, String userName, long dateTime, int time1, int time2, int time3, int time4, int time5, int time6, int time7, int time8, int time9, int time10, int timeMax, float timeAvg, float timeMax2, float timeMax3, boolean synchro, int trophy1, int trophy2, int trophy3, int trophy4) {
        this.id = id;
        this.userName = userName;
        this.dateTime = dateTime;
        this.time1 = time1;
        this.time2 = time2;
        this.time3 = time3;
        this.time4 = time4;
        this.time5 = time5;
        this.time6 = time6;
        this.time7 = time7;
        this.time8 = time8;
        this.time9 = time9;
        this.time10 = time10;
        this.timeMax = timeMax;
        this.timeAvg = timeAvg;
        this.timeMax2 = timeMax2;
        this.timeMax3 = timeMax3;
        this.synchro = synchro;
        this.trophy1 = trophy1;
        this.trophy2 = trophy2;
        this.trophy3 = trophy3;
        this.trophy4 = trophy4;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public long getDateTime() {
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
    public int getTime5() {
        return time5;
    }
    public int getTime6() {
        return time6;
    }
    public int getTime7() {
        return time7;
    }
    public int getTime8() {
        return time8;
    }
    public int getTime9() {
        return time9;
    }
    public int getTime10() {
        return time10;
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

    public boolean isSynchro() {
        return synchro;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setDateTime(long dateTime) {
        this.dateTime = dateTime;
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
    public void setTime5(int time5) {
        this.time5 = time5;
    }
    public void setTime6(int time6) {
        this.time6 = time6;
    }
    public void setTime7(int time7) {
        this.time7 = time7;
    }
    public void setTime8(int time8) {
        this.time8 = time8;
    }
    public void setTime9(int time9) {
        this.time9 = time9;
    }
    public void setTime10(int time10) {
        this.time10 = time10;
    }

    public void setTimeMax(int timeMax) {
        this.timeMax = timeMax;
    }

    public void setTimeAvg(float timeAvg) {
        this.timeAvg = timeAvg;
    }

    public void setTimeMax2(float timeMax2) {
        this.timeMax2 = timeMax2;
    }

    public void setTimeMax3(float timeMax3) {
        this.timeMax3 = timeMax3;
    }

    public void setSynchro(boolean synchro) {
        this.synchro = synchro;
    }


    public int getTrophy1() {
        return trophy1;
    }

    public int getTrophy2() {
        return trophy2;
    }

    public int getTrophy3() {
        return trophy3;
    }

    public int getTrophy4() {
        return trophy4;
    }

    public void setTrophy1(int trophy1) {
        this.trophy1 = trophy1;
    }

    public void setTrophy2(int trophy2) {
        this.trophy2 = trophy2;
    }

    public void setTrophy3(int trophy3) {
        this.trophy3 = trophy3;
    }

    public void setTrophy4(int trophy4) {
        this.trophy4 = trophy4;
    }

    public void initUserScore() {
        this.setUserName("UserName");
        long dateTimeL = new Date().getTime();
//        int date = new Date();
        this.setDateTime(dateTimeL);
        this.setTime1(0);
        this.setTime2(0);
        this.setTime3(0);
        this.setTime4(0);
        this.setTime5(0);
        this.setTime6(0);
        this.setTime7(0);
        this.setTime8(0);
        this.setTime9(0);
        this.setTime10(0);
        this.setTimeMax(0);
        this.setTimeAvg(0);
        this.setTimeMax2(0);
        this.setTimeMax3(0);
        this.setSynchro(false);
    }


//  return average from highest 3 times1 .. times4
//    public float getAvgMax3() {
//        float avgMax3;
//        avgMax3 = time1 + time2 + time3 + time4 - Math.min(Math.min(time1, time2),Math.min(time3,time4));
//        avgMax3 = avgMax3 / 3;
//        return avgMax3;
//    }


    //  return average from highest 2 times1 .. times4
//    public float getAvgMax2() {
//        float max1 = time1;
//        float max2 = time2;
//        if(time3 > max1) {
//            max1 = time3;
//        } else if (time3 > max2) {
//            max2 = time3;
//        }
//        if(time4 > max2) {
//            max2 = time4;
//        } else if (time4 > max1) {
//            max1 = time4;
//        }
//        return (max1 + max2) / 2;
//    }


}
