package com.ondejka.breathexercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


//////////////////////////////////////////////////////////////////////////////
public class MainActivity extends AppCompatActivity {
    int screenWidth;
    int screenHeihgt;
    ImageView setupImageView, historyImageView, backgrImageView, whileImageView, exitImageView;
    TextView timerTexView, breathdownTextView;
    TextView stopTextView, goTextView, cancelTextView, finishTextViewAction;
    TextView time01TexView, time02TexView, time03TexView, time04TexView, time05TexView, timeAvgTexView;
    TextView time01ShadowTextView, time02ShadowTextView, time03ShadowTextView, time04ShadowTextView, time05ShadowTextView, timeAvgShadowTextView;
    Boolean counterIsActive = false;
//    Button goButton;
    Button stopButton;
    Button finishButton;
    Button exitButton;
    CountDownTimer countDownTimer;
    CountDownTimer breathsDownTimer;
    SharedPreferences mPrefs;
    private static final String FILE_NAME = "example.txt";
    public Parameters parameters;
    int[] results = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    int phase;  // 0=Inic, 1=Deep breathing, 2=Deep out & hold, 3=Deep in & keep, 4=Out & start breathing
    boolean timerExit = false;
    int breathsLeft;
    Handler handler;
    Runnable run;
    TextToSpeech textToSpeech;
    int timerSeconds;
    int round;
    int round_MAX;
    public DbHelper myDb;
    public UserScore userScore;
    private JsonPlaceHolderApi jsonPlaceHolderApi;
    private String token;
    MediaPlayer mplayerBackground;
    MediaPlayer mplayerPhase2;
    MediaPlayer mplayerGong;

    Timer timer;
    ArrayList<Integer> playlist;
    int playingBackgroundMusic = 0;


    //////////////////////////////////////////////////////////////////////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        myDb = new DbHelper(this);

        //ak chcem updatovat lokalnu db tabulku
//        myDb.deleteDBtable();
//        myDb.updateDB();
//        myDb.insertDemoData1();
//        setTrophy();
        /////////////////////////////////////////

        userScore = new UserScore();

        screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        screenHeihgt = Resources.getSystem().getDisplayMetrics().heightPixels;

        String mString;

        parameters = new Parameters(40, 1, 120, 15, 4,
                "x@x.com", "start123", "First_name", "Last_name", "M/W", 1970,
                "Y", "Y", "Y", "Y");
        Log.i("Main01:", "OK");

        mPrefs = getSharedPreferences("breaths", 0);
        mString = mPrefs.getString("breaths", "40");
        parameters.setBreathsInStartingPhase(Integer.parseInt(mString));
//        Log.i("Param Breaths Main:", Integer.toString(parameters.getBreathsInStartingPhase()));

        mPrefs = getSharedPreferences("speed", 0);
        mString = mPrefs.getString("speed", "2");
        parameters.setBreathingSpeedInStartingPhase(Integer.parseInt(mString));
//        Log.i("Param Speed Main:", Integer.toString(parameters.getBreathingSpeedInStartingPhase()));

        mPrefs = getSharedPreferences("timeBreathing", 0);
        mString = mPrefs.getString("timeBreathing", "120");
        parameters.setSecondsOfStartingPhase(Integer.parseInt(mString));
//        Log.i("Param Seconds1 Main:", String.valueOf(parameters.getBreathsInStartingPhase()));

        mPrefs = getSharedPreferences("timeRelaxing", 0);
        mString = mPrefs.getString("timeRelaxing", "15");
        parameters.setSecondsOfRelaxingPhase(Integer.parseInt(mString));
//        Log.i("Param Seconds2 Main:", String.valueOf(parameters.getSecondsOfRelaxingPhase()));

        mPrefs = getSharedPreferences("rounds", 0);
        mString = mPrefs.getString("rounds", "4");
        parameters.setRounds(Integer.parseInt(mString));
//        Log.i("Param Rounds Main:", String.valueOf(parameters.getRounds()));

        mPrefs = getSharedPreferences("email", 0);
        mString = mPrefs.getString("email", "email");
        parameters.setEmail(mString);
//        Log.i("Param email Main:", String.valueOf(parameters.getEmail()));

        mPrefs = getSharedPreferences("password", 0);
        mString = mPrefs.getString("password", "start123");
        parameters.setPassword(mString);
//        Log.i("Param password Main:", String.valueOf(parameters.getPassword()));

        mPrefs = getSharedPreferences("firstName", 0);
        mString = mPrefs.getString("firstName", "First_name");
        parameters.setFirstName(mString);
//        Log.i("Param firstName Main:", String.valueOf(parameters.getEmail()));

        mPrefs = getSharedPreferences("lastName", 0);
        mString = mPrefs.getString("lastName", "Last_name");
        parameters.setLastName(mString);
//        Log.i("Param lastName Main:", String.valueOf(parameters.getEmail()));

        mPrefs = getSharedPreferences("gender", 0);
        mString = mPrefs.getString("gender", "M/W");
        parameters.setGender(mString);
//        Log.i("Param gender Main:", parameters.getGender());

        mPrefs = getSharedPreferences("birthYear", 0);
        mString = mPrefs.getString("birthYear", "1970");
        parameters.setBirthYear(Integer.parseInt(mString));
//        Log.i("Param birthYear Main:", String.valueOf(parameters.getBirthYear()));

        mPrefs = getSharedPreferences("voice", 0);
        mString = mPrefs.getString("voice", "Y");
        parameters.setVoice(mString);
//        Log.i("Param voice Main:", parameters.getVoice());

        mPrefs = getSharedPreferences("music", 0);
        mString = mPrefs.getString("music", "M1");
        parameters.setMusic(mString);
//        Log.i("Param voice Main:", parameters.getMusic());

        mPrefs = getSharedPreferences("breathingSound", 0);
        mString = mPrefs.getString("breathingSound", "S1");
        parameters.setBreathingSound(mString);
//        Log.i("Param voice Main:", parameters.getBreathingSound());

        mPrefs = getSharedPreferences("gong", 0);
        mString = mPrefs.getString("gong", "Y");
        parameters.setGong(mString);
//        Log.i("Param voice Main:", parameters.getGong());

        setupImageView = findViewById(R.id.setupImageView);
        historyImageView = findViewById(R.id.historyImageView);
        exitImageView = findViewById(R.id.exitImageView);
        backgrImageView = findViewById(R.id.backgrImageView);
        whileImageView = findViewById(R.id.whileImageView);
//        goButton = findViewById(R.id.goButton);
        stopButton = findViewById(R.id.stopButton);
        finishButton = findViewById(R.id.finishButton);
        exitButton = findViewById(R.id.exitButton);
        timerTexView = findViewById(R.id.countdownTextView);
        breathdownTextView = findViewById(R.id.breathdownTextView);
        goTextView = findViewById(R.id.goTextView);
        cancelTextView = findViewById(R.id.cancelTextView);
        stopTextView = findViewById(R.id.stopTextView);
        finishTextViewAction = findViewById(R.id.finishTextViewAction);
        time01TexView = findViewById(R.id.time01TextView);
        time02TexView = findViewById(R.id.time02TextView);
        time03TexView = findViewById(R.id.time03TextView);
        time04TexView = findViewById(R.id.time04TextView);
        time05TexView = findViewById(R.id.time05TextView);
        timeAvgTexView = findViewById(R.id.timeAvgTextView);
        time01ShadowTextView = findViewById(R.id.time01ShadowTextView);
        time02ShadowTextView = findViewById(R.id.time02ShadowTextView);
        time03ShadowTextView = findViewById(R.id.time03ShadowTextView);
        time04ShadowTextView = findViewById(R.id.time04ShadowTextView);
        time05ShadowTextView = findViewById(R.id.time05ShadowTextView);
        timeAvgShadowTextView = findViewById(R.id.timeAvgShadowTextView);

        ConstraintLayout.LayoutParams parms = new ConstraintLayout.LayoutParams(screenWidth / 10, screenWidth / 10);
        setupImageView.setX(screenWidth / 24);
        setupImageView.setY(screenHeihgt / 200);
        setupImageView.setLayoutParams(parms);

        historyImageView.setX(screenWidth / 48 * 41);
        historyImageView.setY(screenHeihgt / 200);
        historyImageView.setLayoutParams(parms);

        whileImageView.setX(0);
        whileImageView.setY(0);
        whileImageView.getLayoutParams().width = screenWidth;
        whileImageView.getLayoutParams().height = (screenHeihgt / 200) + (screenHeihgt / 18);
        whileImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        backgrImageView.setX(1);
        backgrImageView.setY((screenHeihgt / 200) + (screenHeihgt / 18));
        backgrImageView.getLayoutParams().width = screenWidth - 2;
        backgrImageView.getLayoutParams().height = screenHeihgt - ((screenHeihgt / 200) + (screenHeihgt / 18) + 106);
        backgrImageView.setScaleType(ImageView.ScaleType.FIT_XY);

        initJsonPlaceHolderApi();

//        round_MAX = 4;
        round_MAX = parameters.getRounds();

//        phase = 0;
        timerSeconds = 0;
        round = 0;
        for (int i = 0; i <= round_MAX; i++) {
            results[i] = 0;
        }

        setPhase0();


//        Log.i("registerNewUserTEST", "01");
//        registerNewUserTEST();
//        Log.i("registerNewUserTEST", "02");


    }


    //////////////////////////////////////////////////////////////////////////////
    // on Create Options Menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    //////////////////////////////////////////////////////////////////////////////
    // when tap on menu item
    // on Options Item Selected
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_time:
                Intent intentT = new Intent(this, DisplaySetupParameters.class);
                intentT.putExtra("PARAMETERS", parameters);
                startActivity(intentT);
                return true;
            case R.id.nav_sounds:
                Intent intentS = new Intent(this, DisplaySetupSounds.class);
                intentS.putExtra("PARAMETERS", parameters);
                startActivity(intentS);
                return true;
            case R.id.nav_user:
                Intent intentU = new Intent(this, DisplaySetupUser.class);
                intentU.putExtra("PARAMETERS", parameters);
                startActivity(intentU);
                return true;
        }

        return true;
    }

    //////////////////////////////////////////////////////////////////////////////
    private void initJsonPlaceHolderApi() {

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://3.129.65.192/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the Setup button
     */
    public void setupParameters(View view) {
        Intent intent = new Intent(this, DisplaySetupParameters.class);
        intent.putExtra("PARAMETERS", parameters);
        startActivity(intent);
    }


    //////////////////////////////////////////////////////////////////////////////
    public void resetTimer() {

        if (phase == 1) {
            Log.i("resetTimer ", "phase=1");

            countDownTimer.cancel();
            breathsDownTimer.cancel();
            counterIsActive = false;

        } else if (phase == 2) {
            Log.i("resetTimer ", "phase=2");

            timerExit = true;

            if (parameters.getVoice().equals("Y")) {
                mplayerPhase2.stop();
                mplayerPhase2.release();
            }

            mplayerGong = MediaPlayer.create(getApplicationContext(), R.raw.gong);
            mplayerGong.start();

            processForTimerExit();

//            goButton.setVisibility(View.INVISIBLE);
//            goButton.setText("CANCEL");
            stopButton.setVisibility(View.INVISIBLE);
            goTextView.setVisibility((View.INVISIBLE));
            cancelTextView.setVisibility((View.VISIBLE));
            stopTextView.setVisibility(View.INVISIBLE);

            if (round == round_MAX) {
                saveScore();
            }

            thirdPhase();

        } else if (phase == 3) {
            Log.i("resetTimer ", "phase=3");

            countDownTimer.cancel();
            breathsDownTimer.cancel();
            counterIsActive = false;

        } else if (phase == 4) {

        }

    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the STOP button
     */
    public void stopClick(View view) {
        if (phase == 2) {
            Log.i("Pic_Background_taped", "resetTimer, phase 2");
            resetTimer();
        }
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the EXIT button
     */
    public void exitApp(View view) {
        this.finishAffinity();
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the START/CANCEL button
     */
    public void startCountDown(View view) {

        if (phase == 1) {
            Log.i("Button Pressed", "resetTimer, phase 1");

            resetTimer();
            setPhase0();

            if (!parameters.getMusic().equals("N")) {
                mplayerBackground.stop();
                mplayerBackground.release();
            }

        } else if (phase == 2) {
            // Never used case
            Log.i("Button Pressed", "resetTimer, phase 2");

            resetTimer();

        } else if (phase == 3) {
            resetTimer();
            setPhase0();

            if (!parameters.getMusic().equals("N")) {
                mplayerBackground.stop();
                mplayerBackground.release();
            }

        } else if (phase == 4) {
            resetTimer();
            setPhase0();

            if (!parameters.getMusic().equals("N")) {
                mplayerBackground.stop();
                mplayerBackground.release();
            }

        } else if (phase == 0) {
            Log.i("Button Pressed", "startCountDown");

            initExercise();
            firstPhase();

        } else {
            Log.i("startCountDown", "UNTREATED CASE !!!");
        }
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the FINISH button
     */
    public void finishCountDown(View view) {
        Log.i("finishCountDown_01", "OK");

        finishButton.setVisibility(View.INVISIBLE);
        finishTextViewAction.setVisibility(View.INVISIBLE);

        //copy from phase 4
        timerTexView.setText("");
        breathdownTextView.setText("");

//        phase = 4;  //set phase like 4. ResetTimer() should work as in phase 4.
        resetTimer();
        saveScore();

        if (!parameters.getMusic().equals("N")) {
            mplayerBackground.stop();
            mplayerBackground.release();
        }

    }



    //////////////////////////////////////////////////////////////////////////////
    private void initExercise() {
        Log.i("initExercise_01", "OK");

        counterIsActive = true;
        round = 0;
//        goButton.setText("CANCEL");
        goTextView.setVisibility((View.INVISIBLE));
        cancelTextView.setVisibility((View.VISIBLE));
        clearTextVievs();
        userScore.initUserScore();


        playlist = new ArrayList<>();
        playlist.add(R.raw.bckgr_music_heaven);
        playlist.add(R.raw.bckgr_music_harmony);
        playlist.add(R.raw.bckgr_music_asian);
        playlist.add(R.raw.healing_background_music);
        Log.i("initExercise_10", "OK");

        if (parameters.getMusic().equals("M1"))
            mplayerBackground = MediaPlayer.create(getApplicationContext(), R.raw.healing_background_music);
//        else if (parameters.getMusic().equals("M2"))
//            mplayerBackground = MediaPlayer.create(getApplicationContext(), R.raw.bckgr_music_heaven);
        else if (parameters.getMusic().equals("M2"))
            mplayerBackground = MediaPlayer.create(getApplicationContext(), playlist.get(0));
        Log.i("initExercise_15", "OK");

        if (!parameters.getMusic().equals("N")) {
            mplayerBackground.setVolume(0.30f, 0.30f);
            mplayerBackground.setLooping(true);
            mplayerBackground.start();
        }
        Log.i("initExercise_20", "OK");

        if (parameters.getMusic().equals("M2")) {
            timer = new Timer();
            if (playlist.size() > 1) playNext();
            Log.i("initExercise_99", "OK");
        }

    }

    public void playNext() {
        Log.i("playNext_01", "OK");

        playingBackgroundMusic = (playingBackgroundMusic + 1) % playlist.size();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                mplayerBackground.reset();
                mplayerBackground = MediaPlayer.create(MainActivity.this, playlist.get(playingBackgroundMusic));
                mplayerBackground.setVolume(0.30f, 0.30f);
                mplayerBackground.start();
                if (playlist.size() > playingBackgroundMusic + 1) {
                    playNext();
                }
            }
        }, mplayerBackground.getDuration() + 100);
    }


    //////////////////////////////////////////////////////////////////////////////
    private void setPhase0() {
        phase = 0;
        exitButton.setVisibility(View.INVISIBLE);
        exitImageView.setVisibility(View.VISIBLE);
//        goButton.setText("START");
        finishButton.setVisibility(View.INVISIBLE);
        finishTextViewAction.setVisibility(View.INVISIBLE);
        goTextView.setVisibility((View.VISIBLE));
        cancelTextView.setVisibility((View.INVISIBLE));
        timerTexView.setText("");
        breathdownTextView.setText("");

    }


    //////////////////////////////////////////////////////////////////////////////
    private void setPhase1() {
        phase = 1;
        exitButton.setVisibility(View.INVISIBLE);
        exitImageView.setVisibility(View.INVISIBLE);
        finishButton.setVisibility(View.VISIBLE);
        finishTextViewAction.setVisibility(View.VISIBLE);
//        goButton.setVisibility(View.VISIBLE);
        goTextView.setVisibility(View.INVISIBLE);
        cancelTextView.setVisibility((View.VISIBLE));
        stopButton.setVisibility(View.INVISIBLE);

    }


    //////////////////////////////////////////////////////////////////////////////
    private void firstPhase() {
        setPhase1();

        round++;
        final MediaPlayer mplayerGong = MediaPlayer.create(getApplicationContext(), R.raw.gong);

        final MediaPlayer mplayerBreath;
        if (parameters.getBreathingSound().equals("S3"))
            mplayerBreath = MediaPlayer.create(getApplicationContext(), R.raw.breath_wh_normal_3_9);
        else if (parameters.getBreathingSound().equals("S1"))
            mplayerBreath = MediaPlayer.create(getApplicationContext(), R.raw.breath_bunny_normal_3_9);
        else if (parameters.getBreathingSound().equals("S2"))
            mplayerBreath = MediaPlayer.create(getApplicationContext(), R.raw.breath_bunny_normal_3_9);
        else
            mplayerBreath = null;

        Log.i("firstPhase 01", "OK");

        countDownTimer = new CountDownTimer(parameters.getSecondsOfStartingPhase() * 1000 + 100, 1000) {

            // 1 second ticker ---------------------------------------
            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownTextView((int) (millisUntilFinished / 1000));

                if ((millisUntilFinished < 10000) & (millisUntilFinished > 1000)) {
//                    MediaPlayer mplayerGong = MediaPlayer.create(getApplicationContext(), R.raw.a_tone);
                    if (mplayerGong.isPlaying()) {
                        try {
                            mplayerGong.stop();
                            mplayerGong.prepare();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    mplayerGong.start();
                }
            }

            @Override
            public void onFinish() {
                Log.i("Phase 1", "Timer 1 second ticker done");

                mplayerGong.stop();
                mplayerGong.release();
                resetTimer();
                breathdownTextView.setText("");

                secondPhase();
            }
        }.start();

        breathsLeft = parameters.getBreathsInStartingPhase();
        float realSpeed = (float) ((((float) (parameters.getBreathingSpeedInStartingPhase())) / 2) + 2.5);
        int secondsBreathing = (int) (breathsLeft * realSpeed);
        breathsDownTimer = new CountDownTimer(parameters.getSecondsOfStartingPhase() * 1000 + 100, (long) (realSpeed * 1000)) {


            // 1 breath ticker ---------------------------------------
            @Override
            public void onTick(long millisUntilFinished) {
//                Log.i("breathsDownTimer", "tick");
                updateBreathdownTextView((int) (breathsLeft));
                breathsLeft--;
                if (breathsLeft >= 0) {
                    if ((millisUntilFinished / 1000) > 5) {
//                        MediaPlayer mplayerBreath = MediaPlayer.create(getApplicationContext(), R.raw.breathing05);
                        if (!parameters.getBreathingSound().equals("N")) {
                            if (mplayerBreath.isPlaying()) {
                                try {
                                    mplayerBreath.stop();
                                    mplayerBreath.prepare();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        if (!parameters.getBreathingSound().equals("N"))
                            mplayerBreath.start();
                    }
                }
            }

            @Override
            public void onFinish() {
                if (!parameters.getBreathingSound().equals("N")) {
                    mplayerBreath.stop();
                    mplayerBreath.release();
//                mplayerBreath = null;
                }
            }
        }.start();


    }


    //////////////////////////////////////////////////////////////////////////////
    @SuppressLint("LongLogTag")
    public void updateCountdownTextView(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft - (minutes * 60);

        String secondString;
        if (seconds < 10) {
            secondString = "0" + Integer.toString(seconds);
        } else secondString = Integer.toString(seconds);

        timerTexView.setText(Integer.toString(minutes) + ":" + secondString);

    }


    //////////////////////////////////////////////////////////////////////////////
    @SuppressLint("LongLogTag")
    public void updateBreathdownTextView(int breathsLeft) {

        breathdownTextView.setText(Integer.toString(breathsLeft));

    }


    //////////////////////////////////////////////////////////////////////////////
    public void setPhase2() {
        phase = 2;
//        goButton.setVisibility(View.INVISIBLE);
        goTextView.setVisibility(View.INVISIBLE);
        cancelTextView.setVisibility((View.INVISIBLE));
        finishButton.setVisibility(View.INVISIBLE);
        finishTextViewAction.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.VISIBLE);
        stopTextView.setVisibility(View.VISIBLE);
        exitButton.setVisibility(View.INVISIBLE);
        exitImageView.setVisibility(View.INVISIBLE);

    }


    //////////////////////////////////////////////////////////////////////////////
    public void secondPhase() {
        Log.i("Phase 2", "Started");

        setPhase2();
        timerSeconds = -1;
        timerExit = false;


        handler = new Handler();
        run = new Runnable() {
            @Override
            public void run() {
//                Log.i("Timer", "A second passed by");
                timerSeconds++;
                updateCountdownTextView((int) timerSeconds);
                handler.postDelayed(this, 1000);
            }
        };
        handler.post(run);

        if (parameters.getVoice().equals("Y")) {
            mplayerPhase2 = MediaPlayer.create(getApplicationContext(), R.raw.phase2_wh_min02);
            mplayerPhase2.start();
        }

    }


    //////////////////////////////////////////////////////////////////////////////
    private void processForTimerExit() {
        handler.removeCallbacks(run);

        results[round] = timerSeconds;

        userScore.setUserName(parameters.getEmail());
//        userScore.setDate(String.valueOf(new Date().getTime()));
//        Log.i("setDate", String.valueOf(userScore.getDate()));

        userScore.setDateTime(new Date().getTime());
        Log.i("setDateTime", String.valueOf(userScore.getDateTime()));

        time01TexView.setText(SecondsToTimeString(results[1]));
        userScore.setTime1(results[1]);
        if (round == 2) {
            time02TexView.setText(SecondsToTimeString(results[2]));
            userScore.setTime2(results[2]);
        }
        if (round == 3) {
            time03TexView.setText(SecondsToTimeString(results[3]));
            userScore.setTime3(results[3]);
        }
        if (round == 4) {
            time04TexView.setText(SecondsToTimeString(results[4]));
            userScore.setTime4(results[4]);
        }
        if (round == 5) {
            time05TexView.setText(SecondsToTimeString(results[5]));
            userScore.setTime5(results[5]);
        }
        time01ShadowTextView.setText(time01TexView.getText());
        time02ShadowTextView.setText(time02TexView.getText());
        time03ShadowTextView.setText(time03TexView.getText());
        time04ShadowTextView.setText(time04TexView.getText());
        time05ShadowTextView.setText(time05TexView.getText());

        userScore.setTimeAvg(avgTime());
        timeAvgTexView.setText("avg: " + String.valueOf(SecondsFloatToTimeString(avgTime())));
        timeAvgShadowTextView.setText(timeAvgTexView.getText());

        userScore.setTimeMax(maxTime());
        userScore.setTimeMax2(max2AvgTime());
        userScore.setTimeMax3(max3AvgTime());

        Log.i("processForTimerExit02", "OK");

    }


    //////////////////////////////////////////////////////////////////////////////
    private void thirdPhase() {
        phase = 3;

        countDownTimer = new CountDownTimer(parameters.getSecondsOfRelaxingPhase() * 1000 + 100, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                updateCountdownTextView((int) (millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                Log.i("Phase 3", "Timer all done");
                MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.gong);
                mplayer.start();
                resetTimer();

                fourthPhase();
            }
        }.start();
    }


    //////////////////////////////////////////////////////////////////////////////
    public void setPhase4() {
        phase = 4;
//        goButton.setVisibility(View.INVISIBLE);
        goTextView.setVisibility(View.INVISIBLE);
        cancelTextView.setVisibility((View.INVISIBLE));
        finishButton.setVisibility(View.INVISIBLE);
        finishTextViewAction.setVisibility(View.INVISIBLE);
        stopButton.setVisibility(View.INVISIBLE);
        exitButton.setVisibility(View.INVISIBLE);
        exitImageView.setVisibility(View.INVISIBLE);

    }


    //////////////////////////////////////////////////////////////////////////////
    private void fourthPhase() {
        Log.i("fourthPhase 000", "OK");

        setPhase4();

        final MediaPlayer mplayerBreathout = MediaPlayer.create(getApplicationContext(), R.raw.breathout);
        mplayerBreathout.start();

        countDownTimer = new CountDownTimer(8100, 8000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i("fourthPhase 100", "OK");
            }

            @Override
            public void onFinish() {
                Log.i("fourthPhase 200", "OK");

                if (round < round_MAX) {
                    firstPhase();

                } else {        //last round
                    //same code as in finishCountDown()
                    Log.i("Phase 4", "End of game.");

                    resetTimer();
                    setPhase0();

                    if (!parameters.getMusic().equals("N")) {
                        mplayerBackground.stop();
                        mplayerBackground.release();
                    }
                }
            }
        }.start();

    }


    //////////////////////////////////////////////////////////////////////////////
    private void clearTextVievs() {
        time01TexView.setText("");
        time02TexView.setText("");
        time03TexView.setText("");
        time04TexView.setText("");
        time05TexView.setText("");
        time01ShadowTextView.setText("");
        time02ShadowTextView.setText("");
        time03ShadowTextView.setText("");
        time04ShadowTextView.setText("");
        time05ShadowTextView.setText("");
        timeAvgTexView.setText("");
        timeAvgShadowTextView.setText("");
        for (int i = 0; i <= round_MAX; i++) {
            results[i] = 0;
        }
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the WEB button
     */
    public void openCloudScreen(View view) {
        Log.i("openCloud 01", "OK");
        Intent intent = new Intent(this, CloudScore.class);
        intent.putExtra("PARAMETERS", parameters);
        startActivity(intent);
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the History button - OLD, not used
     */
    public void openHistoryScoreScreen(View view) {
        Log.i("openHistoryScore 01", "OK");
        Intent intent = new Intent(this, HistoryScore.class);
//        Intent intent = new Intent(this, DbHistoryScore.class);

        Log.i("openHistoryScore 02", "OK");
//        intent.putExtra("PARAMETERS", "xxx");
        intent.putExtra("PARAMETERS", parameters);

        Log.i("openHistoryScore 03", "OK");
        startActivity(intent);

        Log.i("openHistoryScore 04", "OK");
    }


    //////////////////////////////////////////////////////////////////////////////

    /**
     * Called when the user taps the History button
     */
    public void openDbHistoryScoreScreen(View view) {
        Intent intent = new Intent(this, DbHistoryScore.class);
        intent.putExtra("PARAMETERS", parameters);
        startActivity(intent);
    }


    //////////////////////////////////////////////////////////////////////////////
    private void saveScore() {

        Log.i("saveScore_01", "OK");
        saveScoreToFile();
        Log.i("saveScore_02", "OK");

        saveScoreToDb();
        Log.i("saveScore_03", "OK");

//        viewAllDataFromDb();
        Log.i("saveScore_04", "OK");

        saveScoreToCloud();
        Log.i("saveScore_05", "OK");

    }


    //////////////////////////////////////////////////////////////////////////////
    public void saveScoreToDb() {

        boolean isInserted = myDb.insertData(userScore.getUserName(), userScore.getDateTime(),
                userScore.getTime1(), userScore.getTime2(), userScore.getTime3(), userScore.getTime4(),
                userScore.getTimeMax(), userScore.getTimeAvg(), userScore.getTimeMax2(), userScore.getTimeMax3(), userScore.isSynchro());

        if (isInserted)
            Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
        else
            Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();

        setTrophy();
    }


    //////////////////////////////////////////////////////////////////////////////
    // setTrophy
    // used indexes:
    // index 0-3: 0=max, 1=avg, 2=max2avg, 3=max3avg
    // index 1-6: 1=top ever, 2=best year, 3=best half y., 4=best q., 5=best month, 6=best week
    //////////////////////////////////////////////////////////////////////////////
    public void setTrophy() {
        Log.i("setTrophy_001", "OK");

        int season;    // 0=not used, 1=ever, 2=year, 3=half year, 4=quarter, 5=month, 6=week

        long date7Days = 7l * 24 * 60 * 60 * 1000;
        long date30Days = 30l * 24 * 60 * 60 * 1000;
        long date91Days = 90l * 24 * 60 * 60 * 1000;
        long date183Days = 183l * 24 * 60 * 60 * 1000;
        long date365Days = 365l * 24 * 60 * 60 * 1000;

        // ID: 0=not used, 1=ever, 2=year, 3=half year, 4=quarter, 5=month, 6=week
        long[] dateCategories = new long[]{0, 0, date365Days, date183Days, date91Days, date30Days, date7Days};

        // ID1: 0=not used, 1=top ever, 2=best year, 3=best half y., 4=best q., 5=best month, 6=best week
        // ID2: 0=max, 1=avg, 2=max2, 3=max3
        int[][] idsMaxims = new int[7][4];
        float[][] maxims = new float[][]{{0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}, {0, 0, 0, 0}};

        float[] max_item = new float[]{0, 0, 0, 0};    //ID: 0=max, 1=avg, 2=max2, 3=max3

        Cursor res = myDb.getAllDataDESC();
        if (res.getCount() == 0) {
            //showMessage("Error", "No data found");
            return;
        }

        long dateToday, dateDB, dateDiff;

        Log.i("setTrophy_010", "OK");

        while (res.moveToNext()) {

            dateToday = new Date().getTime();
            dateDB = res.getLong(2);
//            Log.i("setTrophy11_dateToday", simpleDateFormatDate.format(dateToday));
//            Log.i("setTrophy11_dateDB", simpleDateFormatDate.format(dateDB));

            dateDiff = dateToday - dateDB;
//            Log.i("setTrophy11_datumDiff", String.valueOf(dateDiff));
//            Log.i("setTrophy11_datum7Days", String.valueOf(date7Days));

            max_item[0] = res.getInt(7);
            ;
            max_item[1] = res.getFloat(8);
            max_item[2] = res.getFloat(9);
            max_item[3] = res.getFloat(10);
            Log.i("setTrophy110_1avg_item", String.valueOf(max_item[0]));
            Log.i("setTrophy110_1avg_item", String.valueOf(max_item[1]));
            Log.i("setTrophy110_2avg_item", String.valueOf(max_item[2]));
            Log.i("setTrophy110_3avg_item", String.valueOf(max_item[3]));

            season = 6;                                            // Week
            if (dateDiff <= dateCategories[season]) {
                setTrophySeason(idsMaxims, maxims, max_item, res, season);
            } else {
                season = 5;                                            // Month
                if (dateDiff <= dateCategories[season]) {
                    setTrophySeason(idsMaxims, maxims, max_item, res, season);
                } else {
                    season = 4;                                            // Quarter
                    if (dateDiff <= dateCategories[season]) {
                        setTrophySeason(idsMaxims, maxims, max_item, res, season);
                    } else {
                        season = 3;                                            // HalfYear
                        if (dateDiff <= dateCategories[season]) {
                            setTrophySeason(idsMaxims, maxims, max_item, res, season);
                        } else {
                            season = 2;                                            // Year
                            if (dateDiff <= dateCategories[season]) {
                                setTrophySeason(idsMaxims, maxims, max_item, res, season);
                            } else {
                                season = 1;                                            // Ever
                                setTrophySeason(idsMaxims, maxims, max_item, res, season);
                            }
                        }
                    }
                }
            }

        }

        Log.i("setTrophy20", "OK");

        // update DB
        myDb.clearAllTrophy();
        for (int ses = 6; ses >= 1; ses--) {            // ses: 0=not used, 1=ever, 2=year, 3=half year, 4=quarter, 5=month, 6=week
            for (int i = 0; i < 4; i++) {               // i: 1=average, 2=max2, 3=max3
                Log.i("setTrophy50", String.valueOf(idsMaxims[ses][i]));
                myDb.updateTrophy(idsMaxims[ses][i], ses, i + 1);
            }
        }
        Log.i("setTrophy60", "OK");

    }


    ////////////////////////////////////////////////////////////////////////////
    // Find maxims for season for all 4 types (i: 0=max, 1=average, 2=max2, 3=max3)
    private void setTrophySeason(int[][] idsMaxims, float[][] maxims, float[] max_item, Cursor res, int season) {

        for (int i = 0; i < 4; i++) {                       // i: 0=max, 1=average, 2=max2, 3=max3
            if (max_item[i] > maxims[season][i]) {
                maxims[season][i] = max_item[i];
                idsMaxims[season][i] = res.getInt(0);
                for (int j = 1; j < season; j++) {
                    maxims[j][i] = maxims[season][i];
                    idsMaxims[j][i] = idsMaxims[season][i];
                }
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    public void checkMaxims(int[][] idsMaxims, float[][] maxims, long[] dateCategories, int typ) {

    }

    //////////////////////////////////////////////////////////////////////////////
    public void viewAllDataFromDb() {
        Log.i("viewAllDataFromDb 01", "OK");
        Cursor res = myDb.getAllData();
        Log.i("viewAllDataFromDb 02", "OK");

        if (res.getCount() == 0) {
            showMessage("Error", "No data found");
            return;
        }
        Log.i("viewAllDataFromDb 03", "OK");

        StringBuffer buffer = new StringBuffer();
        while (res.moveToNext()) {
            buffer.append("Id :" + res.getString(0) + "\n");
            buffer.append("userName :" + res.getString(1) + "\n");
            Log.i("viewAllDataFromDb 12", "OK");

            buffer.append("dateTime :" + res.getString(2) + "\n");
            Log.i("viewAllDataFromDb 13", "OK");

            buffer.append("time1 :" + res.getString(3) + "\n");
            buffer.append("time2 :" + res.getString(4) + "\n");
            buffer.append("time3 :" + res.getString(5) + "\n");
            buffer.append("time4 :" + res.getString(6) + "\n");
            buffer.append("timeMax :" + res.getString(7) + "\n");
            buffer.append("timeAvg :" + res.getString(8) + "\n");
            buffer.append("synchro :" + res.getString(9) + "\n\n");
        }

        showMessage("Data", buffer.toString());

    }


    //////////////////////////////////////////////////////////////////////////////
    public void showMessage(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }


    //////////////////////////////////////////////////////////////////////////////
    public void saveScoreToFile() {

        String newScoreText = getNewScore();
        String oldScoreText = getOldHistoryScore();
        String scoreText = newScoreText + ";\n" + oldScoreText;
//        String scoreText = newScoreText;
        Log.i("saveScore_01", scoreText);

        FileOutputStream fos = null;
        try {
//            fos = openFileOutput(FILE_NAME, MODE_APPEND);
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(scoreText.getBytes());

//            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
            Log.i("Saved to ", getFilesDir() + "/" + FILE_NAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //////////////////////////////////////////////////////////////////////////////
    private String getOldHistoryScore() {
        FileInputStream fis = null;
        String oldScoreHistoryText = "";

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();

            int l = 0;
            while ((l < 4) && ((oldScoreHistoryText = br.readLine()) != null)) {
                sb.append(oldScoreHistoryText);
            }
            oldScoreHistoryText = sb.toString();
            Log.i("getOldHistoryScore", oldScoreHistoryText);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return oldScoreHistoryText;
    }


    //////////////////////////////////////////////////////////////////////////////
    private String getNewScore() {
        Date date = new Date();
        DateFormat dateFormat1 = new SimpleDateFormat("d. MMM, yyyy");
        DateFormat dateFormat2 = new SimpleDateFormat("HH:mm");
        String dateStr = dateFormat1.format(date);
        String timeStr = dateFormat2.format(date);

//        String newScoreText = String.valueOf("Robo");
        String newScoreText = parameters.getEmail();
        newScoreText = newScoreText + ";" + String.valueOf("WHex1");
        newScoreText = newScoreText + ";" + String.valueOf(dateStr);
        newScoreText = newScoreText + ";" + String.valueOf(timeStr);
        newScoreText = newScoreText + ";" + String.valueOf(results[1]) + ";" + String.valueOf(results[2]) + ";" + String.valueOf(results[3]) + ";" + String.valueOf(results[4]);
        newScoreText = newScoreText + ";" + String.valueOf(maxTime());
        newScoreText = newScoreText + ";" + String.valueOf(avgTime());
        Log.i("NewScore", newScoreText);

        return newScoreText;
    }


    //////////////////////////////////////////////////////////////////////////////
    private int maxTime() {
        int maxTime = results[1];
        for (int i = 2; i <= round_MAX; i++) {
            if (maxTime < results[i]) {
                maxTime = results[i];
            }
        }
        return maxTime;
    }


    //////////////////////////////////////////////////////////////////////////////
    private float avgTime() {
        float avgTime = 0;
        int notNullResults = 0;

        for (int i = 1; i <= round_MAX; i++) {
            avgTime = avgTime + results[i];
        }
        notNullResults = notNullResults();
        if (notNullResults == 0) {
            avgTime = 0;
        } else {
            avgTime = avgTime / notNullResults;
        }

        avgTime = (float) (Math.round(avgTime * 100.0) / 100.0);
        return avgTime;
    }


    //////////////////////////////////////////////////////////////////////////////
    // return average of max (ROUND_MAX - 1) times
    // in 4 rounds excercice returns average of max 3 times
    private float max3AvgTime() {
        float max3AvgTime = 0;
        int minTime = results[1];
        int notNullResults = 0;

        for (int i = 1; i <= round_MAX; i++) {
            max3AvgTime = max3AvgTime + results[i];
            if (results[i] < minTime) {
                minTime = results[i];
            }
        }
        notNullResults = notNullResults();
        if (notNullResults > (round_MAX - 2)) {
            notNullResults = (round_MAX - 1);
        }
        if (notNullResults == 0) {
            max3AvgTime = 0;
        } else {
            max3AvgTime = (max3AvgTime - minTime) / notNullResults;
        }

        max3AvgTime = (float) (Math.round(max3AvgTime * 100.0) / 100.0);
        return max3AvgTime;
    }

    //////////////////////////////////////////////////////////////////////////////
    // return average of max 2 times
    // WRONG !!
    private float max2AvgTime() {
        float max2AvgTime = 0;
        int minTime = 0;
        int notNullResults = 0;

        float max1 = results[1];
        float max2 = results[2];
        if (results[3] > max1) {
            max1 = results[3];
        } else if (results[3] > max2) {
            max2 = results[3];
        }
        if (results[4] > max2) {
            max2 = results[4];
        } else if (results[4] > max1) {
            max1 = results[4];
        }

        notNullResults = notNullResults();
        if (notNullResults > 1) {
            notNullResults = 2;
        }
        if (notNullResults == 0) {
            max2AvgTime = 0;
        } else {
            max2AvgTime = (max1 + max2) / notNullResults;
        }

        max2AvgTime = (float) (Math.round(max2AvgTime * 100.0) / 100.0);
        return max2AvgTime;
    }


    //////////////////////////////////////////////////////////////////////////////
    // return number of not nulls in results[]
    private int notNullResults() {
        int notNullResults = 0;
        for (int i = 1; i <= round_MAX; i++) {
            if (results[i] != 0) {
                notNullResults++;
            }
        }
        return notNullResults;
    }

    //////////////////////////////////////////////////////////////////////////////
    private void saveScoreToCloud() {
        Log.i("saveScoreToCloud_010", "OK");

        String username = parameters.getEmail();
        String password = parameters.getPassword();
//        Login login = new Login( "admin", "admin");
        Login login = new Login(username, password);
        Log.i("saveScoreToCloud_015", "OK");

        Call<Token> call = jsonPlaceHolderApi.getToken(login);
        Log.i("saveScoreToCloud_020", "OK");

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }

                Token tokenResponse = response.body();

                token = "Token " + tokenResponse.getToken();
                Log.i("Token ", token);

                postResultToCloud();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Token is not correct :(" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Token is not correct", t.getMessage());
            }
        });

    }

    //////////////////////////////////////////////////////////////////////////////
    private void registerNewUserTEST() {
        Log.i("registerNewUserTEST_010", "OK");

        String username = parameters.getEmail();
        username = "test3";
        String password = parameters.getPassword();
        password = "Test1234test1234";
//        Login login = new Login( "admin", "admin");
//        Login login = new Login(username, password);
        Log.i("registerNewUserTEST_015", "OK");
        Log.i("username:", username);
        Log.i("password:", password);

        Call<Token> call = jsonPlaceHolderApi.registration(username, password, password);
        Log.i("registerNewUserTEST_020", "OK");

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Code: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.i("registerNewUserTEST_030", String.valueOf(response.code()));
                    Log.i("registerNewUserTEST_035", String.valueOf(response.body()));
                    return;
                }

                Token tokenResponse = response.body();

                token = "Token " + tokenResponse.getToken();
                Log.i("Token ", token);

            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Token is not correct :(" + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Token is not correct", t.getMessage());
            }
        });

    }


    //////////////////////////////////////////////////////////////////////////////
    private void postResultToCloud() {

        Log.i("postResultToCloud_000 ", "OK");

        String versionName = "1.x";
        try {
            versionName = getApplicationContext().getPackageManager().getPackageInfo(getApplicationContext().getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ScoreForPostToCloud scoreForPostToCloud = new ScoreForPostToCloud(userScore.getDateTime(),
                userScore.getTime1(), userScore.getTime2(), userScore.getTime3(), userScore.getTime4(),
                userScore.getTimeMax(), userScore.getTimeAvg(), userScore.getTimeMax2(), userScore.getTimeMax3(), versionName);
        Log.i("postResultToCloud_010 ", "OK");
        String str_scoreForPostToCloud = "DateTime=" + scoreForPostToCloud.getDateTime();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-Time1=" + scoreForPostToCloud.getTime1();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-Time2=" + scoreForPostToCloud.getTime2();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-Time3=" + scoreForPostToCloud.getTime3();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-Time4=" + scoreForPostToCloud.getTime4();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-TimeMax=" + scoreForPostToCloud.getTimeMax();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-TimeAvg=" + scoreForPostToCloud.getTimeAvg();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-TimeMax2=" + scoreForPostToCloud.getTimeMax2();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-TimeMax3=" + scoreForPostToCloud.getTimeMax3();
        str_scoreForPostToCloud = str_scoreForPostToCloud + "-VersionName=" + scoreForPostToCloud.getVersionapp();
        Log.i("postResultToCloud_011 ", str_scoreForPostToCloud);


        Call<ResultFromCloud> call = jsonPlaceHolderApi.postScore(scoreForPostToCloud, token);
        Log.i("postResultToCloud_100 ", "OK");


        call.enqueue(new Callback<ResultFromCloud>() {
            @Override
            public void onResponse(Call<ResultFromCloud> call, Response<ResultFromCloud> response) {
                Log.i("postResultToCloud_200 ", "OK");

                if (!response.isSuccessful()) {
                    Log.i("postResultToCloud_210 ", "OK");
                    Log.i("postResultToCloud_211 ", String.valueOf(response.body()));
                    Log.i("postResultToCloud_212 ", String.valueOf(response.message()));

                    return;
                }

                ResultFromCloud postResponse = response.body();

                String content = "";
                content += "ID: " + postResponse.getId() + "\n";
                content += "dateTime: " + postResponse.getTime() + "\n";
                content += "time1: " + postResponse.getTime1() + "\n";
                content += "time2: " + postResponse.getTime2() + "\n";
                content += "time3: " + postResponse.getTime3() + "\n";
                content += "time4: " + postResponse.getTime4() + "\n";
                content += "timeMax: " + postResponse.getTime_max() + "\n";
                content += "timeAvg: " + postResponse.getTime_avg() + "\n\n";

//                Log.i("postResultToCloud_220 ", content);
            }

            @Override
            public void onFailure(Call<ResultFromCloud> call, Throwable t) {
                Log.i("postResultToCloud_400 ", t.getMessage());
            }
        });
        Log.i("postResultToCloud_900 ", "OK");

    }

    private void getScore() {
        Log.i("getScore010 ", "OK");
        Log.i("token ", token);

        Call<List<ResultFromCloud>> call = jsonPlaceHolderApi.getScore(token);

        call.enqueue(new Callback<List<ResultFromCloud>>() {
            @Override
            public void onResponse(Call<List<ResultFromCloud>> call, Response<List<ResultFromCloud>> response) {
                Log.i("getScore108 ", "OK");
                if (response.isSuccessful()) {
                    Log.i("getScore110 ", "OK");

                    List<ResultFromCloud> results = response.body();

                    for (ResultFromCloud result : results) {
                        String content = "";
                        content = content + "User: " + result.getUserName() + "\n";
                        content = content + "Id: " + result.getId() + "\n";
                        content = content + "Time: " + result.getTime() + "\n";
                        content = content + "Time3: " + result.getTime3() + "\n\n";

                        Log.i("getScore120 ", content);
                    }
                    Log.i("getScore120 ", "OK");

                } else {
                    Log.i("getScore130 ", "Token is not correct :(");
                    Toast.makeText(MainActivity.this, "token is not correct :(", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<ResultFromCloud>> call, Throwable t) {
                Log.i("getScore140 ", "OK");
                Toast.makeText(MainActivity.this, "error :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //duplicite with HistoryScore
    public String SecondsToTimeString(int secondsTotal) {
        int minutes = secondsTotal / 60;
        int seconds = secondsTotal % 60;

        String secondsString;
        if (seconds < 10) {
            secondsString = "0" + String.valueOf(seconds);
        } else secondsString = String.valueOf(seconds);

        return String.valueOf(minutes) + ":" + secondsString;
    }

    //duplicite with HistoryScore
    @SuppressLint("LongLogTag")
    public String SecondsFloatToTimeString(Float secondsTotal) {
        String timeString = "";
        String secondsString = String.valueOf(secondsTotal);

        int pos = secondsString.indexOf(".");
        if (pos != -1) {
            timeString = SecondsToTimeString(Integer.parseInt(secondsString.substring(0, pos))) + secondsString.substring(pos);
        } else {
            timeString = SecondsToTimeString(Integer.parseInt(secondsString));
        }

        return timeString;
    }



}