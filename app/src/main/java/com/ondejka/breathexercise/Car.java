package com.ondejka.breathexercise;

public class Car {
    String typ;
    String spz;

    public Car(String typ, String spz) {
        this.typ = typ;
        this.spz = spz;
    }

    public String getTyp() {
        return typ;
    }

    public String getSpz() {
        return spz;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    public void setSpz(String spz) {
        this.spz = spz;
    }
}
