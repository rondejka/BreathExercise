package com.ondejka.breathexercise;

import java.io.Serializable;

public class Parameters implements Serializable {
    private int breathsInStartingPhase;
    private int breathingSpeedInStartingPhase;
    private int secondsOfStartingPhase;
    private int secondsOfRelaxingPhase;
    private int rounds;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String gender;
    private int birthYear;
    private String voice;
    private String music;
    private String breathingSound;
    private String gong;

    public Parameters(int breathsInStartingPhase, int breathingSpeedInStartingPhase, int secondsOfStartingPhase, int secondsOfRelaxingPhase, int rounds,
                      String email, String password, String firstName, String lastName, String gender, int birthYear,
                      String voice, String music, String breathingSound, String gong) {
        this.breathsInStartingPhase = breathsInStartingPhase;
        this.breathingSpeedInStartingPhase = breathingSpeedInStartingPhase;
        this.secondsOfStartingPhase = secondsOfStartingPhase;
        this.secondsOfRelaxingPhase = secondsOfRelaxingPhase;
        this.rounds = rounds;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthYear = birthYear;
        this.voice = voice;
        this.music = music;
        this.breathingSound = breathingSound;
        this.gong = gong;
    }

    public int getBreathsInStartingPhase() {
        return breathsInStartingPhase;
    }

    public void setBreathsInStartingPhase(int p_breathsInStartingPhase) {
        breathsInStartingPhase = p_breathsInStartingPhase;
    }

    public int getBreathingSpeedInStartingPhase() {
        return breathingSpeedInStartingPhase;
    }

    public void setBreathingSpeedInStartingPhase(int p_breathingSpeedsInStartingPhase) {
        breathingSpeedInStartingPhase = p_breathingSpeedsInStartingPhase;
    }

    public long getSecondsOfStartingPhase() {
        return secondsOfStartingPhase;
    }

    public void setSecondsOfStartingPhase(int p_secondsOfStartingPhase) {
        secondsOfStartingPhase = p_secondsOfStartingPhase;
    }

    public long getSecondsOfRelaxingPhase() {
        return secondsOfRelaxingPhase;
    }

    public void setSecondsOfRelaxingPhase(int p_secondsOfRelaxingPhase) {
        secondsOfRelaxingPhase = p_secondsOfRelaxingPhase;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int p_rounds) {
        rounds = p_rounds;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public String getVoice() {
        return voice;
    }

    public String getMusic() {
        return music;
    }

    public String getBreathingSound() {
        return breathingSound;
    }

    public String getGong() {
        return gong;
    }

    public void setVoice(String voice) {
        this.voice = voice;
    }

    public void setMusic(String music) {
        this.music = music;
    }

    public void setBreathingSound(String breathingSound) {
        this.breathingSound = breathingSound;
    }

    public void setGong(String gong) {
        this.gong = gong;
    }
}
