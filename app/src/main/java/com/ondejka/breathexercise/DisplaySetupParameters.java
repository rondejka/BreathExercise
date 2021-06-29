package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplaySetupParameters extends AppCompatActivity {
    int screenWidth;
    int screenHeihgt;
    SharedPreferences mPrefs;
    SeekBar breathsSeekBar;
    SeekBar speedBreathingSeekBar;
    SeekBar secondsBreathHoldingSeekBar;
    SeekBar roundsSeekBar;
    TextView breathsTextView;
    TextView timeBreathingTextView;
    TextView speedBreathingTextView;
    TextView secondsBreathHoldingTextView;
    TextView roundsTextView;
    TextView versionTextView;
    boolean updateParametersStarted;
    String updateParametersType;
    Parameters param;
    int speedMax;



    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SetupParam_OnCr_010", "OK");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_setup_parameters);
        Log.i("SetupParam_OnCr_012", "OK");

        breathsSeekBar = findViewById(R.id.breathsSeekBar);
        breathsTextView = findViewById(R.id.breathsTextView);
        timeBreathingTextView = findViewById(R.id.timeBreathingTextView);
        speedBreathingSeekBar = findViewById(R.id.speedBreathingSeekBar);
        speedBreathingTextView = findViewById(R.id.speedBreathingTextView);
        secondsBreathHoldingSeekBar = findViewById(R.id.secondsBreathHoldingSeekBar);
        secondsBreathHoldingTextView = findViewById(R.id.secondsBreathHoldingTextView);
        roundsSeekBar = findViewById(R.id.roundsSeekBar);
        roundsTextView = findViewById(R.id.roundsTextView);
        versionTextView = findViewById(R.id.versionTextView);
        Log.i("SetupParam_OnCr_020", "OK");

        updateParametersStarted=false;
        updateParametersType = "";
        speedMax = 7;

        screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt= Resources.getSystem().getDisplayMetrics().heightPixels;
        Log.i("SetupParam_OnCr_020 screenWidth", String.valueOf(screenWidth));
        Log.i("SetupParam_OnCr_020 screenHeihgt", String.valueOf(screenHeihgt));



        param = (Parameters)getIntent().getSerializableExtra("PARAMETERS");
        Log.i("SetupParam_OnCr_022 param", "OK");

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        try {
            String versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),0).versionName;
            versionTextView.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        breathsSeekBar.setMax(60);
        speedBreathingSeekBar.setMax(speedMax - 1);
        secondsBreathHoldingSeekBar.setMax(20);
        roundsSeekBar.setMax(10);

        breathsSeekBar.setProgress((int) param.getBreathsInStartingPhase());
        breathsTextView.setText(Integer.toString(param.getBreathsInStartingPhase()));

        speedBreathingSeekBar.setProgress(param.getBreathingSpeedInStartingPhase());
        speedBreathingTextView.setText(Integer.toString(speedMax - param.getBreathingSpeedInStartingPhase()));

        timeBreathingTextView.setText(secondsToTimeString((int) param.getSecondsOfStartingPhase()));

        secondsBreathHoldingSeekBar.setProgress((int) param.getSecondsOfRelaxingPhase());
        secondsBreathHoldingTextView.setText(secondsToTimeString((int) param.getSecondsOfRelaxingPhase()));
        Log.i("SetupParam_OnCr_028 param", "OK");

        roundsSeekBar.setProgress((int) param.getRounds());
        Log.i("SetupParam_OnCr_029 param", "OK");
        roundsTextView.setText( Integer.toString(param.getRounds()));
        Log.i("SetupParam_OnCr_030", "OK");

        breathsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateParametersType = "B";
                updateBreathsTextView(progress);
                updateBreathingTimeTextView(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        speedBreathingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSpeedBreathingTextView(progress);

//                updateBreathsTextView(progress);
                updateBreathingTimeTextView_B();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        secondsBreathHoldingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateSecondsBreathHoldingTextView(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        roundsSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateRoundsTextView(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

    }

    // ---- Breaths
    @SuppressLint("LongLogTag")
    public void updateBreathsTextView(int breaths){
        updateParametersStarted = true;

        String breathsString = Integer.toString(breaths);
        breathsTextView.setText(breathsString);
        param.setBreathsInStartingPhase(Integer.parseInt(breathsString));

        mPrefs = getSharedPreferences("breaths", 0);
        String mString = mPrefs.getString("breaths", "40");

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("breaths", breathsString).commit();

        updateParametersStarted=false;
    }

    // ---- Speed of Breathing
    @SuppressLint("LongLogTag")
    public void updateSpeedBreathingTextView(int speed){
        updateParametersStarted = true;

        String speedString = Integer.toString(speed);
        speedBreathingTextView.setText(String.valueOf(speedMax - Integer.parseInt(speedString)));
        param.setBreathingSpeedInStartingPhase(Integer.parseInt(speedString));

        mPrefs = getSharedPreferences("speed", 0);
        String mString = mPrefs.getString("speed", "40");

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("speed", speedString).commit();

        updateParametersStarted=false;
    }

    // ---- Breathing Time
    // --------------A------------------
    @SuppressLint("LongLogTag")
    public void updateBreathingTimeTextView(int breaths){

        float realSpeed = (float) ((((float) (param.getBreathingSpeedInStartingPhase())) / 2) + 2.5);
        int secondsBreathing = (int) (breaths * realSpeed);
        Log.i("updateBreathingTimeTextView speed:", Integer.toString(param.getBreathingSpeedInStartingPhase()));
        Log.i("updateBreathingTimeTextView secondsBreathing:", Integer.toString(secondsBreathing));

        timeBreathingTextView.setText(secondsToTimeString(secondsBreathing));
        param.setSecondsOfStartingPhase(secondsBreathing);

        mPrefs = getSharedPreferences("timeBreathing", 0);
        String mString = mPrefs.getString("timeBreathing", "180");

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("timeBreathing", String.valueOf(secondsBreathing)).commit();
    }

    // ---- Breathing Time
    // --------------B------------------
    @SuppressLint("LongLogTag")
    public void updateBreathingTimeTextView_B(){

        int breaths = param.getBreathsInStartingPhase();
        Log.i("updateBreathingTimeTextView_B breaths:", Integer.toString(breaths));
        float realSpeed = (float) ((((float) (param.getBreathingSpeedInStartingPhase())) / 2) + 2.5);
        int secondsBreathing = (int) (breaths * realSpeed);
        Log.i("updateBreathingTimeTextView_B speed:", Integer.toString(param.getBreathingSpeedInStartingPhase()));
        Log.i("updateBreathingTimeTextView_B secondsBreathing:", Integer.toString(secondsBreathing));

        timeBreathingTextView.setText(secondsToTimeString(secondsBreathing));
        param.setSecondsOfStartingPhase(secondsBreathing);

        mPrefs = getSharedPreferences("timeBreathing", 0);
        String mString = mPrefs.getString("timeBreathing", "180");

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("timeBreathing", String.valueOf(secondsBreathing)).commit();
    }


    @SuppressLint("LongLogTag")
    public void updateSecondsBreathHoldingTextView(int secondsRelaxing){
        updateParametersStarted = true;

        String secondsString = Integer.toString(secondsRelaxing);
        secondsBreathHoldingTextView.setText(secondsString);
        param.setSecondsOfRelaxingPhase(secondsRelaxing);

        mPrefs = getSharedPreferences("timeRelaxing", 0);
        String mString = mPrefs.getString("timeRelaxing", "15");

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("timeRelaxing", secondsString).commit();

        updateParametersStarted=false;
    }

    // ---- Rounds
    @SuppressLint("LongLogTag")
    public void updateRoundsTextView(int rounds){
        updateParametersStarted = true;

        String roundsString = Integer.toString(rounds);
        roundsTextView.setText(String.valueOf(Integer.parseInt(roundsString)));
        param.setRounds(Integer.parseInt(roundsString));

        mPrefs = getSharedPreferences("rounds", 0);
        String mString = mPrefs.getString("rounds", "4");

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString("rounds", roundsString).commit();

        updateParametersStarted=false;
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the Reset button
     */
    public void resetToDefault(View view) {

        breathsSeekBar.setProgress(30);
        speedBreathingSeekBar.setProgress(5);
        secondsBreathHoldingSeekBar.setProgress(15);
        roundsSeekBar.setProgress(4);

    }



    private String secondsToTimeString(int secondsTotal){
        int minutes = secondsTotal / 60;
        int seconds = secondsTotal % 60;

        String secondsString;
        if (seconds < 10) {
            secondsString = "0" + String.valueOf(seconds);
        }
        else secondsString = String.valueOf(seconds);

        return String.valueOf(minutes) + ":" + secondsString;
    }

    private void savePreferences(String name_key, String value, String defValue){
        mPrefs = getSharedPreferences(name_key, 0);
        String mString = mPrefs.getString(name_key, defValue);

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(name_key, value).commit();
    }




}