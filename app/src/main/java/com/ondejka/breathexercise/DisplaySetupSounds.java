package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

public class DisplaySetupSounds extends AppCompatActivity {
    int screenWidth;
    int screenHeihgt;
    SharedPreferences mPrefs;
    CheckBox voiceCheckBox;
    CheckBox gongCheckBox;
    RadioButton musicSoundRadio1;
    RadioButton musicSoundRadio2;
    RadioButton musicSoundRadio3;
    RadioButton breathingSoundRadio1;
    RadioButton breathingSoundRadio2;
    RadioButton breathingSoundRadio3;
    RadioButton breathingSoundRadio4;
    boolean updateParametersStarted;
    String updateParametersType;
    Parameters param;


    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SetupSound_OnCr_010", "OK");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_setup_sounds);
        Log.i("SetupSound_OnCr_012", "OK");

        voiceCheckBox = findViewById(R.id.VoiceCheckBox);
        gongCheckBox = findViewById(R.id.GongCheckBox);
        musicSoundRadio1 = findViewById(R.id.musicRadioButton1);
        musicSoundRadio2 = findViewById(R.id.musicRadioButton2);
        musicSoundRadio3 = findViewById(R.id.musicRadioButton3);
        breathingSoundRadio1 = findViewById(R.id.radioButton1);
        breathingSoundRadio2 = findViewById(R.id.radioButton2);
        breathingSoundRadio3 = findViewById(R.id.radioButton3);
        breathingSoundRadio4 = findViewById(R.id.radioButton4);
        Log.i("SetupSound_OnCr_020", "OK");

        updateParametersStarted=false;
        updateParametersType = "";

        screenWidth  = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt= Resources.getSystem().getDisplayMetrics().heightPixels;
        Log.i("SetupSound_OnCr_020 screenWidth", String.valueOf(screenWidth));
        Log.i("SetupSound_OnCr_020 screenHeihgt", String.valueOf(screenHeihgt));

        param = (Parameters)getIntent().getSerializableExtra("PARAMETERS");
        Log.i("SetupSound_OnCr_022 param", "OK");

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Log.i("SetupSound_OnCr_030", "OK");


        voiceCheckBox.setChecked(param.getVoice().equals("Y"));
        gongCheckBox.setChecked(param.getGong().equals("Y"));
        musicSoundRadio1.setChecked(param.getMusic().equals("N"));
        musicSoundRadio2.setChecked(param.getMusic().equals("M1"));
        musicSoundRadio3.setChecked(param.getMusic().equals("M2"));
        breathingSoundRadio1.setChecked(param.getBreathingSound().equals("N"));
        breathingSoundRadio2.setChecked(param.getBreathingSound().equals("S1"));
        breathingSoundRadio3.setChecked(param.getBreathingSound().equals("S2"));
        breathingSoundRadio4.setChecked(param.getBreathingSound().equals("S3"));
        Log.i("SetupSound_OnCr_032", "OK");

    }

    public void onCheckboxClicked(View view) {
        Log.i("onCheckboxClicked:", "OK");

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.VoiceCheckBox:
                if (checked)
                    saveVoice("Y");
                else
                    saveVoice("N");
                break;
            case R.id.GongCheckBox:
                if (checked)
                    saveGong("Y");
                else
                    saveGong("N");
                break;
        }
    }

    public void onMusicRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case  R.id.musicRadioButton1:
                if (checked)
                    saveMusic("N");
                break;
            case  R.id.musicRadioButton2:
                if (checked)
                    saveMusic("M1");
                break;
            case  R.id.musicRadioButton3:
                if (checked)
                    saveMusic("M2");
                break;
        }
    }

    public void onSoundRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case  R.id.radioButton1:
                if (checked)
                    saveBreathingSound("N");
                break;
            case  R.id.radioButton2:
                if (checked)
                    saveBreathingSound("S1");
                break;
            case  R.id.radioButton3:
                if (checked)
                    saveBreathingSound("S2");
                break;
            case  R.id.radioButton4:
                if (checked)
                    saveBreathingSound("S3");
                break;
        }
    }

    // ---- Voice
    public void saveVoice(String voice){
        param.setVoice(voice);
        savePreferences("voice", voice, "Y");
    }

    // ---- Music
    public void saveMusic(String music){
        param.setMusic(music);
        savePreferences("music", music, "M1");
    }

    // ---- Breathing Sound
    public void saveBreathingSound(String breathingSound){
        param.setBreathingSound(breathingSound);
        savePreferences("breathingSound", breathingSound, "S1");
    }

    // ---- Gong
    public void saveGong(String gong){
        param.setGong(gong);
        savePreferences("gong", gong, "Y");
    }


    private void savePreferences(String name_key, String value, String defValue){
        mPrefs = getSharedPreferences(name_key, 0);
        String mString = mPrefs.getString(name_key, defValue);

        SharedPreferences.Editor mEditor = mPrefs.edit();
        mEditor.putString(name_key, value).commit();
    }



}